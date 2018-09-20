grammar INI;

// TODO:値をalphanum以外も設定できるようにしたい。

@header {
    package com.github.dev001hajipro.lang.ini;
}

ini
	: stmt*
	;
	
stmt
	: parameter
	| section
	;
	
parameter
	: name '=' value
	;

section
	: '[' section_name ']'
	;
	
section_name
	: WORD
	;

name
	: WORD
	;
	
value
	: WORD
	;
	

// TOKEN
WORD	
	: [a-zA-Z_0-9]+
	;

LINE_COMMENT
	: ';' ~[\r\n]* -> skip
	;
	
WS
	: [ \t\r\n]+ -> skip
	;
	

