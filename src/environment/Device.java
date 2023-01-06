package environment;

import java.util.List;
import java.util.ArrayList;

public abstract class Device {
	private List<Integer> ETA = new ArrayList<>();
	public abstract double costEstimate(int nodeId, int time);
	public abstract List<Integer> firstPlan();
	public abstract List<Integer> searchPlan(Visitor visitor);
	public abstract int requestQueueLenghtAt(int nodeId);
}
