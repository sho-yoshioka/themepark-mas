package environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import setting.NodeFactory;
import setting.ThemeParkGraph;

public class ThemePark {
	private ArrayList<Observer> observers = new ArrayList<>();
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
	
	/** Observer関連メソッド */
	public void addObserver(Observer observer) {
        observers.add(observer);
    }
    public void deleteObserver(Observer observer) {
        observers.remove(observer);
    }
    public void notifyObservers() {
        Iterator<Observer> iterator = observers.iterator();
        while (iterator.hasNext()) {
            Observer observer = (Observer)iterator.next();
            observer.update(this);
        }
    }
}
