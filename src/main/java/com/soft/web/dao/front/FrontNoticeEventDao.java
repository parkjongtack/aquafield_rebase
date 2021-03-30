package com.soft.web.dao.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface FrontNoticeEventDao {
	
	// MAX UID
	public int noticeEventMaxUid();
	
	// 목록 카운터
	public int noticeEventCount(Map param);

	// 목록
	public List<Map> noticeEventList(Map param);
	
	// 상세
	public Map<String,Object> noticeEventDetail(Map param);
	
	// 이전글
	public Map<String,Object> noticeEventPrev(Map param);
	
	// 다음글
	public Map<String,Object> noticeEventNext(Map param);
	
	// 등록
	public int insertNoticeEvent(Map param);
	
	// 수정
	public int updateNoticeEvent(Map param);
	
	// 조회수
	public int updateNoticeEventHit(Map param);
	
	// 삭제
	public int deleteNoticeEvent(Map param);
	
}
