package com.github.dev001hajipro.lang.gyoo;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * 変数、加算、Printのみの簡単なプログラミング言語GYOOを作るチュートリアル
 * Antlr4で変数を用意した場合どのように保持すればよいか学べる。
 * https://progur.com/2016/09/how-to-create-language-using-antlr4.html
 * @author dev001hajipro
 *
 */
public class GYOOMain {
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
		GYOOMyListener listener = new GYOOMyListener();
		try {
			listener.program(Paths.get("data/hello.gyo"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
