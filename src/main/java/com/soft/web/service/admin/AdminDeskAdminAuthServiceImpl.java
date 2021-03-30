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

import com.soft.web.dao.admin.AdminDeskAdminAuthDao;
import com.soft.web.util.Util;

@Service
public class AdminDeskAdminAuthServiceImpl implements AdminDeskAdminAuthService {
	private static final Logger logger = LoggerFactory.getLogger(AdminDeskAdminAuthServiceImpl.class);

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public List<Map> deskAdminAuthListTopMenu(Map param) {
		List<Map> deskAdminAuthListTopMenu = null;
		try {
			deskAdminAuthListTopMenu = Util.setListKoConvert(sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthListTopMenu(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminAuthListTopMenu;
	}

	@Override
	public List<Map> deskAdminAuthListLeftMenu(Map param) {
		List<Map> deskAdminAuthListLeftMenu = null;
		try {
			deskAdminAuthListLeftMenu = Util.setListKoConvert(sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthListLeftMenu(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminAuthListLeftMenu;
	}

	@Override
	public int deskAdminAuthGetIsAuthUse(String loginAdminUid,String sMenuCode, HttpServletResponse response) {
		Map<String,String> param = new HashMap<String,String>();
		param.put("adminNum", loginAdminUid);
		param.put("sMenuCode", sMenuCode);	
		
		int ct = sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthGetIsAuthUse(param);
		
		if(ct == 0){
			try{
				StringBuffer sb = new StringBuffer();
				sb.append("<script>\n");
				sb.append("alert('권한이 없습니다.');\n");
				sb.append("top.location.href='/secu_admaf/admdesk/logout.af';\n");
				sb.append("</script>\n");
				Util.htmlPrint(sb.toString(), response);
			}
			catch(Exception e){}
		}
		
		return ct;
	}

	@Override
	public int deskAdminAuthGetMaxUid(Map param) {
		return sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthGetMaxUid(param);
	}

	@Override
	public int deskAdminAuthGetCount(Map param) {
		return sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthGetCount(param);
	}

	@Override
	public List<Map> deskAdminAuthList(Map param) {
		List<Map> deskAdminAuthList = null;
		try {
			deskAdminAuthList = Util.setListKoConvert(sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminAuthList;
	}

	@Override
	public Map deskAdminAuthDetail(Map param) {
		Map deskAdminAuthDetail = null;
		try {
			deskAdminAuthDetail = Util.setMapKoConvert(sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminAuthDetail;
	}

	@Override
	@Transactional
	public int deskAdminAuthInsert(Map param) {
		return sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthInsert(param);
	}

	@Override
	@Transactional
	public int deskAdminAuthUpdate(Map param) {
		return sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthUpdate(param);
	}

	@Override
	@Transactional
	public int deskAdminAuthDelete(Map param) {
		return sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthDelete(param);
	}

	@Override
	@Transactional
	public int deskAdminAuthDeleteAll(Map param) {
		return sqlSession.getMapper(AdminDeskAdminAuthDao.class).deskAdminAuthDeleteAll(param);
	}
	
}
