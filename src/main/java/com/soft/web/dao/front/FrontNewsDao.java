package com.soft.web.dao.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface FrontNewsDao {
	
	// MAX UID
	public int newsMaxUid();
	
	// 목록 카운터
	public int newsCount(Map param);

	// 목록
	public List<Map> newsList(Map param);
	
	// 상세
	public Map<String,Object> newsDetail(Map param);
	
	// 이전글
	public Map<String,Object> newsPrev(Map param);
	
	// 다음글
	public Map<String,Object> newsNext(Map param);
	
	// 등록
	public int newsInsert(Map param);
	
	// 수정
	public int newsUpdate(Map param);
	
	// 조회수
	public int newsHitUpdate(Map param);
	
	// 삭제
	public int newsDelete(Map param);
	
}
