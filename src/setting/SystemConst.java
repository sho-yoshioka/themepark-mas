package setting;

import java.util.Random;

public class SystemConst {
	/** インスタンスの生成禁止 */
	private SystemConst() {
	}
	/** 0: 入口, 1~10: アトラクション, 11~19: 道路, 20: 出口 */
	public static final int GRAPH_SIZE = 21;
	public static final int ENTRANCE = 0;
	public static final int EXIT = 20;
	public static final int NUM_OF_ATTRACTION = 10;
	
	/** シミュレーションの設定 */
	public static final int MAX_USER = 5000;
	public static final int MAX_TIME = 50000;
	public static final int SIM_SEED = 0;
	public static final String METHOD = "CCE";
	
	/** 訪問アトラクション設定 */
	public static final long DECIDE_ATT_SEED = SIM_SEED;
	public static final int NUM_ATT_TO_VISIT = 4;
	public static final Random DECIDE_ATT_RND = new Random(DECIDE_ATT_SEED);
	
	/** ポアソン分布に従った入場で使う乱数とパラメータ */
	public static final double POISSON_RMD = 0.0001 * MAX_USER;
	public static final long ENT_SEED = SIM_SEED;
	public static final Random ENT_RND = new Random(ENT_SEED);
	public static final double[] POISSON_DIS = SystemCalc.poissonDis();
	
	/** 山登り法の回数とプランニングインターバル */
	public static final int VISITOR_PLANNING_INTERVAL = 300;
	public static final int LOCAL_SEARCH_TIMES = 15;
	
	/** resultファイルの保存パス */
	public static String FILE_PATH = "./results/" + METHOD + "/" + MAX_USER + "/";
}