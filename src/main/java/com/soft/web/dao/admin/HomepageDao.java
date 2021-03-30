package com.soft.web.dao.admin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface HomepageDao {
	
	//팝업 Cnt 
	public int popupListCnt(Map param);

	//팝업 List 
	public List popupList(Map param);
	
	public String popInst(Map param);
	
}
