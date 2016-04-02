grammar Lang;

@header {
// For vector3D_object Rule
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import ru.jts_dev.gameserver.constants.AttributeType;
import ru.jts_dev.gameserver.parser.data.item.ItemData.AttributeAttack;
}

identifier_object
    returns [String value]
    @after {$ctx.value = $text;} :
    // workaround about name conflicts (itemdata.txt)
    DAGGER | BOW | CROSSBOW | RAPIER | GLOVES | STEEL | LEATHER | ORIHARUKON | 'slot_lhand'
    | NAME | NONE | ORC
    ;

bool_object
    returns [boolean value]
    @after {$ctx.value = $text.equals("1");}:
    BOOLEAN;

byte_object
    returns [byte value]
     @after {$ctx.value = Byte.valueOf($text);}:
     BOOLEAN | INTEGER;

int_object
    returns [int value]
    @after {$ctx.value = Integer.valueOf($text);}:
    BOOLEAN | INTEGER;

long_object
    returns [long value]
    @after {$ctx.value = Long.valueOf($text);}:
    BOOLEAN | INTEGER;


double_object
    returns [double value]
    @after {$ctx.value = Double.valueOf($text);}:
    BOOLEAN | INTEGER | DOUBLE;

name_object
    returns [String value]:
    '[' io=identifier_object ']' { $ctx.value = $io.value; };

category_object: CATEGORY;

vector3D_object
    returns[Vector3D value]
    @after{$ctx.value = new Vector3D($x.value, $y.value, $z.value);}
    : '{' x=int_object ';' y=int_object ';' z=int_object '}';

empty_list
    returns[List<String> value]
    @init{$ctx.value = new ArrayList<>();}:
    '{''}';

identifier_list
    returns[List<String> value]
    @init{ $ctx.value = new ArrayList<>();}: empty_list
    | '{' io=identifier_object { $ctx.value.add($io.value); }
    (SEMICOLON io=identifier_object { $ctx.value.add($io.value); })* '}';

int_list
    returns [List<Integer> value]
    @init{ $ctx.value = new ArrayList<>(); }: empty_list
    | '{' io=int_object { $ctx.value.add($io.value); }
    (SEMICOLON io=int_object {$ctx.value.add($io.value);})* '}';

double_list
    returns [List<Double> value]
    @init { $ctx.value = new ArrayList<>(); }: empty_list
    | '{' d=double_object { $ctx.value.add(Double.valueOf($d.text)); }
    (SEMICOLON d=double_object { $ctx.value.add(Double.valueOf($d.text)); })* '}';

base_attribute_attack
    returns[AttributeAttack value]: 'base_attribute_attack' '=' aa=attack_attribute {$ctx.value = $aa.value;};
attack_attribute
    returns[AttributeAttack value]
    :'{' attribute ';' io=int_object '}' {$ctx.value = new AttributeAttack($attribute.value, $io.value);};

attribute
    returns[AttributeType value]:
    NONE {$ctx.value = AttributeType.NONE;}
    | FIRE {$ctx.value = AttributeType.FIRE;}
    | WATER {$ctx.value = AttributeType.WATER;}
    | EARTH {$ctx.value = AttributeType.EARTH;}
    | WIND {$ctx.value = AttributeType.WIND;}
    | HOLY {$ctx.value = AttributeType.HOLY;}
    | UNHOLY {$ctx.value = AttributeType.UNHOLY;};

category_list
    returns [List<String> value]
    @init { $ctx.value = new ArrayList<>(); }: empty_list
    | '{' co=category_object { $ctx.value.add($co.text); }
    (SEMICOLON co=category_object { $ctx.value.add($co.text); })* '}';

CATEGORY : '@' NAME;

BOOLEAN: BINARY_DIGIT;
INTEGER: MINUS? DECIMAL;
DOUBLE: INTEGER '.' DIGIT+;

// Attributes
FIRE : 'fire';
WATER : 'water';
EARTH : 'earth';
WIND : 'wind';
UNHOLY : 'unholy';
HOLY : 'holy';

fragment
DECIMAL: ZERO_DIGIT | NON_ZERO_DIGIT DIGIT*;

fragment
MINUS: '-';

fragment
BINARY_DIGIT: [01];

fragment
DIGIT : [0-9];

fragment
NON_ZERO_DIGIT: [1-9];

fragment
ZERO_DIGIT: [0];

SEMICOLON : ';';

// Constants
NONE : 'none';

// npc race
FAIRY : 'fairy';
ANIMAL : 'animal';
HUMANOID : 'humanoid';
PLANT : 'plant';
UNDEAD : 'undead';
CONSTRUCT : 'construct';
BEAST : 'beast';
BUG : 'bug';
ELEMENTAL : 'elemental';
DEMONIC : 'demonic';
GIANT: 'giant';
DRAGON: 'dragon';
DIVINE: 'divine';
SUMMON: 'summon';
PET: 'pet';
HOLYTHING: 'holything';
DWARF: 'dwarf';
MERCHANT: 'merchant';
ELF: 'elf';
KAMAEL: 'kamael';
ORC: 'orc';
MERCENARY: 'mercenary';
CASTLE_GUARD: 'castle_guard';
HUMAN: 'human';
BOSS: 'boss';
ZZOLDAGU: 'zzoldagu';
WORLD_TRAP: 'world_trap';
MONRACE: 'monrace';
DARKELF: 'darkelf';
GUARD: 'guard';
TELEPORTER: 'teleporter';
WAREHOUSE_KEEPER: 'warehouse_keeper';

// Weapon types
SWORD : 'sword';
BLUNT : 'blunt';
BOW : 'bow';
POLE : 'pole';
DAGGER : 'dagger';
DUAL : 'dual';
FIST : 'fist';
DUALFIST : 'dualfist';
FISHINGROD : 'fishingrod';
RAPIER : 'rapier';
ANCIENTSWORD : 'ancientsword';
CROSSBOW : 'crossbow';
FLAG : 'flag';
OWNTHING : 'ownthing';
DUALDAGGER : 'dualdagger';
ETC : 'etc';

// Armor types
LIGHT : 'light';
HEAVY : 'heavy';
MAGIC : 'magic';
SIGIL : 'sigil';

// slot bit types
RHAND : 'rhand';
LRHAND : 'lrhand';
LHAND : 'lhand';
CHEST : 'chest';
LEGS : 'legs';
FEET : 'feet';
HEAD : 'head';
GLOVES : 'gloves';
ONEPIECE : 'onepiece';
REAR : 'rear';
LEAR : 'lear';
RFINGER : 'rfinger';
LFINGER : 'lfinger';
NECK : 'neck';
BACK : 'back';
UNDERWEAR : 'underwear';
HAIR : 'hair';
HAIR2 : 'hair2';
HAIRALL : 'hairall';
ALLDRESS : 'alldress';
RBRACELET : 'rbracelet';
LBRACELET : 'lbracelet';
WAIST : 'waist';
DECO1 : 'deco1';

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

NAME : [A-Za-z0-9_]+ [A-Za-z0-9_'*.~:-]*;

// skip whitespaces and line comments
WS : [ \r\t\u000C\r\n]+ -> skip;
LINE_COMMENT : '//' ~[\r\n]* '\r'? '\n' -> skip;
STAR_COMMENT : '/*' .*? '*/' -> skip;
