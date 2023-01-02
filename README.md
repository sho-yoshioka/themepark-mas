# themepark-mas

---

## 20221226 
### 変更履歴
* 初期ファイル作成
    * Device.java
    * Node.java
    * ThemePark.java
    * User.java

---

## 20221229
### 備忘録
* [マークダウン記法](https://qiita.com/sano1202/items/64593e8e981e8d6439d3)
.mdファイルに用いるタグのサンプル
* [ダイクストラ法](https://www.techiedelight.com/ja/single-source-shortest-paths-dijkstras-algorithm/)
優先キューをヒープで実装したサンプルプログラム
* [ラムダ形式と関数型インターフェース](https://qiita.com/sano1202/items/64593e8e981e8d6439d3)
    * ジェネリクス
        * ``<Integer>, <String>`` など
    * プリミティブ
        * ``int, double``など
    * <? super クラス名>
        * ?はワイルドカード型
        * <クラス名>のスーパクラスの型
        * <? extends Person> ならPersonクラスを継承するクラス


> 3-1. Collections.sort(List<T>, Comparator<? super T>)
> ソートの方法はオブジェクトの種類や状況によって異なります。
> 例えば数値を並べ替える場合でも、単純な昇順だったり、絶対値の昇順だったりします。
どちらのソートも実現するためには数値の比較処理を動的に切り替える必要があります。
そのため、Collections.sortは比較の処理自体を受け取る仕組みになっています。

~~~Java
int[] numbers = {-1, 2, 0, -3, 8};

List<Integer> numbersList = new ArrayList<>();

for(int n : numbers) {
  numbersList.add(n);
}

Collections.sort(numbersList, 【ソート方法】);
~~~
> 【ソート方法】にはComparatorインターフェースのcompare(s1, s2)メソッドを実装したインスタンスを指定します。

> 実際にラムダ式を用いてソートしてみましょう。

~~~java
Collections.sort(numbersList, (a, b) -> { return a - b; });

for(Integer n : numbersList) {
  System.out.print(n + " ");
}
// -3 -1 0 2 8
~~~

以下の記すコードのうち`node -> node.weight`でつまづいた

```java:ThemeParkMap.java
minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));	
//以下のラムダ形式だと思う。おそらくweightの値の昇順のComparator
		//TotalFunction<Node> tf = (node) -> {return node.weight}
		//@Override
		//int applyAsInt(Node) {
		//	return node.weight
		//}
```

### 変更履歴
* ファイル作成
    * setting/ThemeParkGraph.java
        * 初期設定テーマパークグラフでダイクストラ法を実装しているクラス。ダイクストラは委譲を受ける予定
        
---

## 20230101
### 備忘録
* staticなメソッドはオーバライドできない。
クラスに固有のためポリモーフィズムを実現できないから意味がない

### 変更履歴
* ThemeParkGraph.javaのdijkstara()とgetRoute()を他クラス（Main)で定義した。
    * あくまでグラフ構造と始点を引数で与えればいいので、Graphクラスで定義する必要はない。実際にルート探索するクラスで定義すればいい。
    * ただしGraph, Edge, Nodeをクラス宣言するか、publicか同パッケージにしないとだめそう。
    
---

## 20230102
### 備忘録
* 定数はクラスにまとめておくのも一つの方法
    * コンストラクタはprivate
    * フィールドはpublic static final
* シングルトンは万能ではない
    * ユニットテストがやりにくいときがある。（初期状態にもどらないせい）
* スーパークラスのprivateフィールドは子クラスで値変更できない（protectedにする)
* 抽象クラスもコンストラクタは定義できる(superで呼び出す必要がある)
### 変更履歴
* シングルトンパターンをやめた
    * ThemeParkGraph.java
    * ThemePark.java
* 定数クラスを作成
    * setting/SystemConst.java
    * それに伴いThemeParkGraph.javaの定数削除
* ThemeParkNodeに関するクラスを追加
* Nodeの生成はFactoryクラスで作る予定

---

## 20230103(attractionfeature)
### 備忘録
Visitorの行動とNodeの振る舞いを開発中
### 変更履歴
* ThemeParkNode.canServe()に引数でVisitor
* Attraction.java
    * registerQueue(),finishQueue()追加
    * operationでアトラクションが何人稼働しているかの管理
 
* EnumStatus.java
    * visitorの行動状態の列挙
    
* Visitor.java
     * act()の実装
    
### TODO
* visitorのact()は本来visitorIdによらずランダムに実行するべき
* アトラクションのユーザの入れ替えは本来そろえるべき？
    * visitorId昇順で処理していることでうまくばらけてる気もする
    * e.g. id=10の人がremainingTime==0のとき id=1の人はoperation>=capacityより受けられないが id>10の人が受ける時にはoperation--+されてる
    * 1stepしかかわらんから大きな問題ではない