package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.soft.web.dao.admin.AdminSmsTempletDao;
import com.soft.web.dao.admin.AdminStatisticsDao;
import com.soft.web.util.Util;

@Service
public class AdminSaleStatisticsServiceImpl implements 	AdminSaleStatisticsService {

    @Resource(name="config")
    private Properties config;
	
	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;
	
	//매출(상품별)통계
	@Override
	public List<Map> getSaleStatistics(Map param){
		List<Map> list = null;
		try {
			list = Util.setListKoConvert(sqlSession.getMapper(AdminStatisticsDao.class).getSaleStatistics(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return list;
	}	
	

}
