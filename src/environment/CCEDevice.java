package environment;

import java.util.ArrayList;
import java.util.List;

import setting.SystemConst;
import setting.EnumStatus;
import setting.Graph;

public class CCEDevice extends Device {

	public CCEDevice(int userId) {
		super(userId);
	}

	@Override
	public int costEstimate(int nodeId, int time, ThemePark tp, Visitor visitor) {
		if (time == tp.getSimTime()) {
			if (nodeId == SystemConst.ENTRANCE ) {
				return 0; 
			}
			//入口じゃなく、INACTIVEになるのは初期入場時のplan[1]のRoadのみ
			if (visitor.getActStatus() == EnumStatus.INACTIVE) {
				return tp.getNodeAt(nodeId).getServiceTime();
			} else if (visitor.getActStatus() == EnumStatus.SERVED) {
				return visitor.getRemainigTime();
			} else {
				Attraction attraction = ((Attraction)tp.getNodeAt(nodeId));
				int pQueue = attraction.getPriorQueueLength(ownerId);
				int window = attraction.getCapacity();
				int serviceTime = attraction.getServiceTime();
				return (pQueue / window + 1) * serviceTime;
			}
		}
		
		ThemeParkNode currentNode = tp.getNodeAt(nodeId);
		//Roadの場合のコストは単にst_iになる
		if (currentNode.getCapacity() == Integer.MAX_VALUE) {
			return currentNode.getServiceTime();
		} else {
			int queue = ((Attraction)currentNode).getQueueLength();
			int window = ((Attraction)currentNode).getCapacity();
			int serviceTime = ((Attraction)currentNode).getServiceTime();
			return (queue / window + 1) * serviceTime;
		}
	}
	
	@Override
	public double evalPlan(List<Integer> plan, ThemePark tp, Visitor visitor) {
		int[] ETA = new int[plan.size()];
		ETA[0] = tp.getSimTime();
		for (int i = 1; i < plan.size(); i++) {
			ETA[i] = ETA[i-1]  + costEstimate(plan.get(i-1), ETA[i-1], tp, visitor);
		}
		return -ETA[plan.size() - 1];
	}

	@Override
	public List<Integer> searchPlan(ThemePark tp, Visitor visitor) {
		if (visitor.getActStatus() == EnumStatus.TERMINATED) {
			return null;
		}
		int searchCount = 0;
		List<Integer> candidateAttOrder = new ArrayList<>(visitor.getAttractionToVisit());
		List<Integer> candidatePlan = Graph.allDijkstra(tp.getThemeParkGraph(), candidateAttOrder, visitor.getPosition(), SystemConst.GRAPH_SIZE);
		while (true) {
			List<Integer> neighborAttOrder = swapTwoPoints(candidateAttOrder);
			if(neighborAttOrder.equals(candidateAttOrder)) {
				break;
			}
			List<Integer> neighborPlan = Graph.allDijkstra(tp.getThemeParkGraph(), neighborAttOrder, visitor.getPosition(), SystemConst.GRAPH_SIZE);
			if (evalPlan(neighborPlan, tp, visitor) > evalPlan(candidatePlan, tp, visitor)) {
				candidatePlan = neighborPlan;
				candidateAttOrder = neighborAttOrder;
			} else {
				searchCount++;
				if (searchCount == SystemConst.LOCAL_SEARCH_TIMES) {
					break;
				}
			}
		}
		return candidatePlan;
	}	
}
