package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDao {

	// 고객문의 totCnt
	int customerListCnt(Map param);

	// 고객문의 목록
	List<Map> customerList(Map param);
	
	// 고객문의 상세
	Map customerDetail(Map param);

	// 고객문의 답변등록
	void customerUpdate(Map param);

}
