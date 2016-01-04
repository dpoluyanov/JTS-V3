grammar Lang;

identifier_object :  IDENTIFIER;
int_object : INTEGER;

identifier_array : '{' identifier_object (';' identifier_object)* '}';
int_array: '{' int_object (';' int_object)* '}';

NAME : '[' [A-z]+ [A-z0-9_']* ']';
IDENTIFIER : [A-z]+ [A-z0-9_]*;
INTEGER : '-'? ('0' | '1'..'9' '0'..'9'*);

// skip whitespaces and line comments
WS : [ \r\t\u000C\r\n]+ -> skip;
LINE_COMMENT : '//' ~[\r\n]* '\r'? '\n' -> skip;
