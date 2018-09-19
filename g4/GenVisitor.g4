// Antlr4でvisitorクラスを生成するためのサンプル.g4ファイル
// antlr-4.7.1-complete.jar -visitorで呼び出す。
// antlr GenVisitor.g4 -o output -listener -visitor -encoding UTF-8

grammar GenVisitor;

@header {
	package com.github.dev001hajipro.lang.genvisitor;
}

genvisitor	: expr EOF ;

expr		:
	'(' expr ')'
	| expr (TIMES | DIV | MOD) expr
	| expr (PLUS | MINUS) expr
	| NUMBER
;

// lexer rule ////////////////////
// TOKEN
TIMES		: '*';
DIV			: '/';
MOD			: '%';
PLUS			: '+';
MINUS		: '-';
NUMBER		: '-'?[0-9]+;

WS			: [ \t\r\n]+ -> skip;
