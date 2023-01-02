package environment;

public class Entrance extends ThemeParkNode {

	public Entrance(int nodeId, int serviceTime, int capacity) {
		super(nodeId, serviceTime, capacity);
	}
	public Entrance() {
		super(0, 0, Integer.MAX_VALUE);
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
