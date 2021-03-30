package com.soft.web.service.front;

import java.util.List;
import java.util.Map;

public interface FrontMemberService {

	// �쉶�썝媛��엯
	public String memberJoin(Map param);

	//�븘�씠�뵒李얘린
	public String searchId(Map param);

	//蹂몄씤�씤利앹떆 ���옣
	public String niceIdSave(Map param);

	//蹂몄씤�씤利� 泥댄겕
	public int niceChk(Map param);

	//�깉鍮꾨�踰덊샇 �꽕�젙
	public String setPassword(Map param);

	//�븘�씠�뵒 議댁옱�뿬遺� 泥댄겕
	public int idChk(Map param);

	//濡쒓렇�씤  �쉶�썝�젙蹂�
	public Map memberInfo(Map param);

	//�쑕硫� �쉶�썝�젙蹂�
	public Map inactMemInfo(Map param);

	//理쒓렐濡쒓렇�씤�젒�냽�씪 �뾽�뜲�씠�듃
	public String setLastloginDate(Map param);

	//�쑕硫댄빐�젣 �옉�뾽
	public String setInactivityUpd(Map param);

	//�쉶�썝�젙蹂� �닔�젙
	public String setMemInfo(Map param);

	//�쉶�썝�닔�젙 �젙蹂�
	public Map memberUpdInfo(Map param);

	//�쉶�썝�깉�눜
	public String memberDel(String param);

	public String memberRecovery(String param);	
	
	//�쉶�썝�젙蹂� �닔�젙�떆 �꽭�뀞 �떎�떆 RESET �븯湲�
	public Map memberInfoTwo(int param);

	//�쉶�썝鍮꾨�踰덊샇 �닔�젙
	public String setMemPwUpd(Map param);

	// �쉶�썝 濡쒓렇�씤 �떎�뙣 泥섎━
	public String loginFail(Map param);

}
