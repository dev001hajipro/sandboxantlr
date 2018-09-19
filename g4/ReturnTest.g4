// https://keis-software.com/2016/01/18/introduction-to-antlr-pt-3/
grammar ReturnTest;

@header {
	package com.github.dev001hajipro.lang.returntest;	
}

// tupleは、シングルクォートで囲った文字列が、空白区切りで並んだもの。
tuple : STRING_LITERAL (' ' STRING_LITERAL)*
;

// antlr4では.g4ファイル側に、関数を組み込める。
tuple1 returns[List<String> result]: s=STRING_LITERAL (' ' ls+=STRING_LITERAL)*
{
	$result = new ArrayList<String>();
	$result.add($s.getText());
	for (Token t: $ls) {
		$result.add(t.getText());
	}
}
;

// Lexer, TOKEN /////
// 文字列または、'' シングルクォートでエスケープ
fragment STRING_CHARACTER		: ~['] | '\'\''		;
fragment STRING_CHARACTERS		: STRING_CHARACTER+	;
STRING_LITERAL	: '\'' STRING_CHARACTERS? '\''		;

WS	: [ \t\r\n]+ -> skip	;