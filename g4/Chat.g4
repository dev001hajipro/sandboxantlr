grammar Chat;

// https://tomassetti.me/antlr-mega-tutorial/#lexers-and-parser

@header {
	package com.github.dev001hajipro.lang.chat;	
}

/*
 * Parser Rule
 */
chat:
	line+ EOF;

line:
	name command message NEWLINE;

name:
	WORD WHITESPACE ;

command:
	(SAYS | SHOUTS) ':' WHITESPACE ;

message:
	(emoticon | link | color | mention | WORD | WHITESPACE)+ ;
	
emoticon:
	':' '-'? ')'
	|':' '-'? '('
	;

link:
	TEXT TEXT ;
	
color:
	'/' WORD '/' message '/';
	
mention:
	'@' WORD;
 
operation: NUMBER '+' NUMBER	;

/*
 * Lexer Rule, TOKEN
 */
fragment A	: ('A'|'a') ;
fragment S	: ('S'|'s') ;
fragment Y	: ('Y'|'y') ;
fragment H	: ('H'|'h') ;
fragment O	: ('O'|'o') ;
fragment U	: ('U'|'u') ;
fragment T	: ('T'|'t') ;
 
fragment LOWERCASE	: [a-z] ;
fragment UPPERCAE	: [A-Z] ;
 
fragment DIGIT		: [0-9] ;
NUMBER				: DIGIT+ ([.,] DIGIT+)? ; 
 
SAYS		: S A Y S ;
SHOUTS		: S H O U T S;
WORD		: (LOWERCASE|UPPERCAE|'_')+ ;
WHITESPACE	: (' ' | '\t')+ ;
NEWLINE		: ('\r'? '\n' | '\r')+ ;
 
// ])以外
//TEXT		: ~('[')+ ;
//上記の場合、[以外の1文字以上の文字列のため、かっこがない場合、最長でヒットする
// そのため以下のように、[(が先頭で、)]が最後の文字列に書き換える。
TEXT		: ('[' | '(')    ~[\])]+    (']' | ')') ;
 
