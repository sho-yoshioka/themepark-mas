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