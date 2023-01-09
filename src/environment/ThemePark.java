package environment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import setting.SystemConst;
import setting.ThemeParkGraph;

public class ThemePark {
	private final ThemeParkGraph themeParkGraph;
	private final List<ThemeParkNode> themeParkNodes;
	private final List<Visitor> visitors;
	
	private final ArrayList<Observer> observers = new ArrayList<>();
	
	private int entryCount = 0;
	private int simTime = 0;
	
	
	public ThemePark(ThemeParkGraph themeParkGraph, List<ThemeParkNode> themeParkNodes, List<Visitor> visitors) {
		this.themeParkGraph = themeParkGraph;
		this.themeParkNodes = themeParkNodes;
		this.visitors = visitors;
	}
	
	public void arriveVisitor() {
		if (entryCount == SystemConst.MAX_USER) return;
		double entval = SystemConst.ENT_RND.nextDouble();
		String str = Arrays.toString(SystemConst.POISSON_DIS);
		System.out.println(str);
		System.out.println(entval);
		for (int i = 0; i < SystemConst.POISSON_DIS.length; i++) {
			if (entval < SystemConst.POISSON_DIS[i]) {
				break;
			}
			visitors.get(entryCount).enter();
			entryCount++;
		}
	}
	public void sim() {
		
	}
	
	/** getter関連メソッド */
	public int getSimTime() {
		return simTime;
	}
	
	public ThemeParkGraph getThemeParkGraph() {
		return themeParkGraph;
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
