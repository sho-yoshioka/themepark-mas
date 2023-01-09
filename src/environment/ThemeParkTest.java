package environment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import setting.NodeFactory;
import setting.ThemeParkGraph;

class ThemeParkTest {

	@Test
	void test() {
		ThemeParkGraph tpg = new ThemeParkGraph();
		List<Visitor> visitors = new ArrayList<Visitor>();
		Visitor v1 = new Visitor();
		Visitor v2 = new Visitor();
		visitors.add(v1);
		visitors.add(v2);
		NodeFactory nf = new NodeFactory();
		List<ThemeParkNode> nodes = nf.initNode();
		ThemePark tp = new ThemePark(tpg, nodes, visitors);
		for(int i = 0; i < 10; i++) {
			tp.arriveVisitor();
		}
	}

}
