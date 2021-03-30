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

import com.soft.web.dao.admin.CustomerDao;
import com.soft.web.util.Util;

@Service
public class CustomerServiceImpl implements CustomerService {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
	
	// 고객문의 totCnt
	@Override
	public int customerListCnt(Map param) {
//		try {
//			param.put("srch_text",Util.convertStringToHex((String)param.get("srch_text")));
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return sqlSession.getMapper(CustomerDao.class).customerListCnt(param);
	}

	// 고객문의 목록
	@Override
	public List<Map> customerList(Map param) {
		List<Map> customerList = null;		
		try {
			customerList = Util.setListKoConvert(sqlSession.getMapper(CustomerDao.class).customerList(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerList;
	}

	// 고객문의 상세
	@Override
	public Map customerDetail(Map param) {
		Map customerDetail = null;
		
		try {
			customerDetail = Util.setMapKoConvert(sqlSession.getMapper(CustomerDao.class).customerDetail(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return customerDetail;
	}
	
	// 고객문의 답변등록
	@Override
	@Transactional
	public void customerUpdate(Map param) {
		try {
			param.put("ask_content2", Util.convertStringToHex((String)param.get("ask_content2")));
			sqlSession.getMapper(CustomerDao.class).customerUpdate(param);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
