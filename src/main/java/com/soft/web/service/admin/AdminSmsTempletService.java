package com.soft.web.service.admin;

import java.util.List;
import java.util.Map;

public interface AdminSmsTempletService {
	
	// 목록 조회
	public List<Map> adminSmsTempletList(Map param);
	
	// 목록 조회 totCnt
	public int adminSmsTempletListCnt(Map param);

	// 상세 조회
	public Map adminSmsTempletDetail(Map param);

	// 등록
	public int adminSmsTempletInsert(Map param);

	// 수정
	public int adminSmsTempletUpdate(Map param);

	// 삭제
	public int adminSmsTempletDelete(Map param);
}
