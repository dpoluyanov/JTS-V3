grammar Lang;

identifier_object : IDENTIFIER;
int_object : INTEGER;
double_object : INTEGER | DOUBLE;
name_object: '[' name ']';
// workaround about name conflicts (itemdata.txt)
name : NONE | DAGGER | BOW | GLOVES | NAME;
category_object: CATEGORY;

empty_array : '{''}';
identifier_array : empty_array | '{' identifier_object (';' identifier_object)* '}';
int_array2 : empty_array | '{' int_object ';' int_object '}';
int_array6 : empty_array | '{' int_object ';' int_object ';' int_object ';' int_object ';' int_object ';' int_object '}';
int_array : empty_array | '{' int_object (';' int_object)* '}';
double_array : empty_array | '{' double_object (';' double_object)* '}';
category_array : empty_array | '{' category_object (';' category_object)* '}';

CATEGORY : '@' IDENTIFIER;
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

NAME : [A-Za-z0-9]+ [A-Za-z0-9_'*.~:-]*;
IDENTIFIER : [A-Za-z]+ [A-Za-z0-9_]*;

// skip whitespaces and line comments
WS : [ \r\t\u000C\r\n]+ -> skip;
LINE_COMMENT : '//' ~[\r\n]* '\r'? '\n' -> skip;
STAR_COMMENT : '/*' .*? '*/' -> skip;
