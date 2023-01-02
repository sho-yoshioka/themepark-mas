package setting;

public class SystemConst {
	/** インスタンスの生成禁止 */
	private SystemConst() {
	}
	/**
	 * 0 : 入口
	 * 1~10 : アトラクション
	 * 11~19 : 道路
	 * 20 : 出口
	 */
	public static final int GRAPH_SIZE = 21;
	public static final int ENTRANCE = 0;
	public static final int EXIT = 20;
	public static final int MAX_USER = 5000;
	public static final int MAX_TIME = 30000;
	public static final int ATTRACTION_TO_VISIT = 4;
}
