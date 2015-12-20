grammar Lang;

object : '{' (''| int_object) '}';
int_object : INTEGER;

array: object | '{' object (';' object)* '}';
int_array: '{' int_object (';' int_object)* '}';

INTEGER : '-'? ('0' | '1'..'9' '0'..'9'*);

// skip whitespaces and line comments
WS : [ \r\t\u000C\r\n]+ -> skip;
LINE_COMMENT : '//' ~[\r\n]* '\r'? '\n' -> skip;
