package com.soft.web.dao.admin;

import java.util.List;
import java.util.Map;

public interface AdminDeskAdminMenuDao {
	public int deskAdminMenuGetMaxUid(Map param);
	public int deskAdminMenuGuetCount(Map param);
	public List<Map> deskAdminMenuList(Map param);
	public List<Map> deskAdminMenuListAll(Map param);
	public Map deskAdminMenuDetail(Map param);
	public int deskAdminMenuInsert(Map param);
	public int deskAdminMenuUpdate(Map param);
	public int deskAdminMenuDelete(Map param);
}