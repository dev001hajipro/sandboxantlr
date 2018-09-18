package com.github.dev001hajipro.lang.gyoo;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.github.dev001hajipro.lang.gyoo.GYOOParser.AddContext;
import com.github.dev001hajipro.lang.gyoo.GYOOParser.AssignContext;
import com.github.dev001hajipro.lang.gyoo.GYOOParser.PrintContext;

/**
 * 参考資料: https://progur.com/2016/09/how-to-create-language-using-antlr4.html
 * 
 * @author dev001hajipro
 */

public class GYOOMyListener extends GYOOBaseListener {

	private Map<String, Integer> variables;

	@Override
	public void exitAssign(AssignContext ctx) {
		// var1 = var2
		if (ctx.ID().size() > 1) { // 右辺が変数の時
			String variableName = ctx.ID(0).getText();
			String rightID = ctx.ID(1).getText();
			variables.put(variableName, variables.get(rightID));
		} else {
			// 右辺が数値の時
			String variableName = ctx.ID(0).getText();
			String numValue = ctx.NUMBER().getText();
			variables.put(variableName, Integer.parseInt(numValue));
		}
	}

	@Override
	public void exitAdd(AddContext ctx) {
		// 数値を変数に追加
		if (ctx.ID().size() == 1) {
			String variableName = ctx.ID(0).getText();
			int value = Integer.parseInt(ctx.NUMBER().getText());
			variables.put(variableName, variables.get(variableName) + value);
		} else { // add val1 to val2
			String variableName = ctx.ID(1).getText();
			int value = variables.get(ctx.ID(0).getText());
			variables.put(variableName, variables.get(variableName) + value);
		}
	}

	@Override
	public void exitPrint(PrintContext ctx) {
		String value = (ctx.ID() == null) ? ctx.NUMBER().getText() : variables.get(ctx.ID().getText()).toString();
		System.out.println(value);
	}

	public void program(Path path) throws IOException {
		variables = new HashMap<>();

		GYOOLexer lexer = new GYOOLexer(CharStreams.fromPath(path));
		GYOOParser parser = new GYOOParser(new CommonTokenStream(lexer));
		parser.addParseListener(this);

		parser.program();
	}
}
