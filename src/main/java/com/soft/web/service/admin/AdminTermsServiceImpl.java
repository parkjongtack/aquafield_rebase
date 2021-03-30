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
import com.soft.web.dao.admin.AdminTermsDao;
import com.soft.web.util.Util;

@Service
public class AdminTermsServiceImpl implements AdminTermsService {

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public int adminTermsMaxUid() {
		return sqlSession.getMapper(AdminTermsDao.class).adminTermsMaxUid();
	}

	@Override
	public List<Map> adminTermsList(Map param) {
		List<Map> list = null;
		try {
			list = Util.setListKoConvert(sqlSession.getMapper(AdminTermsDao.class).adminTermsList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int adminTermsListCnt(Map param) {
		return sqlSession.getMapper(AdminTermsDao.class).adminTermsListCnt(param);
	}

	@Override
	public Map adminTermsDetail(Map param) {
		 Map map = null;
		 try {
			map = Util.setMapKoConvert(sqlSession.getMapper(AdminTermsDao.class).adminTermsDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return map;
	}

	@Override
	@Transactional
	public int adminTermsInsert(Map param) {
		return sqlSession.getMapper(AdminTermsDao.class).adminTermsInsert(param);
	}

	@Override
	@Transactional
	public int adminTermsUpdate(Map param) {
		return sqlSession.getMapper(AdminTermsDao.class).adminTermsUpdate(param);
	}

	@Override
	@Transactional
	public int adminTermsDelete(Map param) {
		return sqlSession.getMapper(AdminTermsDao.class).adminTermsDelete(param);
	}

}
