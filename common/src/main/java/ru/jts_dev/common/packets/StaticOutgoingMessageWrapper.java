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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MutableMessageHeaders;

/**
 * Represent immutable static packet. All subclasses of this class should be immutable too.
 * This class use {@link Object#clone()} to perform copy of it object.
 * It means, that all fields of this class (and subclasses) wil be cloned lazily
 * and must be guarantee immutable.
 *
 * @author Camelion
 * @see Object#clone()
 * @since 27.01.16
 */
public abstract class StaticOutgoingMessageWrapper extends OutgoingMessageWrapper implements Cloneable {
    private static final Logger logger = LoggerFactory.getLogger(StaticOutgoingMessageWrapper.class);

    protected StaticOutgoingMessageWrapper() {
        super(true);
    }

    /**
     * Check that message is static, and perform cloning buffer and headers to new object
     * with deep copy of mutable {@link #buffer} and {@link #headers}.
     * Also, sets {@link #static_} to {@code false}.
     * Other fields will be copied as-is, and should be guaranteed immutable,
     * because this method is final, and there is no other way in which copy of this fields can be obtained.
     *
     * @return - cloned message
     * @throws CloneNotSupportedException - if
     * @see Object#clone()
     */
    @Override
    public final OutgoingMessageWrapper clone() throws CloneNotSupportedException {
        if (!static_)
            throw new CloneNotSupportedException("Clone not supported for non-static messages");
        logger.trace("Cloning {}, because it is static message", getClass().getSimpleName());
        final OutgoingMessageWrapper msg = (OutgoingMessageWrapper) super.clone();
        msg.headers = new MutableMessageHeaders(headers);
        msg.buffer = buffer.copy();
        msg.static_ = false;

        return msg;
    }
}
