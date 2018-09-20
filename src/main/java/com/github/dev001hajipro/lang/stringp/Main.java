package com.github.dev001hajipro.lang.stringp;

import java.io.IOException;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.github.dev001hajipro.lang.stringp.StringParser.StringsContext;

/**
 * モードを使ったテキスト解析サンプル
 * @author dev001hajipro
 * http://iwsttty.hatenablog.com/entry/2014/05/11/175728
 */
public class Main {

	public static void main(String[] args) throws IOException {
		CharStream input = CharStreams.fromPath(Paths.get("data/stringp.txt"));
		StringLexer lexer = new StringLexer(input);
		StringParser parser = new StringParser(new CommonTokenStream(lexer));
		StringsContext ctx = parser.strings();
		ctx.string().stream().map(s-> "<" + s.value + ">").forEach(System.out::println);
	}

}
