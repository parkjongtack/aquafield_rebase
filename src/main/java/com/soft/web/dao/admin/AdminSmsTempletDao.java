package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminSmsTempletDao {
	
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
