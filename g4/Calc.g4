/**
 * http://niels.nu/blog/2015/antlr-is-awesome.html
 * https://github.com/antlr/grammars-v4
 */
// Parser and lexer name
grammar Calc;

@header {
    package com.github.dev001hajipro.lang.calc;
} 
 

// Lower cased words are parser expressions,
// UPPER CASE words are lexer expressions.
// lexer 字句解析
// Parser 構文解析
calc
:
	expr EOF
;

expr
:
	BR_OPEN expr BR_CLOSE // brackets have a higher precedence than anything else
	| expr TIMES expr
	| expr DIV expr
	| expr PLUS expr
	| expr MINUS expr
	| number
;

number
:
	NUMBER
;

// TOKEN /////////////////

PLUS
:
	'plus'
	| '+'
;

MINUS
:
	'minus'
	| '-'
;

TIMES
:
	'times'
	| '*'
;

DIV
:
	'div'
	| '/'
;

NUMBER
:
	'-'? [0-9]+
;

BR_OPEN
:
	'('
;

BR_CLOSE
:
	')'
;

WS
:
	[ \t\r\n]+ -> skip
;
