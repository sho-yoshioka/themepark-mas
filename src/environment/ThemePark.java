package environment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import setting.SystemConst;
import setting.ThemeParkGraph;

public class ThemePark {
	/** ThemeParkの設定 */
	private final ThemeParkGraph themeParkGraph;
	private final List<ThemeParkNode> themeParkNodes;
	private final List<Visitor> visitors;
	
	private final ArrayList<Observer> observers = new ArrayList<>();
	
	private int entryCount = 0;	
	private int exitCount = 0;
	private int simTime = 0;
	
	public ThemePark(ThemeParkGraph themeParkGraph, List<ThemeParkNode> themeParkNodes, List<Visitor> visitors) {
		this.themeParkGraph = themeParkGraph;
		this.themeParkNodes = themeParkNodes;
		this.visitors = visitors;
	}
	
	/**
	 * [0.0,1.0)のdouble乱数を発生させポアソン分布に従った乱数(k回入場)を発生
	 * entryCountがMAX_USERに到達するまで有効
	 */
	private void arriveVisitors() {
		if (entryCount == SystemConst.MAX_USER) return;
		double entval = SystemConst.ENT_RND.nextDouble();
		for (int i = 0; i < SystemConst.POISSON_DIS.length; i++) {
			if (entval < SystemConst.POISSON_DIS[i]) {
				break;
			}
			visitors.get(entryCount).enter();
			entryCount++;
		}
	}
	
	/** 入場済みのユーザが順番にplanSearch() */
	private void planVisitors() {
		for (int i = 0; i < entryCount; i++) {
			visitors.get(i).planSearch(this);
		}
	}
	
	/** 入場済みのユーザが順番に行動act() */
	private void actVisitors() {
		for (int i = 0; i < entryCount; i++) {
			visitors.get(i).act(this);
		}
	}
	/** 
	 * visitorから呼び出される関数.退場のcountをする.
	 * 毎回EnumStatus.TERMINATEDの人数を数えればテーマパーク側だけで完結はするが、処理無駄な気がするからこうしてる.
	 */
	public void exitVisitor() {
		exitCount++;
	}

	/** simulationの1step. 入場〜プランニング〜通知〜行動を入場済みユーザに対して行う */
	public void simStep() {
		arriveVisitors();
		planVisitors();
		notifyObservers();
		actVisitors();
		simTime++;
	}
	
	/** 
	 * 全ユーザが退場するまでsimStep()を繰り返す.
	 * 最後に記録を取るため・終了処理としてendObservers()を呼び出す.
	 */
	public void sim() {
		//System.out.println("SimStart(t = " + simTime + ")");
		while(true) {
			simStep();
			//System.out.println("Simstep(t = " + simTime + ")");
			if (exitCount == SystemConst.MAX_USER) {
				System.out.println("全ユーザが退場しました。");
				break;
			} else if (simTime == SystemConst.MAX_TIME) {
				System.out.println("simTimeが" + simTime + "stepに到達したため強制終了");
				break;
			}
		}
		endObservers();
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
	
	public Visitor getVisitorAt(int index) {
		return visitors.get(index);
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
    public void endObservers() {
        Iterator<Observer> iterator = observers.iterator();
        while (iterator.hasNext()) {
            Observer observer = (Observer)iterator.next();
            observer.end(this);
        }
    }
}
