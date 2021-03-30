package com.soft.web.service.admin;

import java.util.List;
import java.util.Map;

public interface ReservationService {

	//예약 목록
	List<Map> reservationList(Map param);

	//예약 목록 totCnt
	int reservationListCnt(Map param);

	//예약 상세
	Map reservationDetail(Map param);

	//예약정보 바로보기 사용변경버튼
	int reservationUseChnge(Map param);

	// 예약내용 수정
	int reservationUpdate(Map param);

	//페이징 없는 목록추출
	List<Map> reservationListAll(Map param);
	
	//데스크관리자 대쉬보드 관련 상품별 사람CNT
	public List<Map> reservePersonCntOfProd(Map param);	
	
	//데스크관리자 대쉬보드 관련 예약상태별 사람CNT
	int reservePersonCnt(Map param);
	
}
