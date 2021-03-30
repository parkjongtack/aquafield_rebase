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

import com.soft.web.dao.admin.AdminDeskAdminLoginDao;
import com.soft.web.util.Util;

@Service
public class AdminDeskAdminLoginServiceImpl implements AdminDeskAdminLoginService {
	private static final Logger logger = LoggerFactory.getLogger(AdminDeskAdminLoginServiceImpl.class);

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public Map deskAdminLogin(Map param) {
		Map adminLogin =null;
		
		try {
			adminLogin = Util.setMapKoConvert(sqlSession.getMapper(AdminDeskAdminLoginDao.class).deskAdminLogin(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return adminLogin;
	}

	@Override
	@Transactional
	public int updateDeskAdminCertnum(Map param) {
		return sqlSession.getMapper(AdminDeskAdminLoginDao.class).updateDeskAdminCertnum(param);
	}

	@Override
	@Transactional
	public int updateDeskAdminLoginDigit(Map param) {
		return sqlSession.getMapper(AdminDeskAdminLoginDao.class).updateDeskAdminLoginDigit(param);
	}
}
