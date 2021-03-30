package com.soft.web.dao.admin;

import java.util.Map;

public interface AdminDeskAdminLoginDao {
	public Map deskAdminLogin(Map param);
	public int updateDeskAdminCertnum(Map param);
	public int updateDeskAdminLoginDigit(Map param);
}
