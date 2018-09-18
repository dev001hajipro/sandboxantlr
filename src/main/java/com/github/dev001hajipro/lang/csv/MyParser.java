package com.github.dev001hajipro.lang.csv;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.github.dev001hajipro.lang.csv.CSVParser.FileContext;

/**
 * Antlr4で作ったCSVパーサーを使う。
 * 
 * 参考資料
 * @see http://blog.seamark.co.jp/?p=392
 * 
 * @author dev001hajipro
 * 
 */
// 
public class MyParser {

	public void parser() {
		Path path = Paths.get("c:\\tmp\\test.csv");
		try {
			System.out.println(CharStreams.fromPath(path));
			CSVLexer lexer = new CSVLexer(CharStreams.fromPath(path));
			CommonTokenStream tokens = new CommonTokenStream(lexer);
			CSVParser parser = new CSVParser(tokens);

			FileContext context = parser.file();
			// ParseTree tree = parser.file();
			// return visit(context);
			// tree.toStringTree(parser);
			context.toStringTree();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void visit(FileContext context) {

	}

	public static void main(String[] args) {
		MyParser parser = new MyParser();
		parser.parser();
	}
}
