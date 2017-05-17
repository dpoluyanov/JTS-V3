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
 * @since 07.01.16
 */
public final class ItemTypes {
    public enum ArmorType {
        NONE, LIGHT, HEAVY, MAGIC, SIGIL
    }

    public enum WeaponType {
        NONE, SWORD, BLUNT, BOW, POLE, DAGGER,
        DUAL, FIST, DUALFIST, FISHINGROD,
        RAPIER, ANCIENTSWORD, CROSSBOW,
        FLAG, OWNTHING, DUALDAGGER, ETC
    }

    public enum EtcItemType {
        NONE, POTION, ARROW,
        SCRL_ENCHANT_AM, SCRL_ENCHANT_WP,
        SCROLL, MATERIAL, RECIPE,
        PET_COLLAR, CASTLE_GUARD, LOTTO,
        RACE_TICKET, DYE,
        SEED, SEED2, CROP,
        MATURECROP, HARVEST,
        TICKET_OF_LORD, LURE,
        BLESS_SCRL_ENCHANT_AM, BLESS_SCRL_ENCHANT_WP,
        COUPON, ELIXIR,
        SCRL_ENCHANT_ATTR,
        SCRL_INC_ENCHANT_PROP_WP, SCRL_INC_ENCHANT_PROP_AM,
        BOLT, ANCIENT_CRYSTAL_ENCHANT_WP, ANCIENT_CRYSTAL_ENCHANT_AM,
        RUNE_SELECT, RUNE
    }
}
