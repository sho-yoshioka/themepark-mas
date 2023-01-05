package environment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import setting.SystemConst;

public class Main {
	public static void main(String[] args) {
		//シミュレーション開始処理
		//テーマパーク生成
		ThemePark tp = null;
		//ユーザ生成
		//ノード生成
		//アトラクション稼働
		//ユーザプランニング
		//ユーザ行動
		int one=0;
		int two=0;
		int three=0;
	
		//test　
		//同じリストをshuffleし続けるのも同じ
		//List<Integer> attList = new ArrayList<>();
		//for (int k = 0; k < 3; k++) {
		//	attList.add(k + 1);
		//}
		
		for(int i = 0; i< 10000000; i++) {
			List<Integer> attList = new ArrayList<>();
			for (int k = 0; k < 3; k++) {
				attList.add(k + 1);
			}
			Collections.shuffle(attList, SystemConst.DECIDE_ATT_RND);
			if(attList.get(0)==1)one++;
			if(attList.get(0)==2)two++;
			if(attList.get(0)==3)three++;
		}
		System.out.println(one +" "+ two +" "+ three);
	}

}
