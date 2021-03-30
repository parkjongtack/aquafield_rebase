package com.soft.web.service.front;

import java.util.List;
import java.util.Map;

public interface FrontOneToOneService {
	
	//1:1문의 리스트
	public List<Map> onetoOnelist (Map param);
	
	//1:1문의 개수
	public int onetoOnelistCnt (Map param);
	
	//1:1문의 상세보기
	public Map onetoOneInfo(String param);
	
	//1:1문의 상세보기
	public Map onetoOneInfoNew(Map param);
	
	//1:1문의 등록
	public String oneToOneIns(Map param);	

}
