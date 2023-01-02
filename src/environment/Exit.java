package environment;

public class Exit extends ThemeParkNode {

	public Exit(int nodeId, int serviceTime, int capacity) {
		super(nodeId, serviceTime, capacity);
	}
	public Exit() {
		super(20, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	@Override
	public boolean hasEmpty() {
		System.exit(1);
		return true;
	}

	@Override
	public boolean canServe(Visitor visitor) {
		return false;
	}
	@Override
	public void finishService() {
		//何もしない
	}

}
