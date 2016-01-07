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
