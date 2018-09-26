# My reference site and info.

## Antlr is awesome 

- [Antlr is awesome](http://niels.nu/blog/2015/antlr-is-awesome.html)

はじめてAntlr4をやる人にお勧めのチュートリアル。
電卓を作り、visit関数を再帰で呼び出して動作します。
githubに完成したプロジェクトがあります。

# antlr4を使う

日本語でCSVパーサーの入門記事。ParseTreeWalkerを使ったWalkthroughの実装例も
あります。

- [antlr4を使う(1)](http://blog.seamark.co.jp/?p=353)
- [antlr4を使う(2)](http://blog.seamark.co.jp/?p=392) 

## How to Create a Programming Language Using ANTLR4

- [How to Create a Programming Language Using ANTLR4](https://progur.com/2016/09/how-to-create-language-using-antlr4.html)

変数定義、代入、Printのみの簡単なプログラミング言語GYOOを作るチュートリアル。
変数の保持方法を学べます。

## Antlr is there a simple example? (stackoverflow)

Antlr3でExp.gで電卓を作るサンプルだけれど、注意深く実装すればExp.g4が作れます。
.g4で生成したリスナーやビジターを使わずに、.g4内でJavaコードを書く実装です。

実際にExp.g4を実装して動かしてみましたが、ビジターの実装の方が綺麗です。

- https://stackoverflow.com/questions/1931307/antlr-is-there-a-simple-example

## C3PO

シンプルな関数型プログラミング言語C3POを作成

- [Parsing your own language with ANTLR4](https://medium.com/@fwouts/a-quick-intro-to-antlr4-5f4f35719823)

## 日本語でのCSVParser解説

- https://keis-software.com/2015/10/19/introduction-to-antlr/
- https://keis-software.com/2015/11/16/introduction-to-antlr-pt-2/


* https://keis-software.com/2016/01/18/introduction-to-antlr-pt-3/

.g4ファイルへの関数埋め込みサンプル。
関数埋め込みを行わない場合、visitかイベントリスナーで処理を実装します。

## Mega
 
- https://tomassetti.me/antlr-mega-tutorial/

大きめのチュートリアル。いろんな言語で動かすのは良いが、その分他の言語の知識や
環境構築が必要になるのでちょっと面倒。

## ANTLR v4 の文法ファイルのサンプル

- http://iwsttty.hatenablog.com/entry/2014/05/11/175728

## antlr/grammars-v4 

Grammars written for ANTLR v4; expectation that the grammars are 
free of actions.

- https://github.com/antlr/grammars-v4

## if-else-statements-in-antlr-using-listeners

Visitorを使ったif,whileのあるプログラミング言語作成のチュートリアル。stackoverflowの返答で短い。

- https://stackoverflow.com/questions/15610183/if-else-statements-in-antlr-using-listeners
- https://github.com/bkiers/Mu

## .g4での#マークは、コメントではなくコード生成のヒント

下記のように、パーサールールに#を付けると、ビジターで、visitStatではなく、
visitStringOpr, visitIdOprが用意されます。
```g4
stat :
	STRING  #stringOpr
	| ID    #idOpr
	;
```

- https://stackoverflow.com/questions/23092081/antlr4-visitor-pattern-on-simple-arithmetic-example

## 日本語

- [オレオレJVM言語を作ろう！ How to create a new JVM language](https://www.sakatakoichi.com/entry/2017/07/18/193000) 

## Lexerのモードについて

HTMLは、タグの外のコンテンツとタグの中の属性定義などがあります。このような場合、
IN_MODEとOUT_MODEを定義して、開始タグ<があったら、IN_MODEになり、終了タグ>で
OUT_MODEになるようにしておくと、Lexerのトークンが綺麗に分けられます。

ただし、この機能を使う場合は、ParserとLexerのg4ファイルを別にする必要があります。


```g4
// Lexer.g4
lexer grammer MyLexer;
BEGIN_TAG		: '<' -> pushMode(IN_MODE);
END_TAG			: '>' -> popMode;

mode IN_MODE; // モード定義
...

mode OUT_MODE;
...
```

```g4
// Parser.g4
parser grammer MyParser;
options {
	tokenVocab=MyLexer;
}

```

- http://iwsttty.hatenablog.com/entry/2014/05/11/175728

## for loop

http://bearcave.com/software/antlr/antlr_examples.html

http://cx5software.com/article_antlr/#N20097

http://jakubdziworski.github.io/tags.html#antlr4-ref

http://www.iro.umontreal.ca/~felipe/IFT2030-Automne2002/Complements/tinyc.c