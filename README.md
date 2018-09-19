# sandbox antrlr



Antlr4で作った簡単な四則演算

## 参考資料

 * http://niels.nu/blog/2015/antlr-is-awesome.html
 * https://github.com/antlr/grammars-v4
 
## ANTLR4 IDE for Eclipse

Eclipseのプラグイン[ANTLR4 IDE for Eclipse](https://github.com/antlr4ide/antlr4ide)を使うと、
構文解析ツリーやシンタックスダイアグラムが使えます。


## g4ファイルの追加の仕方
テキストファイルで追加します。拡張子を.g4にするとアイコンが変化します。

## Eclipseの設定

EclipseはPOM.xmlを読んでプロジェクトを作成しています。そのためプロジェクトの設定は必要ありません。
しかし、Eclipse 4.8の場合、環境によってはプロジェクトファセットがJava10になっているので、
プロジェクトを右クリックして「プロパティ」から、「プロジェクトファセット」のJavaのバージョンを
使っているものと合わせないとコンパイルエラーになります。

## Eclipse の GitConfig

EclipseでGitを使う場合にシステムのユーザー情報が使われる場合は、
`git config`で*user.email*と*user.name*を変更すればよいです。

```bash
git config user.email 'username@xxx.com'
git config user.name 'username'
```

## antlr4コマンドの言語オプション

`-Dlanguage`オプションで出力言語を指定できます。

```bash
antlr4 -Dlanguage=JavaScript Chat.g4
```

## fragmentの意味

- https://stackoverflow.com/questions/6487593/what-does-fragment-mean-in-antlr
lexer ruleで使う、インライン関数、マクロのようなもの。

## Visitorクラスの作成
Antlr4 IDEは、.g4ファイルをビルドするときは、以下のように、`-listener`,
`-no-visitor`となっているので、.g4ファイルを右クリック。

```bash
ANTLR Tool v4.7.1 (C:\dev\antlr-4.7.1-complete.jar)
Calc.g4 -o output/dir -listener -no-visitor -encoding UTF-8
```
- https://stackoverflow.com/questions/21213143/antlr4-parser-visitor-not-created
