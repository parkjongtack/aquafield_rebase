package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
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

import com.soft.web.dao.admin.AdminDeskAdminMenuDao;
import com.soft.web.util.Util;

@Service
public class AdminDeskAdminMenuServiceImpl implements AdminDeskAdminMenuService {
	private static final Logger logger = LoggerFactory.getLogger(AdminDeskAdminMenuServiceImpl.class);

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public int deskAdminMenuGetMaxUid(Map param) {
		return sqlSession.getMapper(AdminDeskAdminMenuDao.class).deskAdminMenuGetMaxUid(param);
	}

	@Override
	public int deskAdminMenuGuetCount(Map param) {
		return sqlSession.getMapper(AdminDeskAdminMenuDao.class).deskAdminMenuGuetCount(param);
	}

	@Override
	public List<Map> deskAdminMenuList(Map param) {
		List<Map> deskAdminMenuList = null;
		try {
			deskAdminMenuList = Util.setListKoConvert(sqlSession.getMapper(AdminDeskAdminMenuDao.class).deskAdminMenuList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminMenuList;
	}

	@Override
	public List<Map> deskAdminMenuListAll(Map param) {
		List<Map> deskAdminMenuListAll = null;
		try {
			deskAdminMenuListAll = Util.setListKoConvert(sqlSession.getMapper(AdminDeskAdminMenuDao.class).deskAdminMenuListAll(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminMenuListAll;
	}

	@Override
	public Map deskAdminMenuDetail(Map param) {
		Map deskAdminMenuDetail = null;
		try {
			deskAdminMenuDetail = Util.setMapKoConvert(sqlSession.getMapper(AdminDeskAdminMenuDao.class).deskAdminMenuDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deskAdminMenuDetail;
	}

	@Override
	@Transactional
	public int deskAdminMenuInsert(Map param) {
		try {
			param.put("MENU_NM", Util.convertStringToHex(param.get("MENU_NM").toString()));
			param.put("MENU_INFO", Util.convertStringToHex(param.get("MENU_INFO").toString()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlSession.getMapper(AdminDeskAdminMenuDao.class).deskAdminMenuInsert(param);
	}

	@Override
	@Transactional
	public int deskAdminMenuUpdate(Map param) {
		try {
			param.put("MENU_NM", Util.convertStringToHex(param.get("MENU_NM").toString()));
			param.put("MENU_INFO", Util.convertStringToHex(param.get("MENU_INFO").toString()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlSession.getMapper(AdminDeskAdminMenuDao.class).deskAdminMenuUpdate(param);
	}

	@Override
	@Transactional
	public int deskAdminMenuDelete(Map param) {
		return sqlSession.getMapper(AdminDeskAdminMenuDao.class).deskAdminMenuDelete(param);
	}
}
