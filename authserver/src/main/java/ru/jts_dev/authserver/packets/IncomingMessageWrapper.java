package ru.jts_dev.authserver.packets;

import io.netty.buffer.ByteBuf;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.support.MutableMessageHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 06.12.15
 */
@Scope(SCOPE_PROTOTYPE)
@Component
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

    public int readInt() {
        if (buffer.readableBytes() < 4)
            throw new IndexOutOfBoundsException("At lesat 4 bytes must be readble in buffer");

        return buffer.readInt();
    }
}
