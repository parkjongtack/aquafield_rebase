package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

public interface AdminAdminMenuDao {
	public int adminMenuGetMaxUid(Map param);
	public int adminMenGuetCount(Map param);
	public List<Map> adminMenuList(Map param);
	public List<Map> adminMenuListAll(Map param);
	public Map adminMenuDetail(Map param);
	public int adminMenuInsert(Map param);
	public int adminMenuUpdate(Map param);
	public int adminMenuDelete(Map param);
}
