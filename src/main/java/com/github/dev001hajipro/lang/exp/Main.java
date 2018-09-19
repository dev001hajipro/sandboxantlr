package com.github.dev001hajipro.lang.exp;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

// https://stackoverflow.com/questions/1931307/antlr-is-there-a-simple-example
// .g4にJavaコードを記述する方法で実装
public class Main {

	public static void main(String[] args) {
		ExpLexer lexer = new ExpLexer(CharStreams.fromString("12*(5-6)"));
		ExpParser parser = new ExpParser(new CommonTokenStream(lexer));
		System.out.println("RESULT: " + parser.eval().value);
	}
}
