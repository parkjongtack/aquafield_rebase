package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

public interface AdminAdminAuthDao {
	public List<Map> adminAuthListTopMenu(Map param);
	public List<Map> adminAuthListLeftMenu(Map param);
	public int adminAuthGetIsAuthUse(Map param);
	public int adminAuthGetMaxUid(Map param);
	public int adminAuthGetCount(Map param);
	public List<Map> adminAuthList(Map param);
	public Map adminAuthDetail(Map param);
	public int adminAuthInsert(Map param);
	public int adminAuthUpdate(Map param);
	public int adminAuthDelete(Map param);
	public int adminAuthDeleteAll(Map param);
}