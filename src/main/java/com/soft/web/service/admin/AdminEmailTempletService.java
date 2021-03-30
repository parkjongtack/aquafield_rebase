package com.soft.web.service.admin;

import java.util.List;
import java.util.Map;

public interface AdminEmailTempletService {
	
	// max uid
	public int adminEmailTempletMaxUid();
	
	// 목록 조회
	public List<Map> adminEmailTempletList(Map param);
	
	// 목록 조회 totCnt
	public int adminEmailTempletListCnt(Map param);

	// 상세 조회
	public Map adminEmailTempletDetail(Map param);

	// 등록
	public int adminEmailTempletInsert(Map param);

	// 수정
	public int adminEmailTempletUpdate(Map param);

	// 삭제
	public int adminEmailTempletDelete(Map param);
}
