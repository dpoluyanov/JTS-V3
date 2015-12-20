package ru.jts_dev.gameserver.util;

import io.netty.buffer.ByteBuf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import ru.jts_dev.gameserver.model.GameSession;
import ru.jts_dev.gameserver.service.GameSessionService;

import static org.springframework.integration.ip.IpHeaders.CONNECTION_ID;

/**
 * @author Camelion
 * @since 13.12.15
 */
@Component
public class Encoder {
    @Autowired
    private GameSessionService sessionService;

    @Transformer
    public ByteBuf decrypt(ByteBuf data, @Header(CONNECTION_ID) String connectionId) {
        GameSession gameSession = sessionService.getSessionBy(connectionId);
        ByteBuf key = gameSession.getDecryptKey();

        int temp = 0;
        for (int i = 0; i < data.readableBytes(); i++) {
            final int temp2 = data.getUnsignedByte(i);
            data.setByte(i, (byte) (temp2 ^ key.getByte(i & 15) ^ temp));
            temp = temp2;
        }

        int old = key.getInt(8);
        old += data.readableBytes();

        key.setInt(8, old);

        return data;
    }

    @Transformer
    public ByteBuf encrypt(ByteBuf data, @Header(CONNECTION_ID) String connectionId) {
        GameSession gameSession = sessionService.getSessionBy(connectionId);
        ByteBuf key = gameSession.getEncryptKey();

        int temp = 0;
        for (int i = 0; i < data.readableBytes(); i++) {
            int temp2 = data.getUnsignedByte(data.readerIndex() + i);
            temp = temp2 ^ key.getByte(i & 15) ^ temp;
            data.setByte(data.readerIndex() + i, (byte) temp);
        }

        int old = key.getInt(8);
        old += data.readableBytes();

        key.setInt(8, old);

        return data;
    }
}
