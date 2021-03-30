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

import com.soft.web.dao.admin.AdminAdminMenuDao;
import com.soft.web.util.Util;

@Service
public class AdminAdminMenuServiceImpl implements AdminAdminMenuService {
	private static final Logger logger = LoggerFactory.getLogger(AdminAdminMenuServiceImpl.class);

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public int adminMenuGetMaxUid(Map param) {
		return sqlSession.getMapper(AdminAdminMenuDao.class).adminMenuGetMaxUid(param);
	}

	@Override
	public int adminMenGuetCount(Map param) {
		return sqlSession.getMapper(AdminAdminMenuDao.class).adminMenGuetCount(param);
	}

	@Override
	public List<Map> adminMenuList(Map param) {
		List<Map> adminMenuList = null;
		try {
			adminMenuList = Util.setListKoConvert(sqlSession.getMapper(AdminAdminMenuDao.class).adminMenuList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminMenuList;
	}

	@Override
	public List<Map> adminMenuListAll(Map param) {
		List<Map> adminMenuListAll = null;
		try {
			adminMenuListAll = Util.setListKoConvert(sqlSession.getMapper(AdminAdminMenuDao.class).adminMenuListAll(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminMenuListAll;
	}

	@Override
	public Map adminMenuDetail(Map param) {
		Map adminMenuDetail = null;
		try {
			adminMenuDetail = Util.setMapKoConvert(sqlSession.getMapper(AdminAdminMenuDao.class).adminMenuDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adminMenuDetail;
	}

	@Override
	@Transactional
	public int adminMenuInsert(Map param) {
		try {
			param.put("MENU_NM", Util.convertStringToHex(param.get("MENU_NM").toString()));
			param.put("MENU_INFO", Util.convertStringToHex(param.get("MENU_INFO").toString()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlSession.getMapper(AdminAdminMenuDao.class).adminMenuInsert(param);
	}

	@Override
	@Transactional
	public int adminMenuUpdate(Map param) {
		try {
			param.put("MENU_NM", Util.convertStringToHex(param.get("MENU_NM").toString()));
			param.put("MENU_INFO", Util.convertStringToHex(param.get("MENU_INFO").toString()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sqlSession.getMapper(AdminAdminMenuDao.class).adminMenuUpdate(param);
	}

	@Override
	@Transactional
	public int adminMenuDelete(Map param) {
		return sqlSession.getMapper(AdminAdminMenuDao.class).adminMenuDelete(param);
	}
}
