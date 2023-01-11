package environment;

import java.util.List;

import setting.NodeFactory;
import setting.ThemeParkGraph;
import setting.VisitorFactory;

public class Main {
	public static void main(String[] args) {
		//シミュレーション開始処理
		//テーマパーク要素生成
		ThemeParkGraph tpg = new ThemeParkGraph();
		NodeFactory nodeFactory = new NodeFactory();
		List<ThemeParkNode> nodes = nodeFactory.initNode();
		VisitorFactory visitorFactory = new VisitorFactory();
		List<Visitor> visitors = visitorFactory.initVisitor();
		//テーマパーク生成
		ThemePark tp = new ThemePark(tpg, nodes, visitors);
		Observer fObserver = new FileObserver();
		tp.addObserver(fObserver);
		//ユーザプランニング
		//ユーザ行動
		tp.sim();
	}
}
