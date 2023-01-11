package environment;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import setting.SystemConst;

public class NodesObserver implements Observer {

	private Date date;
	private String filePath = "./bin/results/";
	private String filename;
	private PrintWriter writer;
	
	public NodesObserver() {
		date = new Date();
		String title = SystemConst.METHOD + "-" + SystemConst.MAX_USER + "-Queue_" + "SEED(" + SystemConst.SIM_SEED + ")";
		filename = filePath + title + ".csv";
		try {
			writer = new PrintWriter(new FileWriter(filename));
		} catch (IOException e) {
			e.printStackTrace();
		}
		makeTitle(title, date);
		makeHeadline();
	}
	@Override
	public void update(ThemePark tp) {
		makeItems(tp);
	}
	@Override
	public void end(ThemePark tp) {
		close();
	}
	private void makeTitle(String title, Date date) {
		writer.println(title);
		writer.println(date);
	}
	private void makeHeadline() {
		writer.print("simulationTime,");
		for(int i = 0; i < SystemConst.NUM_OF_ATTRACTION; i++) {
			writer.print("Attraction[" + (i+1) + "]" + ",");
		}
		writer.print("\n");
	}
	private void makeItems(ThemePark tp) {
		int simT = tp.getSimTime();
		writer.print(simT + ",");
		for (int i = 0; i < SystemConst.NUM_OF_ATTRACTION; i++) {
			Attraction att = (Attraction) tp.getNodeAt(i+1);
			int queueLength = att.getQueueLength();
			writer.print(queueLength + ",");
		}	
		writer.print("\n");
	}
	private void close() {
		writer.close();
		System.out.println(filename + "作成");
	}
}
