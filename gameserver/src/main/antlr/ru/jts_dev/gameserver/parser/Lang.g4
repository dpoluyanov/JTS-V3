grammar Lang;


identifier_object :
    // workaround about name conflicts (itemdata.txt)
    DAGGER | BOW | CROSSBOW | RAPIER | GLOVES | STEEL | LEATHER | ORIHARUKON | 'slot_lhand'
    | NAME | NONE
    ;
int_object
    returns [int value]: INTEGER { $ctx.value = Integer.valueOf($text); };
double_object
    returns [double value]:
    INTEGER { $ctx.value = Integer.valueOf($text); }
    | DOUBLE { $ctx.value = Double.valueOf($text); };
name_object returns [String value] : '[' identifier_object ']' { $ctx.value = $text; };
category_object: CATEGORY;

empty_array : '{''}';
identifier_array : empty_array | '{' identifier_object (';' identifier_object)* '}';
int_array2 : empty_array | '{' int_object ';' int_object '}';
int_array6 : empty_array | '{' int_object ';' int_object ';' int_object ';' int_object ';' int_object ';' int_object '}';
int_array : empty_array | '{' int_object (';' int_object)* '}';
double_array : empty_array | '{' double_object (';' double_object)* '}';
category_array : empty_array | '{' category_object (';' category_object)* '}';

CATEGORY : '@' NAME;
DOUBLE : INTEGER '.' '0'..'9'+;
INTEGER : '-'? ('0' | '1'..'9' '0'..'9'*);

// Constants
NONE : 'none';

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
