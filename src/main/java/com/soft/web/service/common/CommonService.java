package com.soft.web.service.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommonService {

	public List<Map> catelist(Map param);
	
	public List<Map> commonlist(Map param);
	
	//FAQ ����Ʈ
	public List<Map> faqlist(Map param);
	
	//���� �̻�� ������Ʈ
	public String batchNoUseUpd();
	
	//���� �ڵ���� ����Ʈ
	public List<Map> batchCancelList();
	
	//1���� SMS �˸�
	public List<Map> beforeOneDaySmslist();
	
	//SMS �߼� �̷� ���
	public String insSmsSend(Map param);
	
	//SMS ���ø� ��������
	public Map getSmsTemplete(Map param);
	
	//SMS 1�� �� �߼��ϱ�
	public int sendSmsCnt(Map param); 
	
	//Email �߼� �̷� ���
	public String insEmailSend(Map param);
	
	//�޸���� 7���� mail�߼�
	public List<Map> beforeSevenDayMailList();
	
	//������ ���� ���� �߼�
	public List<Map> marketAgreeMail();	
	
	//�޸���� INSERT
	public String inactMemIns(Map param);
	
	//�޸鰳�� UPD
	public String inactMemUpd();
	
	//ADMIN ����� INSERT
	public String insAdminContentLog(Map param);
	
	//������ �޴� ��������
	public Map getAdminMenu(String param); 	
	
	//���� ���� ��������
	public Map getPointInfo(String param);

	//���� ���� ��������
	public List<Map> payList();
	
}
