package com.soft.web.dao.front;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface StatisticsVisitorDao {
	public int getCountVisitor(Map param);
	public int getCountDevice(Map param);
	public int getCountBrowser(Map param);
	public int getCountOs(Map param);
	public int getCountReferer(Map param);
	public int getTotalCountReferer(Map param);
	public int getCountScreen(Map param);
	
	public Map listVisitorTime(Map param);
	
	public List listVisitorDay(Map param);
	public List listVisitorMonth(Map param);
	public List listVisitorDevice(Map param);
	public List listVisitorBrowser(Map param);
	public List listVisitorOs(Map param);
	public List listVisitorReferer(Map param);
	public List listVisitorScreen(Map param);

	public int insertVisitor(Map param);
	public int insertDevice(Map param);
	public int insertBrowser(Map param);
	public int insertOs(Map param);
	public int insertReferer(Map param);
	public int insertScreen(Map param);
	
	public int updateVisitor(Map param);
	public int updateDevice(Map param);
	public int updateBrowser(Map param);
	public int updateOs(Map param);
	public int updateReferer(Map param);
	public int updateScreen(Map param);
	public int updatePage(Map param);
}
