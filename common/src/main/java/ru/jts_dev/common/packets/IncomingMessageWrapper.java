/*
 * Copyright (c) 2015, 2016, 2017 JTS-Team authors and/or its affiliates. All rights reserved.
 *
 * This file is part of JTS-V3 Project.
 *
 * JTS-V3 Project is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JTS-V3 Project is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JTS-V3 Project.  If not, see <http://www.gnu.org/licenses/>.
 */

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
    public static final char EOS = '\0';

    private final MessageHeaders headers;
    private ByteBuf payload;

    protected IncomingMessageWrapper() {
        headers = new MutableMessageHeaders(null);
    }

    @Override
    public final ByteBuf getPayload() {
        return payload;
    }

    public final void setPayload(final ByteBuf payload) {
        this.payload = payload;
    }

    @Override
    public final MessageHeaders getHeaders() {
        return headers;
    }

    /**
     * prepare packet after receiving data, read data to fields, for example
     */
    public abstract void prepare();

    public final byte readByte() {
        if (payload.readableBytes() < Byte.BYTES)
            throw new IndexOutOfBoundsException("At least 1 byte1 must be readable in payload");

        return payload.readByte();
    }

    public final int readShort() {
        if (payload.readableBytes() < Short.BYTES)
            throw new IndexOutOfBoundsException("At least 2 bytes must be readable in payload");

        return payload.readShort();
    }

    public final int readInt() {
        if (payload.readableBytes() < Integer.BYTES)
            throw new IndexOutOfBoundsException("At least 4 bytes must be readable in payload");

        return payload.readInt();
    }

    public final String readString() {
        final StringBuilder sb = new StringBuilder(32);
        char ch;
        while ((ch = payload.readChar()) != EOS) {
            sb.append(ch);
        }
        return sb.toString();
    }

    public final byte[] readBytes(final int length) {
        if (payload.readableBytes() < length)
            throw new IndexOutOfBoundsException("At least 4 bytes must be readable in payload");

        final byte[] data = new byte[length];
        payload.readBytes(data);

        return data;
    }

    public final String getConnectionId() {
        if (!headers.containsKey(IpHeaders.CONNECTION_ID))
            throw new RuntimeException("connectionId header not present in headers");
        return (String) headers.get(IpHeaders.CONNECTION_ID);
    }

    public final <E extends Enum<?>> E readIntAs(final Class<E> enumClass) {
        final int value = readInt();

        if (value < 0 || value > enumClass.getEnumConstants().length)
            throw new IndexOutOfBoundsException("value " + value + " not in enum constants of " + enumClass.getName());

        return enumClass.getEnumConstants()[value];
    }

    /**
     * free payload after preparing packet and release Netty ByteBuf
     *
     * @see #prepare()
     * @see ByteBuf#release()
     */
    public final void release() {
        payload.release();
    }
}
