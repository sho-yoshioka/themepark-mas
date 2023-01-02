package environment;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Attraction extends ThemeParkNode {
	private Queue<Visitor> waitingQueue = new ArrayDeque<>();
	private int operation;

	public Attraction(int nodeId, int serviceTime, int capacity) {
		super(nodeId, serviceTime, capacity);
	}
	public Attraction(int nodeId, int serviceTime) {
		super(nodeId, serviceTime, 15);
	}

	@Override
	public boolean hasEmpty() {
		if (operation < capacity && waitingQueue.isEmpty()) return true;
		operation++;
		return false;
	}

	@Override
	public boolean canServe(Visitor visitor) {
		if (operation >= capacity) return false;
		//待ち行列の先頭から空き人数分までのサブリストに含まれているか判定
		List<Visitor> visitors = new ArrayList<>(waitingQueue);
		if (visitors.subList(0, capacity - operation + 1).contains(visitor)) {
			waitingQueue.remove(visitor);
			operation++;
			return true;
		}
		return false;
	}
	
	public void registerQueue(Visitor visitor) {
		waitingQueue.add(visitor);
	}
	
	@Override
	public void finishService() {
		operation--;
	}

}
