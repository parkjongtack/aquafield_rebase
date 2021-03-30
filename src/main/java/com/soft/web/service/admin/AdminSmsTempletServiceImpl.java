package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.web.dao.admin.AdminSmsTempletDao;
import com.soft.web.util.Util;

@Service
public class AdminSmsTempletServiceImpl implements AdminSmsTempletService {

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public List<Map> adminSmsTempletList(Map param) {
		List<Map> list = null;
		try {
			list = Util.setListKoConvert(sqlSession.getMapper(AdminSmsTempletDao.class).adminSmsTempletList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int adminSmsTempletListCnt(Map param) {
		return sqlSession.getMapper(AdminSmsTempletDao.class).adminSmsTempletListCnt(param);
	}

	@Override
	public Map adminSmsTempletDetail(Map param) {
		 Map map = null;
		 try {
			map = Util.setMapKoConvert(sqlSession.getMapper(AdminSmsTempletDao.class).adminSmsTempletDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	@Transactional
	public int adminSmsTempletInsert(Map param) {
		return sqlSession.getMapper(AdminSmsTempletDao.class).adminSmsTempletInsert(param);
	}

	@Override
	@Transactional
	public int adminSmsTempletUpdate(Map param) {
		return sqlSession.getMapper(AdminSmsTempletDao.class).adminSmsTempletUpdate(param);
	}

	@Override
	@Transactional
	public int adminSmsTempletDelete(Map param) {
		return sqlSession.getMapper(AdminSmsTempletDao.class).adminSmsTempletDelete(param);
	}

}
