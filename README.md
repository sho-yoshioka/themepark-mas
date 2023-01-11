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
    
---

## 20230104(f4832ed->)
### 備忘録
* Attractionのメソッドを単体Test済み。
* 即座にサービスを受けるパターンと待ち行列に並ぶパターンの処理は完成
* JUnitのassertionはテストケースの中に複数あってもオッケー

### 変更履歴
* AttractionTest.java 作成
* Attraction.java 完成
* Visitorを抽象クラスから普通のクラスへ。
    * テストのためだが、別にabstractでなくていい

---
    
## 20230105(HEAD -> 6cd6d6)
### VisitorFeature
#### 備忘録
* Visitorの振る舞いを実装
* [分布に従う乱数](http://www1.cts.ne.jp/~clab/hsample/Math/Math5.html)
    * rnd < 確率になるまで、繰り返し乱数を発生させた回数
* [ポアソン分布](https://bellcurve.jp/statistics/course/6984.html)
    * 単位時間あたりrmd回発生する事象が、単位時間の間にk回起こる確率を返す
    * 二項分布の期待値npが一定
* [重複なしの乱数生成](https://www.sejuku.net/blog/22308)
    * Listの値をshuffleで並び替えて取得
    * Listは同じリストをshuffleし続けても、新規Listをshuffleしても同じ
    
#### 変更履歴
* SystemCalc.java(new)
    * ポアソン分布や階乗などの計算を実装（分布に従う入場はThemePark側で処理すべき）
* SystemConst.java
    * ポアソン分布などの定数を追加、整理
* Main.java
    * 重複なし乱数生成にあたり、Listは毎回作っても偏らないかを調査するためにテスト的に使用
    * 結果として、同じリストを使い続けても毎回作っても同じ
* Visitor.java
    * 初期処理として回るアトラクションを決める関数
    * 初期ポジションを定数に変更
    * 初期化処理の関数をインターフェース的に定義
* VisitorTest.java
    * 入場処理のテストを実行
    * 行動のテストはテーマパークがインスタンス化できるようになれば実行
    
#### TODO
* VisitorTestのact()実装
* プランサーチをDeviceクラスに委託する場合の処理


### planSearchFeature
    
#### 備忘録
* [値返却と参照渡し](http://teqspaces.com/Java/6)
    * ダイクストラ法で得るプランのListを得る時にVisitorのフィールドを参照渡しすべきかで調べた。
    * ダイクストラ()内でListを定義し、returnするほうがバグが少なそう。(フィールドをそのように使うと追いかけるのが大変)
    * メソッドの呼び出し元に値を返す2種類の方法
    * 参照の値渡し: 引数にオブジェクトを渡し、そのオブジェクトに直接設定する方法
    * 値返却: メソッドの返却値として渡す方法
    * 値を設定してもらうだけのために参照渡しにするのは「Bad Practice」
    * 参照渡しは呼び出し元との依存度が高くなる
    * 参照渡しは、publicメソッドや共通処理として利用するメソッドではなるべく避ける
    * 長いソースをメソッドに切り出したようなprivateメソッドに留める
    
* [Git stash](https://qiita.com/chihiro/items/f373873d5c2dfbd03250)
    * commitしたくないけど違う作業がしたくなった(ブランチの移動含む)

#### 変更履歴
* ThemeParkGraph.java
    * dijkstra()の経路探索をsource->destだけの１回処理にした
    * dijkstra()の戻り値としてgetRoute()で取得するList<Integer>に変更(void->List<>
#### TODO
* 始点がrouteに含まれるため、４回実行してplanを繋げた時に終点(n回)と始点(n+1回)が重複する
    * n >= 2 のときは返却するList<> routeの[index=0]を消せばいい？
   
---
    
## 20230106
### planSearchFeature
#### 54ecc2a->
#####　変更履歴
* Attractionの待ち行列はVisitor型でなくint型(visitorId)での処理に変更

#### 882a171->
##### 備忘録
* [内部クラス概要](https://www.sejuku.net/blog/22637)
* [内部クラスの種類](https://style.potepan.com/articles/30021.html#Java)
    * あるクラス内のフィールド変数やメソッドにアクセスするクラスを宣言する場合に内部クラスとして宣言
    * 外部クラスのフィールドやメソッドを「利用」は可能だが、「直接アクセス」できない
    * databaseに使えるかと思ったけど微妙かも
* 結局Deviceからのアクセスはtpを引数で渡すことにした

##### 変更履歴
* ダイクストラ法の処理をDeviceクラスに移動
* ThemeParkのObserverインターフェース作成

## 20230107
### planSearchFeature
#### 4ca0946->
##### 備忘録
> 20230106: Attractionの待ち行列はVisitor型でなくint型(visitorId)での処理に変更

* Objectじゃなくintで渡す場合はremove()でアッパークラスにキャストしないとindexとして判断されてバグが起きる（修正済み）

##### 変更履歴
* Graph.java
    * 新規作成
    * Node/Edgeクラスはこちらで宣言
    * dijkstra関係のメソッドをGraphのstaticメソッドに移動
* Attraction.java
    * waitingQueueの型をArrayDequeue -> LinkedList
    * pqueueを取得するのにindexOf()が便利
* Device.java
    * デバイスごとに乱数をフィールドとして定義
    * プランサーチを再現できるようにするため
    * ユーザごとに乱数を持っているべきと考えた
* ThemePark.java
    * themeParkGraphのgetter()を定義
    * 定数でもいい気もするが・・・
* Visitor.java
    * プランサーチで必要な変数のgetter()を定義
        * 回るノードとか状態変数
* SystemConst.java
    * LOCALSEARCHの繰り返し回数
* ThemeParkGraph.java
    * Graaph/Edge/Node classを削除
    * Graph.java を新規作成してそちらへ移動
* Device.java
    * 基礎的な処理を定義
* CCEDevice.java
    * CCEのコスト推定などを実装

##### TODO
* Device関連のテストケース作成

---

## 20230108
### planSearchFeature
#### ca9db73->
##### 備忘録
* 配列のlastIndexはsize()-1なので注意。結構しょうもないミスが多かった（Graph.java)

##### 変更履歴
* CCEDevice.java
    * EvalPlan()で返却する予測滞在時間に(-1)を乗じて返すことで効用の値が大きいほうが優れていると判断することにした
    * CostEstimate()で入口でのコストをreturn0;
        * actStatus == INACTIVEで判断すると無限ループする
        * NodeId == ENTRANCEの判断に変更
        * これでもt=simTのままなので条件分岐に入口でないINACTIVEは道路とした。
    * searchPlan()は出口の際にreturn null;

* Visitor.java
    * actStatusの初期状態をINACETIVEで初期化
    * planの更新をできてなかった。deviceに委譲して帰ってきたものをフィールドに代入するように変更(関数はvoidに)

* Graph.java
    * overlapIndexはsize()でなくsize()-1が正しいので訂正
    
* CCEDeviceTest.java
    * 全メソッドのテストを結果的にしたことになっている。
    * ThemeParkのメソッドもいくつかユニットテストになってる
    
##### TODO
* 回ったアトラクションはリストから消す(Visitor.act())　
    * 20230109実装

### userEnter
#### b4ff31b->
##### 変更履歴
* SystemConst.java/SystemCalc.java
    * poisson分布入場のために分布の配列を作成
    
* ThemePark.java
    * arriveVisitor()
        * poisson分布によってユーザの入場
        
* ThemeParkTest.java
    * arriveVisitor()のテスト
    * ポアソン分布はok
    
---

## 20230109
### release-1.0
#### 9975bc6->
##### 変更履歴
* bumped version number to 1.0

#### 6868f61->
##### 備忘録
* 細かいテストはしていないが、一応sim()は回りそうな段階
* statementの更新タイミングはいつがいいのか
    * plan決定時にstatement送信でよさそう
    * 前半のユーザはstatementが不正確の代わりに先に動ける
    * 後半のユーザはstatementが正確の代わりに遅く動く
    * アトラクション1つの場合考えるとこれであってそう
* act()とplan()のタイミング
    * plan()フェーズとact()フェーズは切り離すべき
    * SCEなら各ユーザ行動がplan()->act()の単位の場合,statementに加えて行列長も大きくなってします
    * CCEは本来行列の振動が起こるはずなので、plan()時では行列の更新はされていないと考えるのが普通
    
##### 変更履歴
> 20230108
> * 回ったアトラクションはリストから消す(Visitor.act())　

* Visitor.java
    * アトラクション削除move()内で実装
    * initをコンストラクタで呼ぶように変更
    * TERMINATEDに遷移する際にexit()でThemePark側の退場カウントを++;
    * searchPlan()内でユーザが退場済みの際にnullを返していたが,呼び出し元のVisitor.planSearch()で処理
* VisitorFactory.java
    * 戻り値をArrayListに
* NodeFactory.java
    * 戻り値をArrayListに
* CCEDevice.java
    * searchPlan()内でユーザが退場済みの際にnullを返していたが,呼び出し元のVisitor.planSearch()で処理
* ThemePark.java
    * sim()の内部関数の実装
* Main.java
    * sim()のためインスタンス作成
    
##### TODO
* `System.out.println()`の削除をしていく
* release-1.0のリリース

## 20230111
### release-1.0
#### 796720a->
##### 備忘録
* データ取るためのObserver作成

##### 変更履歴
* SystemConst.java
    * POISSON_RMDをMAX_USER*0.0001で自動計算するように変更
    * METHOD名を追加（後々はこれでDeviceを選択するようにプログラム改変）
    * SIM_SEEDを追加して、各SEEDをこの値で初期化（それぞれの処理が一様分布ならSEED同じでも大丈夫と判断)
        * だめそうならSEED+1,SEED+2で初期化する
* FileObserver.java
    * ThemeParkクラスのobserverでsim()が終わるとnotify()される。
    * 各個人ユーザの結果をcsvファイルで出力
* Graph.java
    * route()のprint()をコメントアウト
* Visitor.java
    * Timeフィールドのgetterを追加(FileObserverのため)
* ThemePark.java
    * ポアソン分布表示のためのprintln()削除（確認用に表示してただけ）
* Main.java
    * observerの追加処理
    
#### cef06f5->
##### 備忘録
* Nodeの行列長を各tで取得するObserver追加
* 各ConcreteObserverはコンストラクタでファイル作成処理
* bin/resultsを作成し、そこを保存場所として`path`指定

##### 変更履歴
* Main.java
    * NodeObserver追加
* Observer.java
    * `abstract end()`インターフェース定義（ファイルclose()などの終了処理のため）
    * `update()`は`simStep()`で繰り返し、`end()`は`sim()`で一度呼び出す
* NodeObserver.java(new)
    * Attraction[i]の行列長を取得
    * `update()`を`ThemePark.simStep()`ごとに呼び出す
* VisitorObserver.java(new)
    * FileObserverを名前変えただけ
* ThemePark.java
    * `endObserver()`の追加。最後に`sim()`で呼び出す
    * `notifyObserver()`はユーザの行動前に呼び出す（t = ０〜退場時刻ー１）
    
#### 0e1bb84->
##### 備忘録   
* コメントの整理やフィールドを整えた
* 結果ファイルの保存場所を./results/METHOD/MAX_USERに変更(bin/results/から）
* ファイルパスを定数に
##### 変更履歴
* SystemConst.java
    * filepath変更
* <? implements Observer>
    * filepathを定数から参照に変更歴
 