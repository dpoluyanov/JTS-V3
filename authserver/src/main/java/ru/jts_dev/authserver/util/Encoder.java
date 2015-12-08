package ru.jts_dev.authserver.util;

import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.jts_dev.authserver.service.SessionService;
import ru.jts_dev.authserver.model.GameSession;

import java.io.IOException;
import java.security.InvalidParameterException;

/**
 * @author Camelion
 * @since 02.12.15
 */
@Component
public class Encoder {
    public static final byte[] STATIC_BLOWFISH_KEY = {
            (byte) 0x6b, (byte) 0x60, (byte) 0xcb, (byte) 0x5b,
            (byte) 0x82, (byte) 0xce, (byte) 0x90, (byte) 0xb1,
            (byte) 0xcc, (byte) 0x2b, (byte) 0x6c, (byte) 0x55,
            (byte) 0x6c, (byte) 0x6c, (byte) 0x6c, (byte) 0x6c
    };
    public static final String STATIC_KEY_HEADER = "static_key";
    public static final int BLOWFISH_KEY_SIZE = 16;
    private static final int BLOWFISH_BLOCK_SIZE = 8;

    @Autowired
    private SessionService sessionService;

    public ByteBuf validateChecksum(ByteBuf buf) {
        if (buf.readableBytes() % 4 != 0 || buf.readableBytes() <= 4) {
            throw new IndexOutOfBoundsException("ByteBuf size must be multiply of 4 and more, that 4");
        }

        long checksum = 0;
        long check;
        int i;

        for (i = 0; i < buf.readableBytes() - 4; i += 4) {
            check = buf.getInt(i);

            checksum ^= check;
        }

        check = buf.getInt(i);

        if (check != checksum) {
            throw new InvalidParameterException("Wrong checksum");
        }

        return buf.copy(0, buf.readableBytes() - 4);
    }

    public ByteBuf appendChecksum(ByteBuf buf) {
        if (buf.readableBytes() % 4 != 0) {
            throw new IndexOutOfBoundsException("ByteBuf size must be multiply of 4");
        }
        int checksum = 0;
        for (int i = 0; i < buf.readableBytes(); i += 4) {
            checksum ^= buf.getInt(i);
        }

        buf.writeInt(checksum);

        return buf;
    }

    public ByteBuf encWithXor(ByteBuf buf) {
        if (buf.readableBytes() % 4 != 0) {
            throw new IndexOutOfBoundsException("ByteBuf size must be multiply of 4");
        }
        int edx;
        int ecx = 0; // Initial xor key

        for (int pos = 4; pos < buf.readableBytes(); pos += 4) {
            edx = buf.getInt(pos);

            ecx += edx;
            edx ^= ecx;

            buf.setInt(pos, edx);
        }
        buf.writeInt(ecx);
        return buf;
    }

    public ByteBuf appendPadding(ByteBuf buf) {
        if (buf.readableBytes() % BLOWFISH_BLOCK_SIZE != 0) {
            buf.writeBytes(new byte[BLOWFISH_BLOCK_SIZE - buf.readableBytes() % BLOWFISH_BLOCK_SIZE]);
        }

        return buf;
    }

    @Transformer
    public byte[] encrypt(byte[] data, @Header(IpHeaders.CONNECTION_ID) String connectionId,
                          // TODO: 08.12.15 spring integration bug with ingoring defaultValue in header
                          @Header(value = STATIC_KEY_HEADER, required = false) String static_key) throws IOException {
        if (data.length % BLOWFISH_BLOCK_SIZE != 0)
            throw new IndexOutOfBoundsException("data.length must be multiply of 8");

        BlowfishEngine blowfishEngine = new BlowfishEngine();
        if (static_key != null && static_key.equals("true")) {
            blowfishEngine.init(true, STATIC_BLOWFISH_KEY);
        } else {
            GameSession gameSession = sessionService.getSessionBy(connectionId);

            if (gameSession == null)
                throw new NullPointerException("gameSession is null for " + connectionId);

            blowfishEngine.init(true, gameSession.getBlowfishKey());
        }

        for (int i = 0; i < data.length; i += BLOWFISH_BLOCK_SIZE) {
            blowfishEngine.processBlock(data, i, data, i);
        }

        return data;
    }

    @Transformer
    public byte[] decrypt(byte[] data, @Header(IpHeaders.CONNECTION_ID) String connectionId) throws IOException {
        if (data.length % BLOWFISH_BLOCK_SIZE != 0)
            throw new IndexOutOfBoundsException("data.length must be myltiply of 8");

        GameSession gameSession = sessionService.getSessionBy(connectionId);

        BlowfishEngine blowfishEngine = new BlowfishEngine();
        blowfishEngine.init(false, gameSession.getBlowfishKey());

        for (int i = 0; i < data.length; i += BLOWFISH_BLOCK_SIZE) {
            blowfishEngine.processBlock(data, i, data, i);
        }

        return data;
    }
}
