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

package ru.jts_dev.gameserver.packets;

import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @author Camelion
 * @since 05.01.16
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
@Scope(SCOPE_PROTOTYPE)
public @interface Opcode {
    int CLIENT_SWITCH_OPCODE = 0xD0;

    /**
     * Alias for {@link #first()}.
     *
     * @see #first()
     */
    @AliasFor("first")
    int value() default CLIENT_SWITCH_OPCODE; // switch opcode

    /**
     * first packet opcode (byte)
     *
     * @return - expected opcode
     */
    @AliasFor("value")
    int first() default CLIENT_SWITCH_OPCODE; // switch opcode

    /**
     * second packet opcode (short)
     *
     * @return - expected opcode
     */
    int second() default Integer.MIN_VALUE;
}
