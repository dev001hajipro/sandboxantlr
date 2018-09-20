// http://iwsttty.hatenablog.com/entry/2014/05/11/175728
parser grammar StringParser;

@header {
    package com.github.dev001hajipro.lang.stringp;
}

options {
  tokenVocab=StringLexer;
}

strings			: string* ;
string returns [String value]
	:
	QUOTE context=text RQUOTE
	{
		$value = $context.text;
		//System.out.println($context.text);
	}
	;
text: ( ESCAPE_CHAR | NOT_ESCAPE_CHAR )* ;