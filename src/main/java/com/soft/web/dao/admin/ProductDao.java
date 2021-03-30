package com.soft.web.dao.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ProductDao {
	
	public List<Map> prodlist(Map param);
	
	public List<Map> itemlist(Map param);
	
	public void itemIns(Map param);
	
	public List<Map> itemModlist(Map param);
	
	public void itemUpd(Map param);
	
	//상품 존재여부 체크값
	public int itemsCnt(Map param);
	
	//상품 GroupBy 리스트 (패키지,렌탈, 이벤트)
	public List<Map> itemGroupBylist(Map param);

	//패키지 상품옵션 중복 여부 체크(같은 일자 중복 불가)
	public int itemsCheck(Map param);
}
