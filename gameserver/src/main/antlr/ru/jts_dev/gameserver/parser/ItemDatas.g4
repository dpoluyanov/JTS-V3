grammar ItemDatas;

import Lang;

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
slot_legs : 'slot_legs' '=' int_array;
slot_feet : 'slot_feet' '=' int_array;
slot_head : 'slot_head' '=' int_array;
slot_gloves : 'slot_gloves' '=' int_array;
slot_lhand : 'slot_lhand' '=' int_array;
slot_additional : 'slot_additional' '=' name_object;
set_skill : 'set_skill' '=' name_object;
set_effect_skill : 'set_effect_skill' '=' name_object;
set_additional_effect_skill : 'set_additional_effect_skill' '=' name_object;
set_additional2_condition : 'set_additional2_condition' '=' int_object;
set_additional2_effect_skill : 'set_additional2_effect_skill' '=' name_object;
str_inc : 'str_inc' '=' int_array2;
con_inc : 'con_inc' '=' int_array2;
dex_inc : 'dex_inc' '=' int_array2;
int_inc : 'int_inc' '=' int_array2;
men_inc : 'men_inc' '=' int_array2;
wit_inc : 'wit_inc' '=' int_array2;

item :
    'item_begin'
    item_class
    item_id
    name_object
    item_type
    slot_bit_type_wrapper
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
use_condition : 'use_condition' '=' (empty_array | '{' condition (';' condition)* '}');
equip_condition : 'equip_condition' '=' (empty_array | '{' condition (';' condition)* '}');
item_equip_option : 'item_equip_option' '=' (empty_array | identifier_array);
condition : '{' identifier_object (';' (int_object | int_array | identifier_array | category_array))? '}';

for_npc : 'for_npc' '=' int_object;
unequip_skill : 'unequip_skill' '=' identifier_array;

base_attribute_attack : 'base_attribute_attack' '=' attack_attribute;
attack_attribute : '{' attribute ';' int_object '}';
attribute : NONE | FIRE | EARTH;

html : 'html' '=' name_object;

base_attribute_defend : 'base_attribute_defend' '=' int_array6;

category : 'category' '=' empty_array;

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

weapon_type_wrapper : 'weapon_type' '=' weapon_type;
weapon_type : NONE | SWORD | BLUNT | BOW | POLE | DAGGER | DUAL | FIST | DUALFIST | FISHINGROD | RAPIER | ETC
    | ANCIENTSWORD | CROSSBOW | FLAG | OWNTHING | DUALDAGGER;

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
damage_range : 'damage_range' '=' int_array;

attack_speed : 'attack_speed' '=' int_object;

avoid_modify : 'avoid_modify' '=' int_object;

dual_fhit_rate : 'dual_fhit_rate' '=' int_object;

shield_defense : 'shield_defense' '=' int_object;
shield_defense_rate : 'shield_defense_rate' '=' int_object;

reuse_delay : 'reuse_delay' '=' int_object;

initial_count : 'initial_count' '=' int_object;
soulshot_count : 'soulshot_count' '=' int_object;
spiritshot_count : 'spiritshot_count' '=' int_object;

reduced_soulshot : 'reduced_soulshot' '=' int_array2;
reduced_spiritshot : 'reduced_spiritshot' '=' empty_array;
reduced_mp_consume : 'reduced_mp_consume' '=' int_array2;

immediate_effect : 'immediate_effect' '=' int_object;
ex_immediate_effect : 'ex_immediate_effect' '=' int_object;

use_skill_distime : 'use_skill_distime' '=' int_object;

drop_period : 'drop_period' '=' int_object;
duration : 'duration' '=' int_object;
period : 'period' '=' int_object;
equip_reuse_delay : 'equip_reuse_delay' '=' int_object;

capsuled_items : 'capsuled_items' '=' (empty_array | '{' capsuled_item (';' capsuled_item)* '}');
capsuled_item : '{' name_object ';' int_object ';' int_object ';' double_object '}';

price : 'price' '=' int_object;
default_price : 'default_price' '=' int_object;

item_skill : 'item_skill' '=' name_object;
critical_attack_skill : 'critical_attack_skill' '=' name_object;
attack_skill : 'attack_skill' '=' name_object;
magic_skill : 'magic_skill' '=' name_object (';' int_object)?;
item_skill_enchanted_four : 'item_skill_enchanted_four' '=' name_object;

crystal_type_wrapper : 'crystal_type' '=' crystal_type;
crystal_type: NONE | CRYSTAL_FREE | EVENT | D | C | B | A | S | S80 | S84;

crystal_count : 'crystal_count' '=' int_object;

material_type_wrapper : 'material_type' '=' material_type;
material_type : STEEL | FINE_STEEL | WOOD | CLOTH | LEATHER | BONE | BRONZE | ORIHARUKON | MITHRIL | DAMASCUS
    | ADAMANTAITE | BLOOD_STEEL | PAPER | GOLD | LIQUID | FISH | SILVER | CHRYSOLITE | CRYSTAL | HORN | SCALE_OF_DRAGON
    | COTTON | DYESTUFF | COBWEB | RUNE_XP | RUNE_SP | RUNE_REMOVE_PENALTY
    ;

consume_type_wrapper : 'consume_type' '=' consume_type;
consume_type: CONSUME_TYPE_NORMAL | CONSUME_TYPE_STACKABLE | CONSUME_TYPE_ASSET;

default_action_wrapper: 'default_action' '=' default_action;
default_action : ACTION_NONE | ACTION_EQUIP | ACTION_PEEL | ACTION_SKILL_REDUCE | ACTION_SOULSHOT | ACTION_RECIPE
    | ACTION_SKILL_MAINTAIN | ACTION_SPIRITSHOT | ACTION_DICE | ACTION_CALC | ACTION_SEED | ACTION_HARVEST
    | ACTION_CAPSULE | ACTION_XMAS_OPEN | ACTION_SHOW_HTML | ACTION_SHOW_SSQ_STATUS | ACTION_FISHINGSHOT
    | ACTION_SUMMON_SOULSHOT | ACTION_SUMMON_SPIRITSHOT | ACTION_CALL_SKILL | ACTION_SHOW_ADVENTURER_GUIDE_BOOK
    | ACTION_KEEP_EXP | ACTION_CREATE_MPCC | ACTION_NICK_COLOR | ACTION_HIDE_NAME | ACTION_START_QUEST;

recipe_id : 'recipe_id' '=' int_object;
blessed : 'blessed' '=' int_object;
weight : 'weight' '=' int_object;

item_multi_skill_list : 'item_multi_skill_list' '=' identifier_array;

delay_share_group: 'delay_share_group' '=' int_object;

etcitem_type_wrapper : 'etcitem_type' '=' etcitem_type;
etcitem_type : NONE | POTION | ARROW | SCRL_ENCHANT_AM | SCRL_ENCHANT_WP | SCROLL | MATERIAL | RECIPE | PET_COLLAR
    | CASTLE_GUARD | LOTTO | RACE_TICKET | DYE | SEED | SEED2 | CROP | MATURECROP | HARVEST | TICKET_OF_LORD | LURE
    | BLESS_SCRL_ENCHANT_AM | BLESS_SCRL_ENCHANT_WP | COUPON | ELIXIR | SCRL_ENCHANT_ATTR | BOLT | RUNE_SELECT
    | SCRL_INC_ENCHANT_PROP_WP | SCRL_INC_ENCHANT_PROP_AM | ANCIENT_CRYSTAL_ENCHANT_WP | ANCIENT_CRYSTAL_ENCHANT_AM
    | RUNE
    ;

armor_type_wrapper : 'armor_type' '=' armor_type;
armor_type : NONE | LIGHT | HEAVY | MAGIC | SIGIL;

slot_bit_type_wrapper : 'slot_bit_type' '=' '{' slot_bit_type (';' slot_bit_type)? '}';
slot_bit_type: NONE | RHAND | LRHAND | LHAND | CHEST | LEGS | FEET | HEAD | GLOVES | ONEPIECE | REAR | LEAR
    | LFINGER | RFINGER | NECK | BACK | UNDERWEAR | HAIR | HAIR2 | HAIRALL | ALLDRESS | RBRACELET | LBRACELET
    | WAIST
    | DECO1;

item_type : 'item_type' '=' item_class;
item_class : WEAPON | ARMOR | ETCITEM | ASSET | ACCESSARY | QUESTITEM;

item_id : int_object;

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

// Material types
STEEL : 'steel';
FINE_STEEL : 'fine_steel';
WOOD : 'wood';
CLOTH : 'cloth';
LEATHER : 'leather';
BONE : 'bone';
BRONZE : 'bronze';
ORIHARUKON : 'oriharukon';
MITHRIL : 'mithril';
DAMASCUS : 'damascus';
ADAMANTAITE : 'adamantaite';
BLOOD_STEEL : 'blood_steel';
PAPER : 'paper';
GOLD : 'gold';
LIQUID : 'liquid';
FISH : 'fish';
SILVER : 'silver';
CHRYSOLITE : 'chrysolite';
CRYSTAL : 'crystal';
HORN : 'horn';
SCALE_OF_DRAGON : 'scale_of_dragon';
COTTON : 'cotton';
DYESTUFF : 'dyestuff';
COBWEB : 'cobweb';
RUNE_XP : 'rune_xp';
RUNE_SP : 'rune_sp';
RUNE_REMOVE_PENALTY : 'rune_remove_penalty';

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