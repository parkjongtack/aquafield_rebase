package com.soft.web.service.admin;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

import com.soft.web.dao.admin.ProductDao;
import com.soft.web.util.Util;

@Service
public class ProductServiceImpl implements ProductService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());	
	
    @Resource(name="config")
    private Properties config;

	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	public List<Map> prodlist(Map param) {
		List<Map> prodlist = null;
		
		try {
			prodlist =  Util.setListKoConvert(sqlSession.getMapper(ProductDao.class).prodlist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prodlist;
	}
	
	@Override	
	public List<Map> itemlist(Map param){
		List<Map> itemlist = null;
		try {
			itemlist =  Util.setListKoConvert(sqlSession.getMapper(ProductDao.class).itemlist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemlist;
	}
	
	@Override
	@Transactional
	public String itemSetting(List param){

		String result ="";
		List<Map> parameters = new ArrayList<Map>(param);
		
		try {			
			for (int i = 0; i < parameters.size(); i++) {
				itemIns(parameters.get(i));
			}					
			result = "NOERROR"; 
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR"; 
			e.printStackTrace();
		}		
		return result;	
	}
	
	@Override
	public void itemIns(Map param){
		
		try {
			param.put("item_nm",Util.convertStringToHex((String)param.get("item_nm")));
			sqlSession.getMapper(ProductDao.class).itemIns(param);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Map> itemModlist(Map param){
		List<Map> itemModlist = null;
		try {
			itemModlist =  Util.setListKoConvert(sqlSession.getMapper(ProductDao.class).itemModlist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemModlist;
	}
	
	@Override
	@Transactional
	public String itemUpdating(List param){

		String result ="";
		List<Map> parameters = new ArrayList<Map>(param);
		
		try {			
			for (int i = 0; i < parameters.size(); i++) {
				itemUpd(parameters.get(i));
			}					
			result = "NOERROR"; 
		} catch (Exception e) {
			// TODO: handle exception
			result = "ERROR"; 
		}		
		return result;	
	}
	
	@Override
	public void itemUpd(Map param){
		try {
			param.put("item_nm",Util.convertStringToHex((String)param.get("item_nm")));
			sqlSession.getMapper(ProductDao.class).itemUpd(param);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	//�긽�뭹 議댁옱�뿬遺� 泥댄겕媛�
	public int itemsCnt(Map param){
		return sqlSession.getMapper(ProductDao.class).itemsCnt(param);
	}
	
	@Override
	//�긽�뭹 GroupBy 由ъ뒪�듃 (�뙣�궎吏�,�젋�깉, �씠踰ㅽ듃)
	public List<Map> itemGroupBylist(Map param){
		List<Map> itemGroupBylist = null;
		try {
			itemGroupBylist =  Util.setListKoConvert(sqlSession.getMapper(ProductDao.class).itemGroupBylist(param),config.getProperty("character.set"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return itemGroupBylist;		
	}
	
	@Override
	//�뙣�궎吏� �긽�뭹�샃�뀡 以묐났 �뿬遺� 泥댄겕(媛숈� �씪�옄 以묐났 遺덇�)
	public int itemsCheck(Map param){	
		return sqlSession.getMapper(ProductDao.class).itemsCheck(param);
	}
}
