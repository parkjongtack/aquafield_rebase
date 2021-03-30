package com.soft.web.service.admin;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface AdminDeskAdminAuthService {
	public List<Map> deskAdminAuthListTopMenu(Map param);
	public List<Map> deskAdminAuthListLeftMenu(Map param);
	public int deskAdminAuthGetIsAuthUse(String loginAdminUid,String sMenuCode, HttpServletResponse response);
	public int deskAdminAuthGetMaxUid(Map param);
	public int deskAdminAuthGetCount(Map param);
	public List<Map> deskAdminAuthList(Map param);
	public Map deskAdminAuthDetail(Map param);
	public int deskAdminAuthInsert(Map param);
	public int deskAdminAuthUpdate(Map param);
	public int deskAdminAuthDelete(Map param);
	public int deskAdminAuthDeleteAll(Map param);
}
