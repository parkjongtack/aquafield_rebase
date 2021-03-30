package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.web.dao.admin.AdminAdminAuthDao;
import com.soft.web.util.Util;

@Service
public class AdminAdminAuthServiceImpl implements AdminAdminAuthService {
	private static final Logger logger = LoggerFactory.getLogger(AdminAdminAuthServiceImpl.class);

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public List<Map> adminAuthListTopMenu(Map param) {
		List<Map> adminAuthListTopMenu = null;
		try {
			adminAuthListTopMenu = Util.setListKoConvert(sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthListTopMenu(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminAuthListTopMenu;
	}

	@Override
	public List<Map> adminAuthListLeftMenu(Map param) {
		List<Map> adminAuthListLeftMenu = null;
		try {
			adminAuthListLeftMenu = Util.setListKoConvert(sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthListLeftMenu(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminAuthListLeftMenu;
	}

	@Override
	public int adminAuthGetIsAuthUse(String loginAdminUid,String sMenuCode, HttpServletResponse response) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("adminNum", loginAdminUid);
		param.put("sMenuCode", sMenuCode);	
		
		int ct = sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthGetIsAuthUse(param);
		
		if(ct == 0){
			try{
				StringBuffer sb = new StringBuffer();
				sb.append("<script>\n");
				sb.append("alert('권한이 없습니다.');\n");
				sb.append("top.location.href='/secu_admaf/admin/logout.af';\n");
				sb.append("</script>\n");
				Util.htmlPrint(sb.toString(), response);
			}
			catch(Exception e){}
		}
		
		return ct;
	}

	@Override
	public int adminAuthGetMaxUid(Map param) {
		return sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthGetMaxUid(param);
	}

	@Override
	public int adminAuthGetCount(Map param) {
		return sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthGetCount(param);
	}

	@Override
	public List<Map> adminAuthList(Map param) {
		List<Map> adminAuthList = null;
		try {
			adminAuthList = Util.setListKoConvert(sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminAuthList;
	}

	@Override
	public Map adminAuthDetail(Map param) {
		Map adminAuthDetail = null;
		try {
			adminAuthDetail = Util.setMapKoConvert(sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminAuthDetail;
	}

	@Override
	@Transactional
	public int adminAuthInsert(Map param) {
		return sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthInsert(param);
	}

	@Override
	@Transactional
	public int adminAuthUpdate(Map param) {
		return sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthUpdate(param);
	}

	@Override
	@Transactional
	public int adminAuthDelete(Map param) {
		return sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthDelete(param);
	}

	@Override
	@Transactional
	public int adminAuthDeleteAll(Map param) {
		return sqlSession.getMapper(AdminAdminAuthDao.class).adminAuthDeleteAll(param);
	}
	
}
