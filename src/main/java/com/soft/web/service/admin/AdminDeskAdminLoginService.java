package com.soft.web.service.admin;

import java.util.Map;

public interface AdminDeskAdminLoginService {
	public Map deskAdminLogin(Map param);
	public int updateDeskAdminCertnum(Map param);
	public int updateDeskAdminLoginDigit(Map param);
}
