package setting;

public class SystemCalc {

	/** インスタンスの生成禁止 */
	private SystemCalc() {
	}
	
	/** 階乗を計算 */
	public static int factorial(int n) {
		if(n == 0) {
			return 1;
		}
		return n * factorial(n-1);
	}
	
	/** 単位時間あたりrmd回発生する事象が、単位時間の間にk回起こる確率を返す */
	public double poisson(double rmd, int k) {
		return Math.pow(rmd, k) * Math.exp(-rmd) / factorial(k);
	}

}
