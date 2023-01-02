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
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

	@Override
	public boolean canServer() {
		// TODO 自動生成されたメソッド・スタブ
		return false;
	}

}
