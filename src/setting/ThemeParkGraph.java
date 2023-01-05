package setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ThemeParkGraph extends Graph{
	//コンストラクタ生成時に
	//初期化してしまう。
	public ThemeParkGraph() {
		super(initEdge(), SystemConst.GRAPH_SIZE);
	}
	
	//グラフ構造を帰る場合はここを変えればいい
	//初期ファイル用意して読み込みたかったけど、めんどくさかった
	private static List<Edge> initEdge() {
		List<Edge> edges = Arrays.asList(
			new Edge(0, 11, 1),
			new Edge(1, 12, 1),
			new Edge(2, 13, 1),
			new Edge(3, 13, 1),
			new Edge(4, 16, 1),
			new Edge(5, 19, 1),
			new Edge(6, 19, 1),
			new Edge(7, 18, 1),
			new Edge(8, 17, 1),
			new Edge(9, 17, 1),
			new Edge(10, 14, 1),
			new Edge(11, 12, 1), new Edge(11, 14, 1), new Edge(11, 20, 1),
			new Edge(12, 1, 1),new Edge(12, 11, 1),new Edge(12, 13, 1),new Edge(12, 15, 1),
			new Edge(13, 2, 1),new Edge(13, 3, 1),new Edge(13, 12, 1),new Edge(13, 16, 1),
			new Edge(14, 10, 1),new Edge(14, 11, 1),new Edge(14, 15, 1),new Edge(14, 17, 1),
			new Edge(15, 12, 1),new Edge(15, 14, 1),new Edge(15, 16, 1),new Edge(15, 18, 1),
			new Edge(16, 4, 1),new Edge(16, 13, 1),new Edge(16, 15, 1),new Edge(16, 19, 1),
			new Edge(17, 8, 1),new Edge(17, 9, 1),new Edge(17, 14, 1),new Edge(17, 18, 1),
			new Edge(18, 7, 1),new Edge(18, 15, 1),new Edge(18, 17, 1),new Edge(18, 19, 1),
			new Edge(19, 5, 1),new Edge(19, 6, 1),new Edge(19, 16, 1),new Edge(19, 18, 1)
		);
		return edges;
	}
	
}

class Edge {
	int sorce, dest, weight;
	
	public Edge(int sorce, int dest, int weight) {
		this.sorce = sorce;
		this.dest = dest;
		this.weight = weight;
	}
}


class Node {
	int vertex, weight;
	
	/**
	 * 
	 * @param vertex 頂点の通し番号
	 * @param weight 確定した最短コスト
	 */
	public Node(int vertex, int weight) {
		this.vertex = vertex;
		this.weight = weight;
	}
}

class Graph {
	protected List<List<Edge>> adjList = null;
	
	protected Graph(List<Edge> edges, int n){
		adjList = new ArrayList<>();
		
		for (int i = 0; i < n; i++) {
			adjList.add(new ArrayList<>());
		}
		
		for (Edge edge : edges) {
			adjList.get(edge.sorce).add(edge);
		}
	}
}

class Main {
	/**
	 * prev[i] < 0 となるprev[source]まで実行する再帰関数
	 * 呼び出し元のListに経由地をadd()する。dijkstra()で呼び出される
	 * 
	 * @param prev 最小距離を更新した際の経由地の配列
	 * @param i 注目しているノード, 再帰関数でprev[i]とすることで経由地を
	 * @param route 経由地リスト
	 */
	private static void getRoute(int[] prev, int i, List<Integer> route) {
		if (i >= 0) {
			getRoute(prev, prev[i], route);
			route.add(i);
		}
	}
	
	/**
	 * @param graph 探索するグラフ構造
	 * @param source 経路探索の出発地点
	 * @param n グラフの頂点の個数
	 */
	public static List<Integer> dijkstra(Graph graph, int source, int dest, int n) {
		PriorityQueue<Node> minHeap;
		//TotalFunctionインタフェースのapplyAsIntオーバーライドのラムダ形式でTotalFunctionをインスタンス化してるはず(20221227変更履歴を参照)
		minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));	
		minHeap.add(new Node(source, 0));
		
		//ソースから'v'までの初期距離は無限大として設定
		List<Integer> dist;
		dist = new ArrayList<>(Collections.nCopies(n, Integer.MAX_VALUE));
		dist.set(source, 0);
		
		//最小距離が確定したかどうか
		boolean[] done = new boolean[n];
		done[source] = true;
		
		//最小距離が確定した際の経由地を記録
		int[] prev = new int[n];
		prev[source] = -1;
		
		while (!minHeap.isEmpty()) {
			//最小距離確定したノード
			Node node = minHeap.poll();
			int u = node.vertex;
			
			//探索目的の最短経路が確定した時点で終了
			if(u == dest) break;
			
			//最小距離が確定したノード'u'の隣接ノード'v'に対して実行
			for (Edge edge : graph.adjList.get(u)) {
				int v = edge.dest;
				int weight = edge.weight;
				
				//'u'を経由した際の'v'までの距離を計算、dist[v]と比較して最短経路を更新
				if(!done[v] && dist.get(u) + weight < dist.get(v)) {
					dist.set(v, dist.get(u) + weight);
					prev[v] = u;
					minHeap.add(new Node(v, dist.get(v)));
				}
			}
			done[u] = true;
		}
		
		List<Integer> route = new ArrayList<>();
		
		//'dest'までの経路をprev[]を使用して求める
		if (dest != source && dist.get(dest) != Integer.MAX_VALUE) {
			getRoute(prev, dest, route);
			System.out.printf("Path (%d -> %d): Minimum cost = %d, Route = %s\n", source, dest, dist.get(dest), route);
		}
		return route;
	}
	
	public static void main(String[] args) {
		ThemeParkGraph themeParkGraph = new ThemeParkGraph();
		List<Integer> mainList = new ArrayList<>();
		mainList = dijkstra(themeParkGraph, 10, 15, SystemConst.GRAPH_SIZE);
		System.out.println(mainList);
		mainList = dijkstra(themeParkGraph, 15, 15, SystemConst.GRAPH_SIZE);
		System.out.println(mainList);
	}
}
	