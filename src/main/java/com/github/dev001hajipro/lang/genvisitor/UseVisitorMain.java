package com.github.dev001hajipro.lang.genvisitor;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.github.dev001hajipro.lang.genvisitor.GenVisitorParser.GenvisitorContext;

// .g4で生成されたVisitorを使うサンプル
public class UseVisitorMain {
	
	public void run(Path path) throws IOException {
		GenVisitorLexer lexer = new GenVisitorLexer(CharStreams.fromPath(path));
		GenVisitorParser parser = new GenVisitorParser(new CommonTokenStream(lexer));
		
		GenvisitorContext ctx = parser.genvisitor();
		
		//visit(ctx);
	}

	private void visit(GenvisitorContext ctx) {
	}

	public static void main(String[] args) {
		UseVisitorMain app = new UseVisitorMain();
		try {
			app.run(Paths.get("data/hello.gv"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
