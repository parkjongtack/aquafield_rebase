package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface AdminTermsDao {
	
	// max uid
	public int adminTermsMaxUid();
	
	// 목록 조회
	public List<Map> adminTermsList(Map param);
	
	// 목록 조회 totCnt
	public int adminTermsListCnt(Map param);

	// 상세 조회
	public Map adminTermsDetail(Map param);

	// 등록
	public int adminTermsInsert(Map param);

	// 수정
	public int adminTermsUpdate(Map param);

	// 삭제
	public int adminTermsDelete(Map param);

}
