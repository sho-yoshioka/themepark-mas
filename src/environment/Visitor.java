package environment;

import java.util.List;

import setting.EnumStatus;
import setting.SystemConst;

public class Visitor {
	private EnumStatus actStatus;

	private int startTime;
	private int endTime;
	private int waitingTime = 0;
	private int movingTime = 0;
	private int travelTime;
	private int remainingTime;
	
	private int position;
	private List<Integer> plan;
	
	public void act(ThemePark tp) {
		ThemeParkNode currentNode = tp.getNodeAt(position);
		switch(actStatus) {
		case INACTIVE:
			//入口に出現したらスタート
			if (position == 0) {
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
			if (currentNode.canServe(this)) {
				actStatus = EnumStatus.SERVED;
				remainingTime = currentNode.getServiceTime();
			}
			break;
		
		case SERVED:
			remainingTime--;
			if (remainingTime == 0) {
				//TODO アトラクションの処理を別でやって更新した方がいいかも？間違ってたとしても1stepしかずれないから多分誤差
				currentNode.finishService();
				if (currentNode.getCapacity() == Integer.MAX_VALUE) movingTime += currentNode.getServiceTime();
				currentNode = move(tp);
				if (position == SystemConst.EXIT) {
					endTime = tp.getSimTime();
					travelTime = endTime - startTime;
					actStatus = EnumStatus.TERMINATED;
				} else {
					//Roadノードorアトラクション待ち行列なし
					if (currentNode.hasEmpty()) {
						actStatus = EnumStatus.SERVED;
						remainingTime = currentNode.getServiceTime();
					} else {
						//アトラクションノード待ち行列あり
						((Attraction) currentNode).registerQueue(this);
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
	 * act()で呼び出すprivate関数
	 * プランが示す次ノードにpositionを移動し、移動前のノードをプランから除く。
	 * @param tp ThemeParkのインスタンス
	 * @return 移動先のThemeParkNode
	 */
	private ThemeParkNode move(ThemePark tp) {
		position = plan.get(1);
		plan.remove(0);
		return tp.getNodeAt(position); 
	}
}

