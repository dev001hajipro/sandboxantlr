package com.github.dev001hajipro.lang.c3po;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.github.dev001hajipro.lang.c3po.C3POParser.ExpressionContext;
import com.github.dev001hajipro.lang.c3po.C3POParser.MethodCallArgumentsContext;
import com.github.dev001hajipro.lang.c3po.C3POParser.MethodCallContext;

/**
 * LISP言語のように関数を呼び出していくので、C3POBaseListenerで
 * イベントハンドリングせずに、visit()関数で戻り値の文字列を主利するように
 * 実装
 * @author dev001hajipro
 * 参考資料:
 * https://medium.com/@fwouts/a-quick-intro-to-antlr4-5f4f35719823
 */
public class C3POMyListener {

	public final String trimDQ(String s) {
		return s.replaceAll("^\"|\"$", "");
	}

	public String run(Path path) throws IOException {
		C3POLexer lexer = new C3POLexer(CharStreams.fromPath(path));
		C3POParser parser = new C3POParser(new CommonTokenStream(lexer));
		// parser.addParseListener(this);

		final ExpressionContext context = parser.expression();

		return expr(context);
	}

	private String expr(ExpressionContext ctx) {
		if (ctx.methodCall() != null) {
			return mcall(ctx.methodCall());
		} else { // String
			return trimDQ(ctx.getText());
		}
	}

	private String mcall(MethodCallContext methodCall) throws IllegalStateException {
		String methodName = methodCall.methodName().getText();
		if ("print".equals(methodName)) {
			if (methodCall.methodCallArguments() != null) {
				List<String> result = mca(methodCall.methodCallArguments());
				System.out.println(String.join("", result));
				return "";
			}
		} else if ("concat".equals(methodName)) {
			if (methodCall.methodCallArguments() != null) {
				List<String> result = mca(methodCall.methodCallArguments());
				return String.join("", result);
			}
		}
		throw new IllegalStateException("unknown syntax.");
	}

	private List<String> mca(MethodCallArgumentsContext ctx) {
		List<String> result = new ArrayList<>();
		for (ExpressionContext item : ctx.expression()) {
			result.add(expr(item));
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			C3POMyListener listener = new C3POMyListener();
			String result = listener.run(Paths.get("data/hello2.c3po"));
			System.out.println(result);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}
}
