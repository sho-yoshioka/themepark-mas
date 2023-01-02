package environment;

import java.util.ArrayList;
import java.util.List;

import setting.NodeFactory;
import setting.ThemeParkGraph;

public class ThemePark {
	private final ThemeParkGraph themeParkGraph;
	private List<ThemeParkNode> themeParkNodes;
	private List<Visitor> visitors;
	
	private NodeFactory nodeFactory;
	
	private int simTime;
	
	
	public ThemePark(ThemeParkGraph themeParkGraph, List<ThemeParkNode> themeParkNodes, List<Visitor> visitors) {
		this.themeParkGraph = themeParkGraph;
		this.themeParkNodes = themeParkNodes;
		this.visitors = visitors;
	}
	
	public int getSimTime() {
		return simTime;
	}
	public ThemeParkNode getNodeAt(int index) {
		return themeParkNodes.get(index);
	}
}
