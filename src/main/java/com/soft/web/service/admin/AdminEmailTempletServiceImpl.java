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

import com.soft.web.dao.admin.AdminEmailTempletDao;
import com.soft.web.util.Util;

@Service
public class AdminEmailTempletServiceImpl implements AdminEmailTempletService {

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public int adminEmailTempletMaxUid() {
		return sqlSession.getMapper(AdminEmailTempletDao.class).adminEmailTempletMaxUid();
	}

	@Override
	public List<Map> adminEmailTempletList(Map param) {
		List<Map> list = null;
		try {
			list = Util.setListKoConvert(sqlSession.getMapper(AdminEmailTempletDao.class).adminEmailTempletList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int adminEmailTempletListCnt(Map param) {
		return sqlSession.getMapper(AdminEmailTempletDao.class).adminEmailTempletListCnt(param);
	}

	@Override
	public Map adminEmailTempletDetail(Map param) {
		 Map map = null;
		 try {
			map = Util.setMapKoConvert(sqlSession.getMapper(AdminEmailTempletDao.class).adminEmailTempletDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	@Transactional
	public int adminEmailTempletInsert(Map param) {
		return sqlSession.getMapper(AdminEmailTempletDao.class).adminEmailTempletInsert(param);
	}

	@Override
	@Transactional
	public int adminEmailTempletUpdate(Map param) {
		return sqlSession.getMapper(AdminEmailTempletDao.class).adminEmailTempletUpdate(param);
	}

	@Override
	@Transactional
	public int adminEmailTempletDelete(Map param) {
		return sqlSession.getMapper(AdminEmailTempletDao.class).adminEmailTempletDelete(param);
	}

}
