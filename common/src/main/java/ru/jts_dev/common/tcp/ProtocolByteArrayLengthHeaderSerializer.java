package ru.jts_dev.common.tcp;

import org.springframework.integration.ip.tcp.serializer.ByteArrayLengthHeaderSerializer;
import org.springframework.integration.ip.tcp.serializer.SoftEndOfStreamException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import static java.nio.ByteOrder.LITTLE_ENDIAN;

/**
 * @author Camelion
 * @since 30.11.15
 */
public class ProtocolByteArrayLengthHeaderSerializer extends ByteArrayLengthHeaderSerializer {
    private final int headerSize = HEADER_SIZE_UNSIGNED_SHORT;

    @Override
    protected void writeHeader(OutputStream outputStream, int length) throws IOException {
        ByteBuffer lengthPart = ByteBuffer.allocate(this.headerSize).order(LITTLE_ENDIAN);
        length += headerSize; // Protocol thing, length represent header size + data size
        switch (this.headerSize) {
            case HEADER_SIZE_INT:
                lengthPart.putInt(length);
                break;
            case HEADER_SIZE_UNSIGNED_BYTE:
                if (length > 0xff) {
                    throw new IllegalArgumentException("Length header:"
                            + headerSize
                            + " too short to accommodate message length:" + length);
                }
                lengthPart.put((byte) length);
                break;
            case HEADER_SIZE_UNSIGNED_SHORT:
                if (length > 0xffff) {
                    throw new IllegalArgumentException("Length header:"
                            + headerSize
                            + " too short to accommodate message length:" + length);
                }
                lengthPart.putShort((short) length);
                break;
            default:
                throw new IllegalArgumentException("Bad header size:" + headerSize);
        }
        outputStream.write(lengthPart.array());
    }

    @Override
    protected int readHeader(InputStream inputStream) throws IOException, RuntimeException, SoftEndOfStreamException {
        byte[] lengthPart = new byte[this.headerSize];
        try {
            int status = read(inputStream, lengthPart, true);
            if (status < 0) {
                throw new SoftEndOfStreamException("Stream closed between payloads");
            }
            int messageLength;
            switch (headerSize) {
                case HEADER_SIZE_INT:
                    messageLength = ByteBuffer.wrap(lengthPart).order(LITTLE_ENDIAN).getInt();
                    if (messageLength < 0) {
                        throw new IllegalArgumentException("Length header:"
                                + messageLength
                                + " is negative");
                    }
                    break;
                case HEADER_SIZE_UNSIGNED_BYTE:
                    messageLength = ByteBuffer.wrap(lengthPart).order(LITTLE_ENDIAN).get() & 0xff;
                    break;
                case HEADER_SIZE_UNSIGNED_SHORT:
                    messageLength = ByteBuffer.wrap(lengthPart).order(LITTLE_ENDIAN).getShort() & 0xffff;
                    break;
                default:
                    throw new IllegalArgumentException("Bad header size:" + headerSize);
            }

            messageLength -= headerSize; // substract header size from data
            return messageLength;
        } catch (final SoftEndOfStreamException e) {
            throw e;
        }
    }
}
