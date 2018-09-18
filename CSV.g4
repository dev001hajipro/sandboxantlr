grammar CSV;


@header {
	package com.github.dev001hajipro.lang.csv;
}

file:
	header row+
	;

// header: row; と書けるが、パーサー側でヘッダー読み込みとデータ読み込みを
// 

header:
	field (',' field)* '\r'? '\n'
	;

row:
	field (',' field)* '\r'? '\n'
	;

field:
	TEXT
	| STRING
	| 
	;

// TOKEN /////

// TEXTは、,\r\n"以外の1文字以上

TEXT: ~[,\n\r"]+	;

// '"'で、ダブルクォート
// CSVでは、"hello"のように、文字列は""で囲む。
// 文字列中にダブルクォートがある場合はダブルクォートでエスケープする
// "hello""world"
// つまり、'""'は、文字列中のエスケープされたダブルクォートを意味する
// ~'"'は、"以外の文字を意味する。

STRING: '"' ('""' | ~'"')* '"';
	


