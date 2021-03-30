package com.soft.web.sms;

import java.util.List;
import java.util.Map;

public interface SmsDao {
	public int insertSms(Map param);
	public int insertLms(Map param);
	public void insSmsSend(Map param);
}
