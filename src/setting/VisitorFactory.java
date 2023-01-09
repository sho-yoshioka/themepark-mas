package setting;

import java.util.ArrayList;
import java.util.List;

import environment.Visitor;

public class VisitorFactory {
	public List<Visitor> initVisitor() {
		List<Visitor> visitors = new ArrayList<>() ;
		for (int i = 0; i < SystemConst.MAX_USER; i++) {
			Visitor v = new Visitor();
			visitors.add(v);
		}
		return visitors;
	}
}