// https://stackoverflow.com/questions/1931307/antlr-is-there-a-simple-example
// Altr4版のサンプル
// https://github.com/BITPlan/com.bitplan.antlr/blob/master/src/main/antlr4/com/bitplan/exp/Exp.g4

grammar Exp;

@header {
    package com.github.dev001hajipro.lang.exp;
}
/* ベースとなったコード
eval: additionExp;

additionExp: multiplyExp (('+' | '-') multiplyExp)*;
multiplyExp: atomExp (('*'|'/') atomExp)*;
atomExp:
	NUMBER
	| '(' additionExp ')';

// TOKEN/////
NUMBER		: [0-9]* ('.' [0..9]+)?;
WS			: [ \t\r\n]+ -> skip;

*/
// .g4内にJavaコードを記述する方法で実装

eval returns [double value] // 戻り値 [型 変数名]
	: exp=additionExp
	{
		// block内はJavaコードで、先ほどのvalueはダラーvalueで参照できる
		System.out.println("value equals: " + $value);
		$value = $exp.value;
	}
	;

additionExp returns [double value]
	: m1=multiplyExp		{ $value = $m1.value; }
	('+' m2=multiplyExp		{ $value += $m2.value; }
	| '-' m2=multiplyExp	{ $value -= $m2.value; }
	)*
	;
	
multiplyExp returns [double value]
	: a1=atomExp		{ $value = $a1.value; }
	('*' a2=atomExp		{ $value *= $a2.value; }
	| '/' a2=atomExp	{ $value /= $a2.value; }
	)*
	;
	
atomExp returns [double value]
	:
	n=NUMBER						{$value = Double.parseDouble($n.text); }
	| '(' exp=additionExp ')'	{$value = $exp.value; }
	;

// TOKEN/////
fragment DIGIT: [0-9];

NUMBER		: DIGIT+ ('.' DIGIT+)?;
WS			: [ \t\r\n]+ -> skip;
