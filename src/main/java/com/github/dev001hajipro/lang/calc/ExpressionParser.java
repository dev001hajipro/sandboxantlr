package com.github.dev001hajipro.lang.calc;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.github.dev001hajipro.lang.calc.CalcLexer;
import com.github.dev001hajipro.lang.calc.CalcParser;
import com.github.dev001hajipro.lang.calc.CalcParser.ExprContext;

/**
 * Calc.g4のパーサー
 */
public class ExpressionParser {

	/**
	 * 式を解析
	 * 
	 * @param expression
	 * @return
	 */
	public int parser(final String expression) {
		final CalcLexer lexer = new CalcLexer(CharStreams.fromString(expression));

		final CommonTokenStream tokens = new CommonTokenStream(lexer);
		final CalcParser parser = new CalcParser(tokens);

		final ExprContext context = parser.expr();

		return visit(context);
	}

	// 葉になるまで再帰的にAST(抽象構文木)を調べる
	public int visit(ExprContext context) {
		if (context.number() != null) {
			return Integer.parseInt(context.number().getText());
		} else if (context.BR_CLOSE() != null) {
			return visit(context.expr(0));
		} else if (context.TIMES() != null) {
			return visit(context.expr(0)) * visit(context.expr(1));
		} else if (context.DIV() != null) {
			return visit(context.expr(0)) / visit(context.expr(1));
		} else if (context.PLUS() != null) {
			return visit(context.expr(0)) + visit(context.expr(1));
		} else if (context.MINUS() != null) {
			return visit(context.expr(0)) - visit(context.expr(1));
		} else {
			throw new IllegalStateException();
		}
	}

	public static void main(String[] args) {
		ExpressionParser parser = new ExpressionParser();
		System.out.println("1+1=" + parser.parser("1+1"));
		System.out.println("1+(2*3) + 5=" + parser.parser("1+(2*3) + 5"));
		System.out.println("1+(3/3)*(2+2)+ 5=" + parser.parser("1+(3/3)*(2+2)+ 5"));
		System.out.println("1+(3/3)*((2+2)* 5)=" + parser.parser("1+(3/3)*((2+2)* 5)"));
		System.out.println(")=" + parser.parser(")"));
	}
}
