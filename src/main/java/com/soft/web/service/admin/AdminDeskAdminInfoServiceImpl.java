package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.web.dao.admin.AdminDeskAdminInfoDao;
import com.soft.web.util.Util;

@Service
public class AdminDeskAdminInfoServiceImpl implements AdminDeskAdminInfoService {
	private static final Logger logger = LoggerFactory.getLogger(AdminDeskAdminInfoServiceImpl.class);

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
	
	@Autowired
	AdminDeskAdminMenuService adminDeskAdminMenuService;
	
	@Autowired
	AdminDeskAdminAuthService adminDeskAdminAuthService;


	@Override
	@Transactional
	public String operationsManagerWriteAction(List<String> listMenuCode, Map param) {
		String result = "";
		//-- 등록
		if("inst".equals(param.get("mode").toString() ) ){
			
			param.put("USE_AT","Y");

			deskAdminInfoInsert(param);
			//-- get maxUid
			String num = String.valueOf(deskAdminInfoGetMaxUid(param) );
			
			//-- 권한등록
			for(int i = 0; i < listMenuCode.size(); i++){
				if(listMenuCode.get(i).trim().length() > 0){
					Map<String,String> inputParam = new HashMap<String,String>();
					inputParam.put("ADMIN_UID",num);
					inputParam.put("MENU_CODE",listMenuCode.get(i));
					inputParam.put("INS_IP",param.get("INS_IP").toString() );
					inputParam.put("INS_ADMIN_ID",param.get("INS_ADMIN_ID").toString());
					adminDeskAdminAuthService.deskAdminAuthInsert(inputParam);
				}
			}
			result = "{\"result\":true,\"msg\":\"등록 되었습니다.\"}";
		}
		//-- 수정
		else if("updt".equals(param.get("mode").toString() ) ){
			deskAdminInfoUpdate(param);
			String num = param.get("num").toString();
			
			//-- 먼저 권한을 삭제 한다.
			Map<String,String> updateParam = new HashMap<String,String>();
			updateParam.put("num",num);
			adminDeskAdminAuthService.deskAdminAuthDeleteAll(updateParam);

			//-- 권한 등록
			for(int i = 0; i < listMenuCode.size(); i++){
				if(listMenuCode.get(i).trim().length() > 0){
					Map<String,String> inputParam = new HashMap<String,String>();
					inputParam.put("ADMIN_UID",num);
					inputParam.put("MENU_CODE",listMenuCode.get(i));
					inputParam.put("INS_IP",param.get("INS_IP").toString() );
					inputParam.put("INS_ADMIN_ID",param.get("INS_ADMIN_ID").toString());
					adminDeskAdminAuthService.deskAdminAuthInsert(inputParam);
				}
			}
			result = "{\"result\":true,\"msg\":\"수정 되었습니다.\"}";
		}
		
		return result;
	}


	@Override
	@Transactional
	public String managerDeleteAction(Map param) {
		String result = "{\"result\":true,\"msg\":\"삭제 되었습니다.\"}";
		adminDeskAdminAuthService.deskAdminAuthDeleteAll(param);
		deskAdminInfoDelete(param);
		return result;
	}
	
	@Override
	public int deskAdminInfoGetMaxUid(Map param) {
		return sqlSession.getMapper(AdminDeskAdminInfoDao.class).deskAdminInfoGetMaxUid(param);
	}

	@Override
	public int deskAdminInfoGetCount(Map param) {
		return sqlSession.getMapper(AdminDeskAdminInfoDao.class).deskAdminInfoGetCount(param);
	}

	@Override
	public List<Map> deskAdminInfoList(Map param) {
		List<Map> deskAdminInfoList = null;
		try {
			deskAdminInfoList = Util.setListKoConvert(sqlSession.getMapper(AdminDeskAdminInfoDao.class).deskAdminInfoList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminInfoList;
	}

	@Override
	public Map deskAdminInfoDetail(Map param) {
		 Map deskAdminInfoDetail = null;
		 try {
			deskAdminInfoDetail = Util.setMapKoConvert(sqlSession.getMapper(AdminDeskAdminInfoDao.class).deskAdminInfoDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminInfoDetail;
	}

	@Override
	@Transactional
	public int deskAdminInfoInsert(Map param) {
		return sqlSession.getMapper(AdminDeskAdminInfoDao.class).deskAdminInfoInsert(param);
	}

	@Override
	@Transactional
	public int deskAdminInfoUpdate(Map param) {
		return sqlSession.getMapper(AdminDeskAdminInfoDao.class).deskAdminInfoUpdate(param);
	}

	@Override
	@Transactional
	public int deskAdminInfoPasswordUpdate(Map param) {
		return sqlSession.getMapper(AdminDeskAdminInfoDao.class).deskAdminInfoPasswordUpdate(param);
	}

	@Override
	@Transactional
	public int deskAdminInfoDelete(Map param) {
		return sqlSession.getMapper(AdminDeskAdminInfoDao.class).deskAdminInfoDelete(param);
	}
}
