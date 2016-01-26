package ru.jts_dev.authserver.config;

import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.jts_dev.authserver.service.AuthSessionService;
import ru.jts_dev.authserver.util.Encoder;
import ru.jts_dev.common.config.UtilsConfig;
import ru.jts_dev.common.id.BitSetIdPool;
import ru.jts_dev.common.id.IdPool;

import java.util.Random;

import static io.netty.buffer.Unpooled.buffer;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * @author Camelion
 * @since 02.12.15
 */
@ContextConfiguration(classes = {
        Encoder.class,
        UtilsConfig.class,
        AuthSessionService.class,
        KeyGenerationConfig.class,
        BitSetIdPool.class
})
@RunWith(SpringJUnit4ClassRunner.class)
public class EncoderTest extends Assert {

    @Autowired
    private Encoder encoder;

    @Autowired
    private Random random;

    @Test
    public void testEncWithXor() throws Exception {
        byte[] data = new byte[32];
        random.nextBytes(data);
        ByteBuf buf = buffer(32, 36);

        ByteBuf raw = buf.copy(); // create copy of buf, because encoder due direct write to buf

        ByteBuf encoded = encoder.encWithXor(buf);

        // encoded array must be longer for 4 bytes
        assertTrue(raw.writerIndex() + 4 == encoded.writerIndex());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testEncWithXor_Exception() throws Exception {
        byte[] data = new byte[31];
        random.nextBytes(data);
        ByteBuf buf = wrappedBuffer(data);

        encoder.encWithXor(buf);
    }

    @Test
    public void appendPadding() {
        byte[] data = new byte[6];
        random.nextBytes(data);
        ByteBuf buf = buffer(8).writeBytes(data);

        assertThat(buf.readableBytes(), not(is(8)));

        buf = encoder.appendPadding(buf);

        assertThat(buf.readableBytes(), is(8));
    }
}