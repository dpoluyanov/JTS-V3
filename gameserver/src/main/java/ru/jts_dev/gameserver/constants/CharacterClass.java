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

package ru.jts_dev.gameserver.constants;

/**
 * @author Camelion
 * @since 17.01.16
 */
public enum CharacterClass {
    HUMAN_FIGHTER(0),

    TEMPORARY_STUB1(1), TEMPORARY_STUB2(2), TEMPORARY_STUB3(3), TEMPORARY_STUB4(4), TEMPORARY_STUB5(5),
    TEMPORARY_STUB6(6), TEMPORARY_STUB7(7), TEMPORARY_STUB8(8), TEMPORARY_STUB9(9),

    HUMAN_MAGICIAN(10),

    TEMPORARY_STUB11(11), TEMPORARY_STUB12(12), TEMPORARY_STUB13(13), TEMPORARY_STUB14(14), TEMPORARY_STUB15(15),
    TEMPORARY_STUB16(16), TEMPORARY_STUB17(17),

    ELF_FIGHTER(18),

    TEMPORARY_STUB19(19), TEMPORARY_STUB20(20), TEMPORARY_STUB21(21), TEMPORARY_STUB22(22), TEMPORARY_STUB23(23),
    TEMPORARY_STUB24(24),

    ELF_MAGICIAN(25),

    TEMPORARY_STUB26(26), TEMPORARY_STUB27(27), TEMPORARY_STUB28(28), TEMPORARY_STUB29(29), TEMPORARY_STUB30(30),

    DARKELF_FIGHTER(31),

    TEMPORARY_STUB32(32), TEMPORARY_STUB33(33), TEMPORARY_STUB34(34), TEMPORARY_STUB35(35), TEMPORARY_STUB36(36),
    TEMPORARY_STUB37(37),

    DARKELF_MAGICIAN(38),

    TEMPORARY_STUB39(39), TEMPORARY_STUB40(40), TEMPORARY_STUB41(41), TEMPORARY_STUB42(42), TEMPORARY_STUB43(43),

    ORC_FIGHTER(44),

    TEMPORARY_STUB45(45), TEMPORARY_STUB46(46), TEMPORARY_STUB47(47), TEMPORARY_STUB48(48),

    ORC_SHAMAN(49),

    TEMPORARY_STUB50(50), TEMPORARY_STUB51(51), TEMPORARY_STUB52(52),

    DWARF_APPRENTICE(53),

    // TODO: 27.01.16 temporary constant between dwarf and kamael classes

    KAMAEL_M_SOLDIER(123),
    KAMAEL_F_SOLDIER(124);

    private final int classId;

    CharacterClass(int classId) {
        this.classId = classId;
    }

    public int getId() {
        return classId;
    }
}
