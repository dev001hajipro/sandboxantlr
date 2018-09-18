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
グローバルなEメールと名前を使ってしまう場合は、consoleで`git config user.email`を指定すればよい。

