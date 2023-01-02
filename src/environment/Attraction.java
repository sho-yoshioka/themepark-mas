package environment;

import java.util.ArrayDeque;
import java.util.Queue;

public class Attraction extends ThemeParkNode {
	private Queue<Visitor> waitingQueue = new ArrayDeque<>();

	public Attraction(int nodeId, int serviceTime, int capacity) {
		super(nodeId, serviceTime, capacity);
	}
	public Attraction(int nodeId, int serviceTime) {
		super(nodeId, serviceTime, 15);
	}

	@Override
	public boolean hasEmpty() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean canServer() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
