package com.soft.web.service.admin;

import java.util.List;
import java.util.Map;

public interface MemberService {

	// �쉶�썝紐⑸줉 議고쉶
	public List<Map> memberList(Map param);

	public List<Map> memberList2(Map param);
	
	public int memberListCnt2(Map param);	
	
	// �쉶�썝紐⑸줉 議고쉶 totCnt
	public int memberListCnt(Map param);

	// �쉶�썝�긽�꽭 議고쉶
	public Map memberDetail(Map param);

	// �쉶�썝�긽�꽭 �닔�젙
	public void memberUpdate(Map param);
	
	//�쑕硫닿컻�젙 議고쉶
	public List<Map> inactMemberList(Map param);
	
	// �쉶�썝紐⑸줉 議고쉶 totCnt
	public int inactMemberListCnt(Map param);	
	

}
