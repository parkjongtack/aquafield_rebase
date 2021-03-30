package com.soft.web.sms;

import java.util.Map;

public interface SmsService {
	public boolean sendSms(Map param) throws Exception;
	public int insertSms(Map param);
	public int insertLms(Map param);
}
