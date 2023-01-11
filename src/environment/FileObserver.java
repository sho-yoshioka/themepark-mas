package environment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import setting.SystemConst;

public class FileObserver implements Observer {

	private String filename;
	private PrintWriter writer;
	
	@Override
	public void update(ThemePark tp) {
		Date date = new Date();
		String title = SystemConst.METHOD + "-" + SystemConst.MAX_USER + "_" + "SEED(" + SystemConst.SIM_SEED + ")";
		filename = title + ".csv";
		try {
			writer = new PrintWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		makeTitle(title, date);
		makeHeadline();
		makeItems(tp);
		close();
		System.out.println(filename + "作成");
	}
	private void makeTitle(String title, Date date) {
		writer.println(title);
		writer.println(date);
	}
	private void makeHeadline() {
		writer.print("VisitorId,");
		writer.print("waitingTime,");
		writer.print("movingTime,");
		writer.print("travelTime,");
		writer.print("\n");
	}
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
	private void close() {
		writer.close();
	}
}
