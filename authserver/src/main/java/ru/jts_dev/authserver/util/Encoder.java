package ru.jts_dev.authserver.util;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import ru.jts_dev.authserver.model.AuthSession;
import ru.jts_dev.authserver.service.AuthSessionService;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.Objects;
import java.util.Random;

import static org.springframework.integration.ip.IpHeaders.CONNECTION_ID;

/**
 * @author Camelion
 * @since 02.12.15
 */
@Component
public class Encoder {
    private static final byte[] STATIC_BLOWFISH_KEY = {
            (byte) 0x6b, (byte) 0x60, (byte) 0xcb, (byte) 0x5b,
            (byte) 0x82, (byte) 0xce, (byte) 0x90, (byte) 0xb1,
            (byte) 0xcc, (byte) 0x2b, (byte) 0x6c, (byte) 0x55,
            (byte) 0x6c, (byte) 0x6c, (byte) 0x6c, (byte) 0x6c
    };
    public static final String STATIC_KEY_HEADER = "static_key";
    public static final int BLOWFISH_KEY_SIZE = 16;
    private static final Logger log = LoggerFactory.getLogger(Encoder.class);
    private static final int BLOWFISH_BLOCK_SIZE = 8;

    private final AuthSessionService authSessionService;

    private final Random random;

    @Autowired
    public Encoder(AuthSessionService authSessionService, Random random) {
        Assert.notNull(authSessionService, "AuthService must not be null!");
        Assert.notNull(random, "Random must not be null!");

        this.authSessionService = authSessionService;
        this.random = random;
    }

    public ByteBuf appendBlowFishPadding(ByteBuf buf) {
        int padding = BLOWFISH_BLOCK_SIZE - buf.readableBytes() % BLOWFISH_BLOCK_SIZE;
        byte[] stuff = new byte[padding];
        random.nextBytes(stuff);
        buf.writeBytes(stuff);

        return buf;
    }

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

        buf.writeZero(4); // for blowfish block

        return buf;
    }

    public ByteBuf encWithXor(ByteBuf buf) {
        if (buf.readableBytes() % 4 != 0) {
            throw new IndexOutOfBoundsException("ByteBuf size must be multiply of 4");
        }
        int edx;
        int ecx = 0; // Initial xor key

        buf.writeLong(random.nextLong()); // 8 bytes padding

        for (int pos = 4; pos < buf.readableBytes(); pos += 4) {
            edx = buf.getInt(pos);

            ecx += edx;
            edx ^= ecx;

            buf.setInt(pos, edx);
        }
        buf.writeInt(ecx);

        buf.writeInt(random.nextInt()); // 4 bytes for blowfish block

        return buf;
    }

    @Transformer
    public byte[] encrypt(byte[] data, @Header(CONNECTION_ID) String connectionId,
                          // TODO: 08.12.15 spring integration bug with ignoring defaultValue in header
                          @Header(value = STATIC_KEY_HEADER, required = false) String static_key) throws IOException {
        if (data.length % BLOWFISH_BLOCK_SIZE != 0)
            throw new IndexOutOfBoundsException("data.length must be multiply of " + BLOWFISH_BLOCK_SIZE);

        BlowfishEngine blowfishEngine = new BlowfishEngine();
        if (static_key != null && static_key.equals("true")) {
            blowfishEngine.init(STATIC_BLOWFISH_KEY);
        } else {
            AuthSession gameSession = authSessionService.getSessionBy(connectionId);

            // perform null check
            Objects.requireNonNull(gameSession, "gameSession is null for " + connectionId);

            blowfishEngine.init(gameSession.getBlowfishKey());
        }
        if (log.isTraceEnabled() && data.length > 0) {
            final StringBuilder leftStr = new StringBuilder("[");
            for (byte b : data) {
                leftStr.append(" ");
                leftStr.append(String.format("%02X", b));
            }
            leftStr.append(" ]");

            log.trace("Raw bytes before encrypt: " + leftStr);
        }

        for (int i = 0; i < data.length; i += BLOWFISH_BLOCK_SIZE) {
            blowfishEngine.encryptBlock(data, i, data, i);
        }

        return data;
    }

    @Transformer
    public byte[] decrypt(byte[] data, @Header(CONNECTION_ID) String connectionId) throws IOException {
        if (data.length % BLOWFISH_BLOCK_SIZE != 0)
            throw new IndexOutOfBoundsException("data.length must be multiply of " + BLOWFISH_BLOCK_SIZE);

        AuthSession gameSession = authSessionService.getSessionBy(connectionId);

        BlowfishEngine blowfishEngine = new BlowfishEngine();
        blowfishEngine.init(gameSession.getBlowfishKey());

        for (int i = 0; i < data.length; i += BLOWFISH_BLOCK_SIZE) {
            blowfishEngine.decryptBlock(data, i, data, i);
        }

        return data;
    }
}
