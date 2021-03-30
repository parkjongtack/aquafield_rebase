package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

public interface AdminDeskAdminAuthDao {
	public List<Map> deskAdminAuthListTopMenu(Map param);
	public List<Map> deskAdminAuthListLeftMenu(Map param);
	public int deskAdminAuthGetIsAuthUse(Map param);
	public int deskAdminAuthGetMaxUid(Map param);
	public int deskAdminAuthGetCount(Map param);
	public List<Map> deskAdminAuthList(Map param);
	public Map deskAdminAuthDetail(Map param);
	public int deskAdminAuthInsert(Map param);
	public int deskAdminAuthUpdate(Map param);
	public int deskAdminAuthDelete(Map param);
	public int deskAdminAuthDeleteAll(Map param);
}
