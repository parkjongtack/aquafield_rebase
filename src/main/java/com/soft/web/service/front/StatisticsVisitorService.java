package com.soft.web.service.front;

import java.util.List;
import java.util.Map;

public interface StatisticsVisitorService {
	public boolean visitorDataInsert(Map<?,?> param) throws Throwable;
	
	public int getCountVisitor(Map param) throws Exception;
	public int getCountDevice(Map param) throws Exception;
	public int getCountBrowser(Map param) throws Exception;
	public int getCountOs(Map param) throws Exception;
	public int getCountReferer(Map param) throws Exception;
	public int getTotalCountReferer(Map param) throws Exception;
	public int getCountScreen(Map param) throws Exception;
	
	public Map listVisitorTime(Map param) throws Exception;
	
	public List listVisitorDay(Map param) throws Exception;
	public List listVisitorMonth(Map param) throws Exception;
	public List listVisitorDevice(Map param) throws Exception;
	public List listVisitorBrowser(Map param) throws Exception;
	public List listVisitorOs(Map param) throws Exception;
	public List listVisitorReferer(Map param) throws Exception;
	public List listVisitorScreen(Map param) throws Exception;

	public int insertVisitor(Map param) throws Exception;
	public int insertDevice(Map param) throws Exception;
	public int insertBrowser(Map param) throws Exception;
	public int insertOs(Map param) throws Exception;
	public int insertReferer(Map param) throws Exception;
	public int insertScreen(Map param) throws Exception;
	
	public int updateVisitor(Map param) throws Exception;
	public int updateDevice(Map param) throws Exception;
	public int updateBrowser(Map param) throws Exception;
	public int updateOs(Map param) throws Exception;
	public int updateReferer(Map param) throws Exception;
	public int updateScreen(Map param) throws Exception;
	public int updatePage(Map param) throws Exception;

}
