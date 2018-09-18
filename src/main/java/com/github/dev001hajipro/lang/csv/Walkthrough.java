package com.github.dev001hajipro.lang.csv;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.github.dev001hajipro.lang.csv.CSVParser.FieldContext;
import com.github.dev001hajipro.lang.csv.CSVParser.FileContext;
import com.github.dev001hajipro.lang.csv.CSVParser.HeaderContext;
import com.github.dev001hajipro.lang.csv.CSVParser.RowContext;

/**
 * いわゆるイベントリスナーやコールバックのサンプル。
 * 
 * Antlr4では、.g4に構文を書くと、それの入出時を補足できる。このCSVでは
 * header, row, fieldなどを構文として書いたが、このタイミングのイベントリスナーが
 * 生成される(CSVBaseListener)。
 * @author dev001hajipro
 *
 */
public class Walkthrough extends CSVBaseListener {
	
	public Optional<String> parser(Path path) throws IOException {
		CSVLexer lexer = new CSVLexer(CharStreams.fromPath(path));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		CSVParser parser = new CSVParser(tokens);
		
		ParseTree tree = parser.file();
		
		// .walkをすることで、CSVBaseListenerの各メソッドをオーバーライドして
		// 処理を補足できる。
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(this, tree);;
		
		return Optional.ofNullable(tree.toStringTree());
	}
	
	private List<String> header;
	private List<String[]> rows = new ArrayList<String[]>();
	
	@Override
	public void exitHeader(HeaderContext ctx) {
		if (ctx.getText() != null) {
			System.out.println("\\tH[" + ctx.getText() + "]");
			List<FieldContext> fields = ctx.field();
			header = fields.stream().map(f->f.getText().trim()).collect(Collectors.toList());
		}
		System.out.println("exitHeader");
	}
	
	@Override
	public void exitRow(RowContext ctx) {
		if (ctx.getText() != null) {
			System.out.println("\\tR[" + ctx.getText() + "]");
			List<FieldContext> fields = ctx.field();
			String[] row = fields.stream().map(f->f.getText().trim()).toArray(String[]::new);
			rows.add(row);
		}
		System.out.println("exitRow");
	}
	
	@Override
	public void exitField(FieldContext ctx) {
		if (ctx.getText() != null) {
			System.out.println("\tF:[" + ctx.getText() + "]");
		}
		System.out.println("exitField");
	}

	@Override
	public void exitFile(FileContext ctx) {
	}
	
	@Override
	public void visitTerminal(TerminalNode node) {
		//System.out.println("visitTerminal:" + node);
	}
	
	public void run() {
		Path path = Paths.get("c:\\tmp\\test.csv");
		try {
			parser(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		header.forEach(System.out::print);
		System.out.println();
		
		rows.forEach(rowArray-> {
			System.out.println(Arrays.toString(rowArray));
		});
	}

	public static void main(String[] args) {
		Walkthrough wt = new Walkthrough();
		wt.run();
	}

}
