package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

public interface AdminAdminInfoDao {
	public int adminInfoGetMaxUid(Map param);
	public int adminInfoGetCount(Map param);
	public List<Map> adminInfoList(Map param);
	public Map adminInfoDetail(Map param);
	public int adminInfoInsert(Map param);
	public int adminInfoUpdate(Map param);
	public int adminInfoPasswordUpdate(Map param);
	public int adminInfoDelete(Map param);
	
	//사용기록 조회
	public List<Map> adminUseLogList(Map param);
	//사용기록 Cnt
	public int adminUseLogCnt(Map param);
	//사용기록 1depth 메뉴 리스트
	public List<Map> adminOnedepthMenuList();

	//사용자기록 엑셀
	public List<Map> adminUseLogListExel(Map param);
}
