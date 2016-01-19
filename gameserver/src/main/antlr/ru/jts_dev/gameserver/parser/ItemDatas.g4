grammar ItemDatas;

import Lang;

@header {
import ru.jts_dev.gameserver.constants.AttributeType;
import ru.jts_dev.gameserver.constants.ConsumeType;
import ru.jts_dev.gameserver.constants.CrystalType;
import ru.jts_dev.gameserver.constants.DefaultAction;
import ru.jts_dev.gameserver.constants.ItemClass;
import ru.jts_dev.gameserver.constants.ItemTypes.*;
import ru.jts_dev.gameserver.constants.MaterialType;
import ru.jts_dev.gameserver.constants.SlotBitType;
import ru.jts_dev.gameserver.parser.data.item.ItemData.AttributeAttack;
import ru.jts_dev.gameserver.parser.data.item.ItemData.CapsuledItemData;
import ru.jts_dev.gameserver.parser.data.item.condition.*;
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

can_move
    returns[boolean value]: 'can_move' '=' bo=bool_object {$ctx.value = $bo.value;};
is_premium
    returns[boolean value]: 'is_premium' '=' bo=bool_object {$ctx.value = $bo.value;};

is_olympiad_can_use
    returns[boolean value]: 'is_olympiad_can_use' '=' bo=bool_object {$ctx.value = $bo.value;};

use_condition
    returns[List<Condition> value = new ArrayList<>()]:
    'use_condition' '='
    (empty_list
    | '{' c=condition {$ctx.value.add($c.value);} (';' c=condition {$ctx.value.add($c.value);})* '}');

equip_condition
    returns[List<Condition> value = new ArrayList<>()]:
    'equip_condition' '='
    (empty_list
    | '{' c=condition {$ctx.value.add($c.value);} (';' c=condition {$ctx.value.add($c.value);})* '}');

item_equip_option
    returns[List<String> value]: 'item_equip_option' '=' il=identifier_list {$ctx.value = $il.value;};

condition
    returns[Condition value]:
    '{' (ecr_c=ec_race_condition {$ctx.value = $ecr_c.value;}
    | ute_c=uc_transmode_exclude_condition {$ctx.value = $ute_c.value;}
    | ecc_c=ec_category_condition {$ctx.value = $ecc_c.value;}
    | ucc_c=uc_category_condition {$ctx.value = $ucc_c.value;}
    | ucr_c=uc_race_condition {$ctx.value = $ucr_c.value;}
    | ech_c=ec_hero_condition {$ctx.value = $ech_c.value;}
    | eccast_c=ec_castle_condition {$ctx.value = $eccast_c.value;}
    | ecs_c=ec_sex_condition {$ctx.value = $ecs_c.value;}
    | eca_c=ec_agit_condition {$ctx.value = $eca_c.value;}
    | eccn_c=ec_castle_num_condition {$ctx.value = $eccn_c.value;}
    | ecacad_c=ec_academy_condition {$ctx.value = $ecacad_c.value;}
    | ecsc_c=ec_social_class_condition {$ctx.value = $ecsc_c.value;}
    | ucl_c=uc_level_condition {$ctx.value = $ucl_c.value;}
    | ucrl_c=uc_required_level_condition {$ctx.value = $ucrl_c.value;}
    | ecrl_c=ec_required_level_condition {$ctx.value = $ecrl_c.value;}
    | ucrp_c=uc_restart_point_condition {$ctx.value = $ucrp_c.value;}
    | ecn_c=ec_nobless_condition {$ctx.value = $ecn_c.value;}
    | eccl_c=ec_clan_leader_condition {$ctx.value = $eccl_c.value;}
    | ecsj_c=ec_subjob_condition {$ctx.value = $ecsj_c.value;}
    | ucti_c=uc_transmode_include_condition {$ctx.value = $ucti_c.value;}
    | ucin_c=uc_inzone_num_condition {$ctx.value = $ucin_c.value;}
    | ecin_c=ec_inzone_num_condition {$ctx.value = $ecin_c.value;}
    | ecchao_c=ec_chao_condtion {$ctx.value = $ecchao_c.value;}
    | ucsf_c=uc_in_residence_siege_field_condition {$ctx.value = $ucsf_c.value;}
    | ecf_c=ec_fortress_condition {$ctx.value = $ecf_c.value;}
    | ecan_c=ec_agit_num_condition {$ctx.value = $ecan_c.value;}
    | io=identifier_object {System.out.println($io.value);}
     )
    '}';

ec_race_condition
    returns[EcRace value]:
    'ec_race' ';' il=int_list {$ctx.value = new EcRace($il.value);};
uc_race_condition
    returns[UcRace value]:
    'uc_race' ';' il=int_list {$ctx.value = new UcRace($il.value);};

uc_transmode_exclude_condition
    returns[UcTransmodeExclude value]:
    'uc_transmode_exclude' ';' il=identifier_list {$ctx.value = new UcTransmodeExclude($il.value);};
ec_category_condition
    returns[EcCategory value]:
    'ec_category' ';' cl=category_list {$ctx.value = new EcCategory($cl.value);};

uc_category_condition
    returns[UcCategory value]:
    'uc_category' ';' cl=category_list {$ctx.value = new UcCategory($cl.value);};

ec_hero_condition
    returns[EcHero value]:
    'ec_hero' ';'  bo=bool_object {$ctx.value = new EcHero($bo.value);};

ec_castle_condition
    returns[EcCastle value]:
    'ec_castle' ';'  bo=bool_object {$ctx.value = new EcCastle($bo.value);};

ec_sex_condition
    returns[EcSex value]:
    'ec_sex' ';' io=int_object {$ctx.value = new EcSex($io.value);};
ec_agit_condition
    returns[EcAgit value]:
    'ec_agit' ';' bo=bool_object {$ctx.value = new EcAgit($bo.value);};
ec_castle_num_condition
    returns[EcCastleNum value]:
    'ec_castle_num' ';' '{' io=int_object '}' {$ctx.value = new EcCastleNum($io.value);};
ec_academy_condition
    returns[EcAcademy value]:
    'ec_academy' ';' bo=bool_object {$ctx.value = new EcAcademy($bo.value);};
ec_social_class_condition
    returns[EcSocialClass value]:
    'ec_social_class' ';' bo=byte_object {$ctx.value = new EcSocialClass($bo.value);};
uc_level_condition
    returns[UcLevel value]:
    'uc_level' ';' '{' min=int_object ';' max=int_object '}' {$ctx.value = new UcLevel($min.value, $max.value);};
uc_required_level_condition
    returns[UcRequiredLevel value]:
    'uc_requiredlevel' ';' io=int_object {$ctx.value = new UcRequiredLevel($io.value);};
ec_required_level_condition
    returns[EcRequiredLevel value]:
    'ec_requiredlevel' ';' io=int_object {$ctx.value = new EcRequiredLevel($io.value);};
uc_restart_point_condition
    returns[UcRestartPoint value]:
    'uc_restart_point' ';' io=int_object {$ctx.value = new UcRestartPoint($io.value);};
ec_nobless_condition
    returns[EcNobless value]:
    'ec_nobless' ';' bo=bool_object {$ctx.value = new EcNobless($bo.value);};
ec_clan_leader_condition
    returns[EcClanLeader value]:
    'ec_clan_leader' ';' bo=bool_object {$ctx.value = new EcClanLeader($bo.value);};
ec_subjob_condition
    returns[EcSubJob value]:
    'ec_subjob' ';' bo=bool_object {$ctx.value = new EcSubJob($bo.value);};
uc_transmode_include_condition
    returns[UcTransmodeInclude value]:
    'uc_transmode_include' ';' il=identifier_list {$ctx.value = new UcTransmodeInclude($il.value);};
uc_inzone_num_condition
    returns[UcInzoneNum value]:
    'uc_inzone_num' ';' il=int_list {$ctx.value = new UcInzoneNum($il.value);};
ec_inzone_num_condition
    returns[EcInzoneNum value]:
    'ec_inzone_num' ';' il=int_list {$ctx.value = new EcInzoneNum($il.value);};
ec_chao_condtion
    returns[EcChao value]:
    'ec_chao' ';' bo=bool_object {$ctx.value = new EcChao($bo.value);};
uc_in_residence_siege_field_condition
    returns[UcInResidenceSiegeFlag value]:
    'uc_in_residence_siege_field' {$ctx.value = new UcInResidenceSiegeFlag();};
ec_fortress_condition
    returns[EcFortress value]:
    'ec_fortress' ';' bo=bool_object {$ctx.value = new EcFortress($bo.value);};
ec_agit_num_condition
    returns[EcAgitNum value]:
    'ec_agit_num' ';' '{' io=int_object '}' {$ctx.value = new EcAgitNum($io.value);};

for_npc
    returns[boolean value]: 'for_npc' '=' bo=bool_object {$ctx.value = $bo.value;};
unequip_skill
    returns[List<String> value]: 'unequip_skill' '=' il=identifier_list {$ctx.value = $il.value;};

base_attribute_attack
    returns[AttributeAttack value]: 'base_attribute_attack' '=' aa=attack_attribute {$ctx.value = $aa.value;};
attack_attribute
    returns[AttributeAttack value]
    :'{' attribute ';' io=int_object '}' {$ctx.value = new AttributeAttack($attribute.value, $io.value);};

attribute
    returns[AttributeType value]:
    NONE {$ctx.value = AttributeType.NONE;}
    | FIRE {$ctx.value = AttributeType.FIRE;}
    | EARTH {$ctx.value = AttributeType.EARTH;};

html
    returns[String value]: 'html' '=' no=name_object {$ctx.value = $no.value;};

base_attribute_defend
    returns[List<Integer> value]: 'base_attribute_defend' '=' il=int_list {$ctx.value = $il.value;};

category
    returns[List<String> value = new ArrayList<>();]: 'category' '=' empty_list;

enchant_enable
    returns[int value]: 'enchant_enable' '=' io=int_object {$ctx.value = $io.value;};
elemental_enable
    returns[boolean value]: 'elemental_enable' '=' bo=bool_object {$ctx.value = $bo.value;};
enchanted
    returns[int value]: 'enchanted' '=' io=int_object {$ctx.value = $io.value;};

mp_consume
    returns[int value]: 'mp_consume' '=' io=int_object {$ctx.value = $io.value;};
magical_damage
    returns[int value]: 'magical_damage' '=' io=int_object {$ctx.value = $io.value;};
durability
    returns[int value]: 'durability' '=' io=int_object {$ctx.value = $io.value;};
damaged
    returns[boolean value]: 'damaged' '=' bo=bool_object {$ctx.value = $bo.value;};
magic_weapon
    returns[boolean value]: 'magic_weapon' '=' bo=bool_object {$ctx.value = $bo.value;};

physical_defense
    returns[int value]: 'physical_defense' '=' io=int_object {$ctx.value = $io.value;};
magical_defense
    returns[int value]: 'magical_defense' '=' io=int_object {$ctx.value = $io.value;};
mp_bonus
    returns[int value]: 'mp_bonus' '=' io=int_object {$ctx.value = $io.value;};

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

is_trade
    returns [boolean value]: 'is_trade' '=' bo=bool_object {$ctx.value=$bo.value;};
is_drop
    returns [boolean value]: 'is_drop' '=' bo=bool_object {$ctx.value=$bo.value;};
is_destruct
    returns [boolean value]: 'is_destruct' '=' bo=bool_object {$ctx.value=$bo.value;};
is_private_store
    returns [boolean value]: 'is_private_store' '=' bo=bool_object {$ctx.value=$bo.value;};
keep_type
    returns [byte value]: 'keep_type' '=' bo=byte_object {$ctx.value=$bo.value;};

physical_damage
    returns[int value]: 'physical_damage' '=' io=int_object {$ctx.value=$io.value;};
random_damage
    returns[int value]: 'random_damage' '=' io=int_object {$ctx.value=$io.value;};
critical
    returns[int value]: 'critical' '=' io=int_object {$ctx.value=$io.value;};
hit_modify
    returns[double value]: 'hit_modify' '=' d=double_object {$ctx.value=$d.value;};

attack_range
    returns[int value]: 'attack_range' '=' io=int_object {$ctx.value=$io.value;};

damage_range
    returns[List<Integer> value]: 'damage_range' '=' il=int_list {$ctx.value = $il.value;};

attack_speed
    returns[int value]: 'attack_speed' '=' io=int_object {$ctx.value = $io.value;};

avoid_modify
    returns[int value]: 'avoid_modify' '=' io=int_object {$ctx.value=$io.value;};

dual_fhit_rate
    returns[int value]: 'dual_fhit_rate' '=' io = int_object {$ctx.value=$io.value;};

shield_defense
    returns[int value]: 'shield_defense' '=' io=int_object {$ctx.value = $io.value;};
shield_defense_rate
    returns[int value]: 'shield_defense_rate' '=' io=int_object {$ctx.value = $io.value;};

reuse_delay
    returns[int value]: 'reuse_delay' '=' io=int_object {$ctx.value = $io.value;};

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