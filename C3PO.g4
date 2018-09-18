grammar C3PO;

@header {
    package com.github.dev001hajipro.lang.c3po;
}

expression:
	methodCall
	| STRING
	;

methodCall:
	methodName '(' methodCallArguments ')';

/*
// 以下のように:のあとに、|くる場合は、
statement: | aaa (',' aaa)*;

// 引数なし|引数あり　となっていることに注意。
statement:
  // 引数なし
  | aaa (',' aaa)*
  ; 
 
 */ 

methodCallArguments:
	// 引数なし
	| expression (',' expression)*;

methodName: NAME;
	
// TOKEN /////
NAME			: [a-zA-Z][a-zA-Z0-9]*;
STRING		: '"' ~('"')* '"';
WS			: [ \t\u000C\r\n]+ -> skip;