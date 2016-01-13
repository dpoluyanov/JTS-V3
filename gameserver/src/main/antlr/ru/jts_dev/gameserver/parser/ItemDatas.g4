grammar ItemDatas;

import Lang;

@header {
import ru.jts_dev.gameserver.constants.ConsumeType;
import ru.jts_dev.gameserver.constants.CrystalType;
import ru.jts_dev.gameserver.constants.DefaultAction;
import ru.jts_dev.gameserver.constants.ItemClass;
import ru.jts_dev.gameserver.constants.ItemTypes.*;
import ru.jts_dev.gameserver.constants.MaterialType;
import ru.jts_dev.gameserver.constants.SlotBitType;
import ru.jts_dev.gameserver.parser.data.item.ItemData.CapsuledItemData;
}

file : (item | set)+;

set :
    'set_begin'
    int_object
    slot_chest
    slot_legs?
    slot_head?
    slot_gloves?
    slot_feet?
    slot_lhand?
    slot_additional
    set_skill
    set_effect_skill
    set_additional_effect_skill
    (set_additional2_condition set_additional2_effect_skill)?
    str_inc
    con_inc
    dex_inc
    int_inc
    men_inc
    wit_inc
    'set_end'
    ;

slot_chest : 'slot_chest' '=' '{' int_object '}';
slot_legs : 'slot_legs' '=' int_list;
slot_feet : 'slot_feet' '=' int_list;
slot_head : 'slot_head' '=' int_list;
slot_gloves : 'slot_gloves' '=' int_list;
slot_lhand : 'slot_lhand' '=' int_list;
slot_additional : 'slot_additional' '=' name_object;
set_skill : 'set_skill' '=' name_object;
set_effect_skill : 'set_effect_skill' '=' name_object;
set_additional_effect_skill : 'set_additional_effect_skill' '=' name_object;
set_additional2_condition : 'set_additional2_condition' '=' int_object;
set_additional2_effect_skill : 'set_additional2_effect_skill' '=' name_object;
str_inc : 'str_inc' '=' int_list;
con_inc : 'con_inc' '=' int_list;
dex_inc : 'dex_inc' '=' int_list;
int_inc : 'int_inc' '=' int_list;
men_inc : 'men_inc' '=' int_list;
wit_inc : 'wit_inc' '=' int_list;

item :
    'item_begin'
    item_class
    item_id
    name_object
    item_type
    slot_bit_type_list
    armor_type_wrapper
    etcitem_type_wrapper
    delay_share_group
    item_multi_skill_list
    recipe_id
    blessed
    weight
    default_action_wrapper
    consume_type_wrapper
    initial_count
    soulshot_count
    spiritshot_count
    reduced_soulshot
    reduced_spiritshot
    reduced_mp_consume
    immediate_effect
    ex_immediate_effect
    drop_period
    duration
    use_skill_distime
    period
    equip_reuse_delay
    price
    default_price
    item_skill
    critical_attack_skill
    attack_skill
    magic_skill
    item_skill_enchanted_four
    capsuled_items
    material_type_wrapper
    crystal_type_wrapper
    crystal_count
    is_trade
    is_drop
    is_destruct
    is_private_store
    keep_type
    physical_damage
    random_damage
    weapon_type_wrapper
    critical
    hit_modify
    avoid_modify
    dual_fhit_rate
    shield_defense
    shield_defense_rate
    attack_range
    damage_range
    attack_speed
    reuse_delay
    mp_consume
    magical_damage
    durability
    damaged
    physical_defense
    magical_defense
    mp_bonus
    category
    enchanted
    base_attribute_attack
    base_attribute_defend
    html
    magic_weapon
    enchant_enable
    elemental_enable
    unequip_skill
    for_npc
    item_equip_option
    use_condition
    equip_condition
    is_olympiad_can_use
    can_move
    is_premium
    /* todo High Five additions */
    'item_end'
    ;

can_move : 'can_move' '=' int_object;
is_premium : 'is_premium' '=' int_object;

is_olympiad_can_use : 'is_olympiad_can_use' '=' int_object;
use_condition : 'use_condition' '=' (empty_list | '{' condition (';' condition)* '}');
equip_condition : 'equip_condition' '=' (empty_list | '{' condition (';' condition)* '}');
item_equip_option : 'item_equip_option' '=' (empty_list | identifier_list);
condition : '{' identifier_object (';' (int_object | int_list | identifier_list | category_list))? '}';

for_npc : 'for_npc' '=' int_object;
unequip_skill : 'unequip_skill' '=' identifier_list;

base_attribute_attack : 'base_attribute_attack' '=' attack_attribute;
attack_attribute : '{' attribute ';' int_object '}';
attribute : NONE | FIRE | EARTH;

html : 'html' '=' name_object;

base_attribute_defend : 'base_attribute_defend' '=' int_list;

category : 'category' '=' empty_list;

enchant_enable : 'enchant_enable' '=' int_object;
elemental_enable : 'elemental_enable' '=' int_object;
enchanted : 'enchanted' '=' int_object;

mp_consume : 'mp_consume' '=' int_object;
magical_damage : 'magical_damage' '=' int_object;
durability : 'durability' '=' int_object;
damaged : 'damaged' '=' int_object;
magic_weapon : 'magic_weapon' '=' int_object;

physical_defense : 'physical_defense' '=' int_object;
magical_defense : 'magical_defense' '=' int_object;
mp_bonus : 'mp_bonus' '=' int_object;

weapon_type_wrapper
    returns [WeaponType value]: 'weapon_type' '=' wt=weapon_type { $ctx.value = $wt.value; };
weapon_type
    returns [WeaponType value]:
    NONE { $ctx.value = WeaponType.NONE; }
    | SWORD { $ctx.value = WeaponType.SWORD; }
    | BLUNT { $ctx.value = WeaponType.BLUNT; }
    | BOW { $ctx.value = WeaponType.BOW; }
    | POLE { $ctx.value = WeaponType.POLE; }
    | DAGGER { $ctx.value = WeaponType.DAGGER; }
    | DUAL { $ctx.value = WeaponType.DUAL; }
    | FIST { $ctx.value = WeaponType.FIST; }
    | DUALFIST { $ctx.value = WeaponType.DUALFIST; }
    | FISHINGROD { $ctx.value = WeaponType.FISHINGROD; }
    | RAPIER { $ctx.value = WeaponType.RAPIER; }
    | ETC { $ctx.value = WeaponType.ETC; }
    | ANCIENTSWORD { $ctx.value = WeaponType.ANCIENTSWORD; }
    | CROSSBOW { $ctx.value = WeaponType.CROSSBOW; }
    | FLAG { $ctx.value = WeaponType.FLAG; }
    | OWNTHING { $ctx.value = WeaponType.OWNTHING; }
    | DUALDAGGER { $ctx.value = WeaponType.DUALDAGGER; }
    ;

is_trade : 'is_trade' '=' int_object;
is_drop : 'is_drop' '=' int_object;
is_destruct : 'is_destruct' '=' int_object;
is_private_store : 'is_private_store' '=' int_object;
keep_type : 'keep_type' '=' int_object;

physical_damage : 'physical_damage' '=' int_object;
random_damage : 'random_damage' '=' int_object;
critical : 'critical' '=' int_object;
hit_modify : 'hit_modify' '=' double_object;
attack_range : 'attack_range' '=' int_object;
damage_range : 'damage_range' '=' int_list;

attack_speed : 'attack_speed' '=' int_object;

avoid_modify : 'avoid_modify' '=' int_object;

dual_fhit_rate : 'dual_fhit_rate' '=' int_object;

shield_defense
    returns[int value]: 'shield_defense' '=' io=int_object {$ctx.value = $io.value;};
shield_defense_rate
    returns[int value]: 'shield_defense_rate' '=' io=int_object {$ctx.value = $io.value;};

reuse_delay : 'reuse_delay' '=' int_object;

initial_count
    returns[int value]: 'initial_count' '=' io=int_object {$ctx.value=$io.value;};
soulshot_count
    returns[int value]: 'soulshot_count' '=' io=int_object {$ctx.value=$io.value;};
spiritshot_count
    returns[int value]: 'spiritshot_count' '=' io=int_object {$ctx.value=$io.value;};

reduced_soulshot
    returns[List<Integer> value]
    @init{ $ctx.value = new ArrayList<>();}: 'reduced_soulshot' '=' il=int_list {$ctx.value = $il.value;};

reduced_spiritshot
    returns[List<Integer> value = new ArrayList<>();]: 'reduced_spiritshot' '=' empty_list;

reduced_mp_consume
    returns[List<Integer> value]: 'reduced_mp_consume' '=' il=int_list {$ctx.value=$il.value;};

immediate_effect
    returns[int value]: 'immediate_effect' '=' io=int_object {$ctx.value=$io.value;};

ex_immediate_effect
    returns[int value]: 'ex_immediate_effect' '=' io=int_object {$ctx.value=$io.value;};

use_skill_distime
    returns[int value]: 'use_skill_distime' '=' io=int_object {$ctx.value=$io.value;};

drop_period
    returns[int value]: 'drop_period' '=' io=int_object {$ctx.value=$io.value;};

duration
    returns[int value]: 'duration' '=' io=int_object {$ctx.value=$io.value;};

period
    returns[int value]: 'period' '=' io=int_object {$ctx.value=$io.value;};

equip_reuse_delay
    returns[int value]: 'equip_reuse_delay' '=' io=int_object {$ctx.value=$io.value;};

capsuled_items
    returns[List<CapsuledItemData> value = new ArrayList<>()]:
    'capsuled_items' '=' (empty_list | '{' ci=capsuled_item {$ctx.value.add($ci.value);}
    (';' ci=capsuled_item {$ctx.value.add($ci.value);})* '}');

capsuled_item
    returns[CapsuledItemData value]
    @after{$ctx.value = new CapsuledItemData($p1.value, $p2.value, $p3.value, $p4.value);}:
    '{' p1=name_object ';' p2=int_object ';' p3=int_object ';' p4=double_object '}';

price
    returns[long value]: 'price' '=' io=int_object {$ctx.value=$io.value;};

default_price
    returns[long value]: 'default_price' '=' io=int_object {$ctx.value=$io.value;};

item_skill
    returns[String value]: 'item_skill' '=' no=name_object {$ctx.value=$no.value;};
    
critical_attack_skill
    returns[String value]: 'critical_attack_skill' '=' no=name_object {$ctx.value=$no.value;};

attack_skill
    returns[String value]: 'attack_skill' '=' no=name_object {$ctx.value=$no.value;};

magic_skill
    returns[String value, int unk]:
    'magic_skill' '=' no=name_object {$ctx.value=$no.value;}
    (';' io=int_object {$ctx.unk = $io.value;})?;

item_skill_enchanted_four
    returns[String value]: 'item_skill_enchanted_four' '=' no=name_object {$ctx.value=$no.value;};

crystal_type_wrapper
    returns[CrystalType value]: 'crystal_type' '=' ct=crystal_type{$ctx.value=$ct.value;};

crystal_type
    returns[CrystalType value]:
    NONE {$ctx.value=CrystalType.NONE;}
    | CRYSTAL_FREE {$ctx.value=CrystalType.CRYSTAL_FREE;}
    | EVENT {$ctx.value=CrystalType.EVENT;}
    | D {$ctx.value=CrystalType.D;}
    | C {$ctx.value=CrystalType.C;}
    | B {$ctx.value=CrystalType.B;}
    | A {$ctx.value=CrystalType.A;}
    | S {$ctx.value=CrystalType.S;}
    | S80 {$ctx.value=CrystalType.S80;}
    | S84 {$ctx.value=CrystalType.S84;};

crystal_count
    returns[int value]: 'crystal_count' '=' io=int_object {$ctx.value=$io.value;};

material_type_wrapper
    returns[MaterialType value]: 'material_type' '=' mt=material_type {$ctx.value=$mt.value;};
material_type
    returns[MaterialType value]:
    STEEL {$ctx.value=MaterialType.STEEL;}
    | FINE_STEEL {$ctx.value=MaterialType.FINE_STEEL;}
    | WOOD {$ctx.value=MaterialType.WOOD;}
    | CLOTH {$ctx.value=MaterialType.CLOTH;}
    | LEATHER {$ctx.value=MaterialType.LEATHER;}
    | BONE {$ctx.value=MaterialType.BONE;}
    | BRONZE {$ctx.value=MaterialType.BRONZE;}
    | ORIHARUKON {$ctx.value=MaterialType.ORIHARUKON;}
    | MITHRIL {$ctx.value=MaterialType.MITHRIL;}
    | DAMASCUS {$ctx.value=MaterialType.DAMASCUS;}
    | ADAMANTAITE {$ctx.value=MaterialType.ADAMANTAITE;}
    | BLOOD_STEEL {$ctx.value=MaterialType.BLOOD_STEEL;}
    | PAPER {$ctx.value=MaterialType.PAPER;}
    | GOLD {$ctx.value=MaterialType.GOLD;}
    | LIQUID {$ctx.value=MaterialType.LIQUID;}
    | FISH {$ctx.value=MaterialType.FISH;}
    | SILVER {$ctx.value=MaterialType.SILVER;}
    | CHRYSOLITE {$ctx.value=MaterialType.CHRYSOLITE;}
    | CRYSTAL {$ctx.value=MaterialType.CRYSTAL;}
    | HORN {$ctx.value=MaterialType.HORN;}
    | SCALE_OF_DRAGON {$ctx.value=MaterialType.SCALE_OF_DRAGON;}
    | COTTON {$ctx.value=MaterialType.COTTON;}
    | DYESTUFF {$ctx.value=MaterialType.DYESTUFF;}
    | COBWEB {$ctx.value=MaterialType.COBWEB;}
    | RUNE_XP {$ctx.value=MaterialType.RUNE_XP;}
    | RUNE_SP {$ctx.value=MaterialType.RUNE_SP;}
    | RUNE_REMOVE_PENALTY {$ctx.value=MaterialType.RUNE_REMOVE_PENALTY;}
    ;

consume_type_wrapper
    returns[ConsumeType value]: 'consume_type' '=' ct=consume_type {$ctx.value=$ct.value;};
consume_type
    returns[ConsumeType value]:
    CONSUME_TYPE_NORMAL {$ctx.value=ConsumeType.CONSUME_TYPE_NORMAL;}
    | CONSUME_TYPE_STACKABLE {$ctx.value=ConsumeType.CONSUME_TYPE_STACKABLE;}
    | CONSUME_TYPE_ASSET {$ctx.value=ConsumeType.CONSUME_TYPE_ASSET;};

default_action_wrapper
    returns[DefaultAction value]: 'default_action' '=' da=default_action {$ctx.value=$da.value;};
default_action
    returns[DefaultAction value]:
    ACTION_NONE {$ctx.value=DefaultAction.ACTION_NONE;}
    | ACTION_EQUIP {$ctx.value=DefaultAction.ACTION_EQUIP;}
    | ACTION_PEEL {$ctx.value=DefaultAction.ACTION_PEEL;}
    | ACTION_SKILL_REDUCE {$ctx.value=DefaultAction.ACTION_SKILL_REDUCE;}
    | ACTION_SOULSHOT {$ctx.value=DefaultAction.ACTION_SOULSHOT;}
    | ACTION_RECIPE {$ctx.value=DefaultAction.ACTION_RECIPE;}
    | ACTION_SKILL_MAINTAIN {$ctx.value=DefaultAction.ACTION_SKILL_MAINTAIN;}
    | ACTION_SPIRITSHOT {$ctx.value=DefaultAction.ACTION_SPIRITSHOT;}
    | ACTION_DICE {$ctx.value=DefaultAction.ACTION_DICE;}
    | ACTION_CALC {$ctx.value=DefaultAction.ACTION_CALC;}
    | ACTION_SEED {$ctx.value=DefaultAction.ACTION_SEED;}
    | ACTION_HARVEST {$ctx.value=DefaultAction.ACTION_HARVEST;}
    | ACTION_CAPSULE {$ctx.value=DefaultAction.ACTION_CAPSULE;}
    | ACTION_XMAS_OPEN {$ctx.value=DefaultAction.ACTION_XMAS_OPEN;}
    | ACTION_SHOW_HTML {$ctx.value=DefaultAction.ACTION_SHOW_HTML;}
    | ACTION_SHOW_SSQ_STATUS {$ctx.value=DefaultAction.ACTION_SHOW_SSQ_STATUS;}
    | ACTION_FISHINGSHOT {$ctx.value=DefaultAction.ACTION_FISHINGSHOT;}
    | ACTION_SUMMON_SOULSHOT {$ctx.value=DefaultAction.ACTION_SUMMON_SOULSHOT;}
    | ACTION_SUMMON_SPIRITSHOT {$ctx.value=DefaultAction.ACTION_SUMMON_SPIRITSHOT;}
    | ACTION_CALL_SKILL {$ctx.value=DefaultAction.ACTION_CALL_SKILL;}
    | ACTION_SHOW_ADVENTURER_GUIDE_BOOK {$ctx.value=DefaultAction.ACTION_SHOW_ADVENTURER_GUIDE_BOOK;}
    | ACTION_KEEP_EXP {$ctx.value=DefaultAction.ACTION_KEEP_EXP;}
    | ACTION_CREATE_MPCC {$ctx.value=DefaultAction.ACTION_CREATE_MPCC;}
    | ACTION_NICK_COLOR {$ctx.value=DefaultAction.ACTION_NICK_COLOR;}
    | ACTION_HIDE_NAME {$ctx.value=DefaultAction.ACTION_HIDE_NAME;}
    | ACTION_START_QUEST {$ctx.value=DefaultAction.ACTION_START_QUEST;}
    ;

recipe_id
    returns[int value]: 'recipe_id' '=' io=int_object {$ctx.value = $io.value;};
blessed
    returns[int value] : 'blessed' '=' io=int_object {$ctx.value = $io.value;};
weight
    returns[int value]: 'weight' '=' io=int_object {$ctx.value = $io.value;};

item_multi_skill_list
    returns[List<String> value]: 'item_multi_skill_list' '=' il=identifier_list {$ctx.value = $il.value;};

delay_share_group returns[int value]: 'delay_share_group' '=' io=int_object { $ctx.value = $io.value;};

etcitem_type_wrapper
    returns [EtcItemType value]: 'etcitem_type' '=' et=etcitem_type { $ctx.value = $et.value; };
etcitem_type
    returns [EtcItemType value]:
    NONE { $ctx.value = EtcItemType.NONE; }
    | POTION { $ctx.value = EtcItemType.POTION; }
    | ARROW { $ctx.value = EtcItemType.ARROW; }
    | SCRL_ENCHANT_AM { $ctx.value = EtcItemType.SCRL_ENCHANT_AM; }
    | SCRL_ENCHANT_WP { $ctx.value = EtcItemType.SCRL_ENCHANT_WP; }
    | SCROLL { $ctx.value = EtcItemType.SCROLL; }
    | MATERIAL { $ctx.value = EtcItemType.MATERIAL; }
    | RECIPE { $ctx.value = EtcItemType.RECIPE; }
    | PET_COLLAR { $ctx.value = EtcItemType.PET_COLLAR; }
    | CASTLE_GUARD { $ctx.value = EtcItemType.CASTLE_GUARD; }
    | LOTTO { $ctx.value = EtcItemType.LOTTO; }
    | RACE_TICKET { $ctx.value = EtcItemType.RACE_TICKET; }
    | DYE { $ctx.value = EtcItemType.DYE; }
    | SEED { $ctx.value = EtcItemType.SEED; }
    | SEED2 { $ctx.value = EtcItemType.SEED2; }
    | CROP { $ctx.value = EtcItemType.CROP; }
    | MATURECROP { $ctx.value = EtcItemType.MATURECROP; }
    | HARVEST { $ctx.value = EtcItemType.HARVEST; }
    | TICKET_OF_LORD { $ctx.value = EtcItemType.TICKET_OF_LORD; }
    | LURE { $ctx.value = EtcItemType.LURE; }
    | BLESS_SCRL_ENCHANT_AM { $ctx.value = EtcItemType.BLESS_SCRL_ENCHANT_AM; }
    | BLESS_SCRL_ENCHANT_WP { $ctx.value = EtcItemType.BLESS_SCRL_ENCHANT_WP; }
    | COUPON { $ctx.value = EtcItemType.COUPON; }
    | ELIXIR { $ctx.value = EtcItemType.ELIXIR; }
    | SCRL_ENCHANT_ATTR { $ctx.value = EtcItemType.SCRL_ENCHANT_ATTR; }
    | BOLT { $ctx.value = EtcItemType.BOLT; }
    | RUNE_SELECT { $ctx.value = EtcItemType.RUNE_SELECT; }
    | SCRL_INC_ENCHANT_PROP_WP { $ctx.value = EtcItemType.SCRL_INC_ENCHANT_PROP_WP; }
    | SCRL_INC_ENCHANT_PROP_AM { $ctx.value = EtcItemType.SCRL_INC_ENCHANT_PROP_AM; }
    | ANCIENT_CRYSTAL_ENCHANT_WP { $ctx.value = EtcItemType.ANCIENT_CRYSTAL_ENCHANT_WP; }
    | ANCIENT_CRYSTAL_ENCHANT_AM { $ctx.value = EtcItemType.ANCIENT_CRYSTAL_ENCHANT_AM; }
    | RUNE { $ctx.value = EtcItemType.RUNE; }
    ;

armor_type_wrapper
    returns [ArmorType value] : 'armor_type' '=' at=armor_type { $ctx.value = $at.value; };
armor_type
    returns[ArmorType value]:
    NONE { $ctx.value = ArmorType.NONE; }
    | LIGHT { $ctx.value = ArmorType.LIGHT; }
    | HEAVY { $ctx.value = ArmorType.HEAVY; }
    | MAGIC { $ctx.value = ArmorType.MAGIC; }
    | SIGIL { $ctx.value = ArmorType.SIGIL; };

slot_bit_type_list
    returns [List<SlotBitType> value = new ArrayList<>();]:
    'slot_bit_type' '=' '{'
    sbt=slot_bit_type { $ctx.value.add($sbt.value); }
    (';' sbt=slot_bit_type { $ctx.value.add($sbt.value); })? '}';
slot_bit_type returns [SlotBitType value]:
    NONE { $ctx.value = SlotBitType.NONE; }
    | RHAND { $ctx.value = SlotBitType.RHAND; }
    | LRHAND { $ctx.value = SlotBitType.LRHAND; }
    | LHAND { $ctx.value = SlotBitType.LHAND; }
    | CHEST { $ctx.value = SlotBitType.CHEST; }
    | LEGS { $ctx.value = SlotBitType.LEGS; }
    | FEET { $ctx.value = SlotBitType.FEET; }
    | HEAD { $ctx.value = SlotBitType.HEAD; }
    | GLOVES { $ctx.value = SlotBitType.GLOVES; }
    | ONEPIECE { $ctx.value = SlotBitType.ONEPIECE; }
    | REAR { $ctx.value = SlotBitType.REAR; }
    | LEAR { $ctx.value = SlotBitType.LEAR; }
    | LFINGER { $ctx.value = SlotBitType.LFINGER; }
    | RFINGER { $ctx.value = SlotBitType.RFINGER; }
    | NECK { $ctx.value = SlotBitType.NECK; }
    | BACK { $ctx.value = SlotBitType.BACK; }
    | UNDERWEAR { $ctx.value = SlotBitType.UNDERWEAR; }
    | HAIR { $ctx.value = SlotBitType.HAIR; }
    | HAIR2 { $ctx.value = SlotBitType.HAIR2; }
    | HAIRALL { $ctx.value = SlotBitType.HAIRALL; }
    | ALLDRESS { $ctx.value = SlotBitType.ALLDRESS; }
    | RBRACELET { $ctx.value = SlotBitType.RBRACELET; }
    | LBRACELET { $ctx.value = SlotBitType.LBRACELET; }
    | WAIST { $ctx.value = SlotBitType.WAIST; }
    | DECO1 { $ctx.value = SlotBitType.DECO1; };

item_type
    returns [ItemClass value]: 'item_type' '=' ic=item_class { $ctx.value = $ic.value;};
item_class
    returns [ItemClass value]:
    WEAPON { $ctx.value = ItemClass.WEAPON; }
    | ARMOR { $ctx.value = ItemClass.ARMOR; }
    | ETCITEM { $ctx.value = ItemClass.ETCITEM; }
    | ASSET { $ctx.value = ItemClass.ASSET; }
    | ACCESSARY { $ctx.value = ItemClass.ACCESSARY; }
    | QUESTITEM { $ctx.value = ItemClass.QUESTITEM; };

item_id returns [int value]: io=int_object { $ctx.value = $io.value; } ;

// item classes
WEAPON : 'weapon';
ARMOR : 'armor';
ETCITEM : 'etcitem';
ASSET : 'asset';
ACCESSARY : 'accessary';
QUESTITEM : 'questitem';

// Etcitem types
POTION : 'potion';
ARROW : 'arrow';
SCRL_ENCHANT_AM : 'scrl_enchant_am';
SCRL_ENCHANT_WP : 'scrl_enchant_wp';
SCROLL : 'scroll';
MATERIAL : 'material';
RECIPE : 'recipe';
PET_COLLAR : 'pet_collar';
CASTLE_GUARD : 'castle_guard';
LOTTO : 'lotto';
RACE_TICKET : 'race_ticket';
DYE : 'dye';
SEED : 'seed';
SEED2 : 'seed2';
CROP : 'crop';
MATURECROP : 'maturecrop';
HARVEST : 'harvest';
TICKET_OF_LORD : 'ticket_of_lord';
LURE : 'lure';
BLESS_SCRL_ENCHANT_AM : 'bless_scrl_enchant_am';
BLESS_SCRL_ENCHANT_WP : 'bless_scrl_enchant_wp';
COUPON : 'coupon';
ELIXIR : 'elixir';
SCRL_ENCHANT_ATTR : 'scrl_enchant_attr';
SCRL_INC_ENCHANT_PROP_WP : 'scrl_inc_enchant_prop_wp';
SCRL_INC_ENCHANT_PROP_AM : 'scrl_inc_enchant_prop_am';
BOLT : 'bolt';
ANCIENT_CRYSTAL_ENCHANT_WP : 'ancient_crystal_enchant_wp';
ANCIENT_CRYSTAL_ENCHANT_AM : 'ancient_crystal_enchant_am';
RUNE_SELECT : 'rune_select';
RUNE : 'rune';

// Action types
ACTION_NONE : 'action_none';
ACTION_EQUIP : 'action_equip';
ACTION_PEEL : 'action_peel';
ACTION_SKILL_REDUCE : 'action_skill_reduce';
ACTION_SHOW_HTML : 'action_show_html';
ACTION_SOULSHOT : 'action_soulshot';
ACTION_RECIPE : 'action_recipe';
ACTION_SKILL_MAINTAIN : 'action_skill_maintain';
ACTION_SPIRITSHOT : 'action_spiritshot';
ACTION_DICE : 'action_dice';
ACTION_CALC : 'action_calc';
ACTION_SEED : 'action_seed';
ACTION_HARVEST : 'action_harvest';
ACTION_CAPSULE : 'action_capsule';
ACTION_XMAS_OPEN : 'action_xmas_open';
ACTION_SHOW_SSQ_STATUS : 'action_show_ssq_status';
ACTION_FISHINGSHOT : 'action_fishingshot';
ACTION_SUMMON_SOULSHOT : 'action_summon_soulshot';
ACTION_SUMMON_SPIRITSHOT : 'action_summon_spiritshot';
ACTION_CALL_SKILL : 'action_call_skill';
ACTION_SHOW_ADVENTURER_GUIDE_BOOK : 'action_show_adventurer_guide_book';
ACTION_KEEP_EXP : 'action_keep_exp';
ACTION_CREATE_MPCC : 'action_create_mpcc';
ACTION_NICK_COLOR : 'action_nick_color';
ACTION_HIDE_NAME : 'action_hide_name';
ACTION_START_QUEST : 'action_start_quest';

// Consume types
CONSUME_TYPE_NORMAL: 'consume_type_normal';
CONSUME_TYPE_STACKABLE: 'consume_type_stackable';
CONSUME_TYPE_ASSET: 'consume_type_asset';

// Crystal types
CRYSTAL_FREE: 'crystal_free';
EVENT: 'event';
D: 'd';
C: 'c';
B: 'b';
A: 'a';
S80: 's80';
S84: 's84';
S: 's';

// Attributes
FIRE : 'fire';
EARTH : 'earth';