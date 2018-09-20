// http://iwsttty.hatenablog.com/entry/2014/05/11/175728
lexer grammar StringLexer;

@header {
    package com.github.dev001hajipro.lang.stringp;
}

// TOKENS ////
QUOTE		: '"' -> pushMode(STRING_MODE);
WS			: [ \t] -> skip;
NEWLINE		: ('\r'? '\n' | '\r') -> skip;
OUTER_CHAR	: . -> skip;

//////////
mode STRING_MODE;
RQUOTE				: '"' -> popMode; //
// バックスラッシュは、スキップして、ESCAPE_CHARモードになる
ESCAPE			: '\\' -> skip, pushMode(ESCAPE_CHAR_MODE);
NOT_ESCAPE_CHAR		: .;

//////////
mode ESCAPE_CHAR_MODE;
ESCAPE_CHAR		: ('\\' | '"') -> popMode;
