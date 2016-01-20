package ru.jts_dev.common.packets;

import io.netty.buffer.ByteBuf;
import org.springframework.integration.ip.IpHeaders;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

/**
 * @author Camelion
 * @since 06.12.15
 */
public abstract class IncomingMessageWrapper implements Message<ByteBuf>, Runnable {
    private final MessageHeaders headers;
    private ByteBuf buffer;

    public IncomingMessageWrapper() {
        headers = new MutableMessageHeaders(null);
    }

    @Override
    public ByteBuf getPayload() {
        return buffer;
    }

    public void setPayload(ByteBuf buffer) {
        this.buffer = buffer;
    }

    @Override
    public MessageHeaders getHeaders() {
        return headers;
    }

    /**
     * prepare packet after receiving data, read data to fields, for example
     */
    public abstract void prepare();


    public byte readByte() {
        if (buffer.readableBytes() < Byte.BYTES)
            throw new IndexOutOfBoundsException("At least 1 byte1 must be readable in buffer");

        return buffer.readByte();
    }

    public int readShort() {
        if (buffer.readableBytes() < Short.BYTES)
            throw new IndexOutOfBoundsException("At least 2 bytes must be readable in buffer");

        return buffer.readShort();
    }

    public int readInt() {
        if (buffer.readableBytes() < Integer.BYTES)
            throw new IndexOutOfBoundsException("At least 4 bytes must be readable in buffer");

        return buffer.readInt();
    }

    public String readString() {
        final StringBuilder sb = new StringBuilder();
        char ch;
        while ((ch = buffer.readChar()) != '\0') {
            sb.append(ch);
        }
        return sb.toString();
    }

    public byte[] readBytes(int length) {
        if (buffer.readableBytes() < length)
            throw new IndexOutOfBoundsException("At least 4 bytes must be readable in buffer");

        byte[] data = new byte[length];
        buffer.readBytes(data);

        return data;
    }

    public String getConnectionId() {
        if (!getHeaders().containsKey(IpHeaders.CONNECTION_ID))
            throw new RuntimeException("connectionId header not present in headers");
        return (String) getHeaders().get(IpHeaders.CONNECTION_ID);
    }

    public <E extends Enum<?>> E readIntAs(Class<E> enumClass) {
        int value = readInt();

        if (value < 0 || value > enumClass.getEnumConstants().length)
            throw new IndexOutOfBoundsException("value " + value + " not in enum constants of " + enumClass.getName());

        return enumClass.getEnumConstants()[value];
    }

    /**
     * free buffer after preparing packet and release Netty ByteBuf
     *
     * @see #prepare()
     * @see ByteBuf#release()
     */
    public void release() {
        getPayload().release();
    }
}
