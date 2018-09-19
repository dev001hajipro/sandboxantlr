package com.github.dev001hajipro.lang.returntest;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.github.dev001hajipro.lang.returntest.ReturnTestParser.Tuple1Context;

// https://keis-software.com/2016/01/18/introduction-to-antlr-pt-3/
// .g4ファイルでJavaメソッドを記述し、それをこのクラスで使うサンプル。
public class Main {

	public static void main(String[] args) {
		ReturnTestLexer lexer = new ReturnTestLexer(CharStreams.fromString("'a' 'xxx' 'c11'"));
		ReturnTestParser parser = new ReturnTestParser(new CommonTokenStream(lexer));
		Tuple1Context ctx = parser.tuple1();
		ctx.result.stream().forEach(System.out::println);
	}

}
