package com.soft.web.dao.admin;

import java.util.Map;

public interface AdminLoginDao {
	public Map adminLogin(Map param);
	public int updateAdminCertnum(Map param);
	public int updateAdminLoginDigit(Map param);
}
