package ru.jts_dev.common.packets;

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
public abstract class OutgoingMessageWrapper implements Message<ByteBuf> {
    private final ByteBuf buffer;
    private final MessageHeaders headers;

    public OutgoingMessageWrapper() {
        buffer = buffer().order(ByteOrder.LITTLE_ENDIAN);
        headers = new MutableMessageHeaders(null);
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

    /**
     * write boolean to byte, true == 0x01, false = 0x00
     *
     * @param b - boolean value for writing
     */
    protected void putByte(boolean b) {
        buffer.writeByte(b ? 0x01 : 0x00);
    }

    protected void putShort(int s) {
        buffer.writeShort(s);
    }

    protected void putInt(int i) {
        buffer.writeInt(i);
    }

    protected void putLong(long l) {
        buffer.writeLong(l);
    }

    protected void putDouble(double d) {
        buffer.writeDouble(d);
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
