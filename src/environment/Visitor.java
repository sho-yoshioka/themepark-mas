package environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import setting.EnumStatus;
import setting.SystemConst;

public class Visitor {
	private Device device;
	
	private int visitorId;
	private static int visitorCount = 0;
	/** 状態変数act()の条件分岐 */
	private EnumStatus actStatus = EnumStatus.INACTIVE;

	/** time系 */
	private int startTime = 0;
	private int endTime;
	private int waitingTime = 0;
	private int movingTime = 0;
	private int travelTime = 0;
	private int remainingTime;
	
	/** 
	 * positionはThemeParkNodeで管理していないのは依存度を下げるため 
	 * ThemeParkインスタンスにpositionを渡すことでNodeのメソッドを呼び出す
	 */
	private int position = -1;
	private List<Integer> attractionToVisit;
	private List<Integer> plan;
	
	public Visitor() {
		visitorId = visitorCount;
		visitorCount++;
		initVisitor();
		device = new CCEDevice(visitorId);
	}
	
	/** getter メソッド ~~~ */
	public int getPosition() {
		return position;
	}
	public int getId() {
		return visitorId;
	}
	public EnumStatus getActStatus() {
		return actStatus;
	}
	public int getwaitingTime() {
		return waitingTime;
	}
	public int getMovingTime() {
		return movingTime;
	}
	public int getTravelTime() {
		return travelTime;
	}
	public int getRemainigTime() {
		return remainingTime;
	}
	public List<Integer> getAttractionToVisit() {
		return attractionToVisit;
	}
	/** ~~~ getter メソッド */
	
	
	/**
	 * act()で呼び出すprivate関数
	 * プランが示す次ノードにpositionを移動し、移動前のノードをプランから除く。
	 * 移動先アトラクションの場合はリストから消す
	 * @param tp ThemeParkのインスタンス
	 * @return 移動先のThemeParkNode
	 */
	private ThemeParkNode move(ThemePark tp) {
		position = plan.get(1);
		if (attractionToVisit.contains((Integer)position)) {
			attractionToVisit.remove((Integer)position);
		}
		plan.remove(0);
		return tp.getNodeAt(position); 
	}
	
	/**
	 * 
	 * @param tp
	 */
	public void act(ThemePark tp) {
		ThemeParkNode currentNode = tp.getNodeAt(position);
		switch(actStatus) {
		case INACTIVE:
			//入口に出現したらスタート
			if (position == SystemConst.ENTRANCE) {
				startTime = tp.getSimTime();
				//次ノードへ移動して前ノードを除く
				currentNode = move(tp);
				if (currentNode.hasEmpty()) {
					actStatus = EnumStatus.SERVED;
					remainingTime = currentNode.getServiceTime();
				} else {
					//今回の設定では入口の隣接ノードはRoadノードなので起こり得ない
					//TODO　アトラクションがありえるなら待ち行列に登録する
					System.exit(2);
				}
			}
			break;
		
		case WAITING:
			waitingTime++;
			if (currentNode.canServe(getId())) {
				actStatus = EnumStatus.SERVED;
				remainingTime = currentNode.getServiceTime();
			}
			break;
		
		case SERVED:
			remainingTime--;
			if (remainingTime == 0) {
				//TODO アトラクションの処理を別でやって更新した方がいいかも？間違ってたとしても1stepしかずれないから多分誤差
				currentNode.finishService();
				if (currentNode.getCapacity() == Integer.MAX_VALUE) {
					movingTime += currentNode.getServiceTime();
				}
				currentNode = move(tp);
				if (position == SystemConst.EXIT) {
					endTime = tp.getSimTime();
					travelTime = endTime - startTime;
					actStatus = EnumStatus.TERMINATED;
					System.out.println("VisitorID [" + visitorId + "] が退場");
					exit(tp);
				} else {
					//Roadノードorアトラクション待ち行列なし
					if (currentNode.hasEmpty()) {
						actStatus = EnumStatus.SERVED;
						remainingTime = currentNode.getServiceTime();
					} else {
						//アトラクションノード待ち行列あり
						((Attraction) currentNode).registerQueue(getId());
						actStatus = EnumStatus.WAITING;
					}
				}
			}
			break;
			
		case TERMINATED:
			break;
		
		}
	}
	/** 
	 * 操作側(ThemePark.sim())から呼び出すメソッド 
	 * 分布に従って入場
	 * */
	public void enter() {
		position = SystemConst.ENTRANCE;
		System.out.println("VisitorID [" + visitorId + "] が入場");
	}
	public void exit(ThemePark tp) {
		tp.exitVisitor();
	}
	
	/**
	 * 重複なしの乱数をListのshuffleで作成して訪問数分のサブリストを返す
	 */
	private List<Integer> setAttractionToVisit() {
		List<Integer> attList = new ArrayList<>();
		for (int i = 0; i < SystemConst.NUM_OF_ATTRACTION; i++) {
			attList.add(i + 1);
		}
		Collections.shuffle(attList, SystemConst.DECIDE_ATT_RND);
		return new ArrayList<>(attList.subList(0, SystemConst.NUM_ATT_TO_VISIT));
	}
	
	public void initVisitor() {
		attractionToVisit = setAttractionToVisit();
	}
	
	public void planSearch(ThemePark tp) {
		if (actStatus == EnumStatus.TERMINATED) return;
		if (startTime == 0 || (tp.getSimTime() - startTime) % SystemConst.VISITOR_PLANNING_INTERVAL == 0) {
			plan = device.searchPlan(tp, this);
		}
	}
	
}

