package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.soft.web.dao.admin.AdminLoginDao;
import com.soft.web.util.Util;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
	private static final Logger logger = LoggerFactory.getLogger(AdminLoginServiceImpl.class);

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public Map adminLogin(Map param) {
		Map adminLogin =null;
		
		try {
			adminLogin = Util.setMapKoConvert(sqlSession.getMapper(AdminLoginDao.class).adminLogin(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return adminLogin;
	}

	@Override
	@Transactional
	public int updateAdminCertnum(Map param) {
		return sqlSession.getMapper(AdminLoginDao.class).updateAdminCertnum(param);
	}

	@Override
	@Transactional
	public int updateAdminLoginDigit(Map param) {
		return sqlSession.getMapper(AdminLoginDao.class).updateAdminLoginDigit(param);
	}
}
