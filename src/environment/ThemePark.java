package environment;

import java.util.ArrayList;
import java.util.List;

import setting.NodeFactory;
import setting.ThemeParkGraph;

public class ThemePark {
	private final ThemeParkGraph themeParkGraph;
	private NodeFactory nodeFactory;
	private List<ThemeParkNode> themeParkNodes;
	private List<Visitor> visitors;
	
	private ThemePark(ThemeParkGraph tpg, List<ThemeParkNode> tpn, List<Visitor> visitors) {
		
	}
	public List<ThemeParkNode> createNode() {
		List<ThemeParkNode> tpNodes = new ArrayList<ThemeParkNode>();
		tpNodes = nodeFactory.initNode();
		return tpNodes;
	}
	public List<Visitor> createVisitor(){
		
	}
}
