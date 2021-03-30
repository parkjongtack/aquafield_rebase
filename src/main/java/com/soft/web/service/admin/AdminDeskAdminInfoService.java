package com.soft.web.service.admin;

import java.util.List;
import java.util.Map;

public interface AdminDeskAdminInfoService {
	public String operationsManagerWriteAction(List<String> listMenuCode, Map param);
	public String managerDeleteAction(Map param);
	
	public int deskAdminInfoGetMaxUid(Map param);
	public int deskAdminInfoGetCount(Map param);
	public List<Map> deskAdminInfoList(Map param);
	public Map deskAdminInfoDetail(Map param);
	public int deskAdminInfoInsert(Map param);
	public int deskAdminInfoUpdate(Map param);
	public int deskAdminInfoPasswordUpdate(Map param);
	public int deskAdminInfoDelete(Map param);
}
