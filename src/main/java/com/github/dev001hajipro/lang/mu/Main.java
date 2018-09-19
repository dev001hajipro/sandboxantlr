package com.github.dev001hajipro.lang.mu;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * .g4で生成した、MuBaseVisitorを継承したVisitorを作成し、ParseTreeを渡すことで
 * ビジターパターンで処理できる。
 * 
 * @author dev001hajipro
 *
 */
public class Main {

	public static void main(String[] args) {
		try {
			Path path = Paths.get("data/test.mu");
			MuLexer lexer = new MuLexer(CharStreams.fromPath(path));
			MuParser parser = new MuParser(new CommonTokenStream(lexer));
			ParseTree tree = parser.parse(); // .parseは、.g4の最初に書かれたルール
			EvalVisitor visitor = new EvalVisitor();
			visitor.visit(tree);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
