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

package ru.jts_dev.authserver.config;

import io.netty.buffer.ByteBuf;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ru.jts_dev.authserver.service.AuthSessionService;
import ru.jts_dev.authserver.util.Encoder;
import ru.jts_dev.common.config.UtilsConfig;
import ru.jts_dev.common.id.impl.bitset.BitSetIdPool;

import java.util.Random;

import static io.netty.buffer.Unpooled.buffer;
import static io.netty.buffer.Unpooled.wrappedBuffer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.expectThrows;

/**
 * @author Camelion
 * @since 02.12.15
 */
@SpringJUnitConfig({
        Encoder.class,
        UtilsConfig.class,
        AuthSessionService.class,
        KeyGenerationConfig.class,
        BitSetIdPool.class
})
public class EncoderTest {

    @Autowired
    private Encoder encoder;

    @Autowired
    private Random random;

    @Test
    public void testEncWithXor() throws Exception {
        byte[] data = new byte[32];
        random.nextBytes(data);
        ByteBuf buf = buffer(32, 48);

        ByteBuf raw = buf.copy(); // create copy of buf, because encoder due direct write to buf

        ByteBuf encoded = encoder.encWithXor(buf);

        // encoded array must be longer for 4 bytes
        assertThat(raw.writerIndex() + 16 == encoded.writerIndex());
    }

    @Test
    public void testEncWithXor_Exception() throws Exception {
        byte[] data = new byte[31];
        random.nextBytes(data);
        ByteBuf buf = wrappedBuffer(data);

        expectThrows(IndexOutOfBoundsException.class, () -> encoder.encWithXor(buf));
    }

    @Test
    public void appendPadding() {
        byte[] data = new byte[6];
        random.nextBytes(data);
        ByteBuf buf = buffer(8).writeBytes(data);

        assertThat(buf.readableBytes()).isNotEqualTo(8);

        buf = encoder.appendBlowFishPadding(buf);

        assertThat(buf.readableBytes()).isEqualTo(8);
    }
}