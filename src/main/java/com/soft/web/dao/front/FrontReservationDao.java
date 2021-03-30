package com.soft.web.dao.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface FrontReservationDao {
	
	//�쑕臾댁씪 �뀑�똿
	public List<Map> emptyDayList(Map param);
	
	//SEASON DAY �뀑�똿
	public List<Map> seasonDayList(Map param);
	
	//RESERVE DAY
	public List<Map> reserveDayList(Map param);	
	
	//�긽�뭹�젙蹂�
	public List<Map> prodInfolist(Map param);
	
	//�긽�뭹 cnt 泥댄겕
	public int prodInfoCnt(Map param);
	
	//�삁�빟踰덊샇 以묐났�솗�씤
	public int getReserveNumCnt(String param);
	
	//�삁�빟踰덊샇 媛��졇�삤湲�
	public String getReserveNum(String param);
	
	//�삁�빟 insert
	public void reserVationIns(Map param);
	
	//媛�寃⑹젙蹂� 媛��졇�삤湲�
	public Map getItemInfo(String param);
	
	//�삁�빟�젙蹂� 由ъ뒪�듃
	public List<Map> getReservelist(Map param);
	
	//�삁�빟 �긽�뭹 Qty �젙蹂�
	public int itemQtyInfo(int param);
	
	//�삁�빟 �긽�뭹 Qty �뾽�뜲�씠�듃
	public void itemQtyUpd(Map param);
	
	public void itemQtyUpdMinus(Map param);	
	
	//�삁�빟 �젙蹂� 媛��졇�삤湲�
	public Map getReserveInfo(int param);
	
	//�삁�빟 �젙蹂� 媛��졇�삤湲� new
	public Map getReserveInfoNew(Map param);
	
	//PG 寃곗젣 �젙蹂� INSERT
	public int pgResultIns(Map param);
	
	//PG RESULT �젙蹂� 媛��졇�삤湲�
	public Map pgResultInfo(String param);
	
	//PG 寃곗젣 痍⑥냼 INSERT
	public void pgCancelIns(Map param);
	
	//�삁�빟痍⑥냼 �뾽�뜲�씠�듃
	public void reserveCancelUpd(Map param);
	
	//�쉶�썝�깉�눜 愿��젴 �삁�빟�젙蹂� CNT 媛��졇�삤湲� 
	public int getReservelistCnt(int param);
	
	//�삁�빟�젙蹂� Detail INSERT
	public void reserveDetailIns(Map param);

	//�삁�빟�젙蹂� Detail Delete
	public String reserveDetailDel(int param);	

	//PG 寃곗젣 �썑 �삁�빟 寃곗젣 �긽�깭 Update
	public void reserVationUpd(Map param);
	
	//�늻�씫�맂 �궗�슜�옄 �벑濡앺븯湲� �쐞�븳 �궗�슜�옄 �젙蹂� 媛��졇�삤湲�
	public Map getMemInfo(String param);

	//吏��젏 �젙蹂� 媛��졇�삤湲�
	public Map getPointInfo(String param);

	//吏��젙�닔�웾 泥댄겕
	public List<Integer> getQuantityList(Map param);

	public void memberExtUpd(Map param);
}