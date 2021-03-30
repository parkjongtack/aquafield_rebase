package com.soft.web.service.admin;

import java.util.Map;

public interface AdminLoginService {
	public Map adminLogin(Map param);
	public int updateAdminCertnum(Map param);
	public int updateAdminLoginDigit(Map param);
}
