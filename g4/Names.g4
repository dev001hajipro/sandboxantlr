grammar Names;

// Antlr4では、カンマ区切りのトークンの繰り返しは、以下のように書く
// NAME (',' NAME)*
// また、識別子を作る場合も[a-z]?ではなく、以下のように書く。
// [a-z][a-z]*

@header {
    package com.github.dev001hajipro.lang.names;
}

// Antlr4再帰の書きかた。
names:
	NAME (',' NAME)* N2
	;

n2s:
	N2
	;

N2:
	[;]+
	;

// LEXER
NAME:
	//他の文法を見ると、[0-9a-zA-Z_]+のように書かない。
	[0-9a-zA-Z_][0-9a-zA-Z_]*
	;


WS:
	[ \t\u000C\r\n] -> skip
	;