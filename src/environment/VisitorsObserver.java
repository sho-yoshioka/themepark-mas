package environment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import setting.SystemConst;

public class VisitorsObserver implements Observer {

	private Date date;
	private String filename;
	private PrintWriter writer;
	
	public VisitorsObserver() {
		date = new Date();
		String title = SystemConst.METHOD + "-" + SystemConst.MAX_USER + "_" + "SEED(" + SystemConst.SIM_SEED + ")";
		filename = SystemConst.FILE_PATH + title + ".csv";
		try {
			writer = new PrintWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		makeTitle(date, title);
		makeHeadline();
	}
	
	@Override
	public void update(ThemePark tp) {
	}
	
	@Override
	public void end(ThemePark tp) {
		makeItems(tp);
		close();
	}
	/**
	 * 1,2行目に記載
	 * @param title ファイルのタイトル
	 * @param date ファイル作成日時
	 */
	private void makeTitle(Date date, String title) {
		writer.println(date);
		writer.println(title);
	}
	/** csvファイルの先頭行を作成. Visitorのフィールド */
	private void makeHeadline() {
		writer.print("VisitorId,");
		writer.print("waitingTime,");
		writer.print("movingTime,");
		writer.print("travelTime,");
		writer.print("\n");
	}
	/**
	 * sim()終了後に全visitorの情報を記録
	 * @param tp 観測対象のテーマパークインスタンス
	 */
	private void makeItems(ThemePark tp) {
		for (int i = 0; i < SystemConst.MAX_USER; i++) {
			Visitor visitor = tp.getVisitorAt(i);
			int visitorId = visitor.getId();
			int wTime = visitor.getwaitingTime();
			int mTime = visitor.getMovingTime();
			int tTime = visitor.getTravelTime();
			writer.print(visitorId + ",");
			writer.print(wTime + ",");
			writer.print(mTime + ",");
			writer.print(tTime + ",");
			writer.print("\n");
		}
	}
	/** ファイルの終了処理 */
	private void close() {
		writer.close();
		System.out.println(filename + "作成");
	}
}
