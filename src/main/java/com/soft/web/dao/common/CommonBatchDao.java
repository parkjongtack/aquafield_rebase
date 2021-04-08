package com.soft.web.dao.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CommonBatchDao {

	//���� �̻�� ������Ʈ
	public void batchNoUseUpd();
	
	//1���� SMS �˸�
	public List<Map> beforeOneDaySmslist();
	
	//�̻�� 10% ����� �ڵ���� ���ฮ��Ʈ ��������
	public List<Map> batchCancelList();
	
	//�޸���� 7���� mail�߼�
	public List<Map> beforeSevenDayMailList();
	
	//������ ���� ���� �߼�
	public List<Map> marketAgreeMail();	
	
	//�޸���� INSERT
	public void inactMemIns(Map param);
	
	//�޸鰳�� UPD
	public void inactMemUpd();
}
