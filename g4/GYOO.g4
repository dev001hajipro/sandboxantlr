grammar GYOO;

@header {
	package com.github.dev001hajipro.lang.gyoo;
}

program:
	'begin' statement+ 'end'
	;

statement:
	assign
	| add
	| print
	;

assign	: 'let' ID 'be' (NUMBER | ID);
add		: 'add' (NUMBER | ID) 'to' ID;
print	: 'print' (NUMBER | ID);

// TOKEN /////
ID		: [a-z]+	;
NUMBER	: [0-9]+	;
WS		: [ \r\n\t]+ -> skip	;
