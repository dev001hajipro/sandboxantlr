grammar Mu;

// https://stackoverflow.com/questions/15610183/if-else-statements-in-antlr-using-listeners
// Visitorを使ったif文作成のチュートリアル。stackoverflowの返答で短い。
// https://github.com/bkiers/Mu

// [変更前]
// -listener -no-visitor -encoding UTF-8
// [変更後]
// -no-listener -visitor -encoding UTF-8

@header {
    package com.github.dev001hajipro.lang.mu;
}

parse		: block EOF;
block		: stat*; // stat= statement
stat
	: assignment
	| if_stat
	| while_stat
	| log
	| OTHER { System.out.println("unknown char: " + $OTHER.text); }
	;

assignment	: ID ASSIGN expr SEMICOLON;

if_stat
	: IF condition_block (ELSE IF condition_block)* (ELSE stat_block)?;

condition_block: expr stat_block;

stat_block
	: OBRACE block CBRACE // {}
	| stat
	;

while_stat: WHILE expr stat_block;
log: LOG expr SEMICOLON;

expr
// 4.2から<assoc=right>を書く場所が変わった。
// https://github.com/antlr/antlr4/blob/master/doc/left-recursion.md
//	: POW<assoc=right> expr					# powExpr
	: <assoc=right>expr POW expr				# powExpr
	| MINUS expr								# unaryMinusExpr
	| NOT expr								# notExpr
	| expr op=(MULT | DIV | MOD) expr			# multiplicationExpr
	| expr op=(PLUS | MINUS) expr				# additiveExpr
	| expr op=(LTEQ | GTEQ | LT | GT) expr	# relationExpr
	| expr op=(EQ | NEQ) expr				# equalityExpr
	| expr AND expr							# andExpr
	| expr OR expr							# orExpr
	| atom									# atomExpr
	;

atom
	: OPAR expr CPAR	# parExpr
	| (INT | FLOAT)	# numberAtom
	| (TRUE | FALSE)	# booleanAtom
	| ID				# idAtom
	| STRING			# stringAtom
	| NIL			# nilAtom
	;
	
// lexer rule /////
OR		: '||';
AND		: '&&';
EQ		: '==';
NEQ		: '!=';
GT		: '>';
LT		: '<';
GTEQ		: '>=';
LTEQ		: '<=';
PLUS		: '+';
MINUS	: '-';
MULT		: '*';
DIV		: '/';
MOD		: '%';
POW		: '^';
NOT		: '!';

SEMICOLON: ';';
ASSIGN	: '=';
OPAR		: '(';
CPAR		: ')';
OBRACE	: '{';
CBRACE	: '}';

TRUE		: 'true';
FALSE	: 'false';
NIL		: 'nil';
IF		: 'if';
ELSE		: 'else';
WHILE	: 'while';
LOG		: 'log';

ID 		: [a-zA-Z_] [a-zA-Z_0-9]*;
INT		: [0-9]+;
FLOAT
	: [0-9]+ '.' [0-9]*
	| '.' [0-9]*
	;

STRING	: '"' (~["\r\n] | '""')* '"';
COMMENT	: '#' ~[\r\n]* -> skip;
SPACE	: [ \t\r\n] -> skip;
OTHER	: .;
