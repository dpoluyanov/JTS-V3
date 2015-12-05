package ru.jts_dev.authserver.packets;

import io.netty.buffer.ByteBuf;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.nio.ByteOrder;

import static io.netty.buffer.Unpooled.buffer;

/**
 * @author Camelion
 * @since 30.11.15
 */
public abstract class MessageWrapper implements Message<ByteBuf> {
    private final MessageHeaders headers;
    private final ByteBuf buffer;

    public MessageWrapper() {
        headers = new MutableMessageHeaders(null);
        buffer = buffer().order(ByteOrder.LITTLE_ENDIAN);
    }

    @Override
    public ByteBuf getPayload() {
        return buffer;
    }

    @Override
    public MessageHeaders getHeaders() {
        return headers;
    }

    /**
     * write data to buffer before send
     */
    public abstract void write();

    protected void putByte(int b) {
        buffer.writeByte(b);
    }

    protected void putInt(int i) {
        buffer.writeInt(i);
    }

    protected void putBytes(byte[] data) {
        buffer.writeBytes(data);
    }

    protected void putString(String str) {
        for (int i = 0; i < str.length(); i++) {
            buffer.writeChar(str.charAt(i));
        }
        buffer.writeChar('\0');
    }
}
