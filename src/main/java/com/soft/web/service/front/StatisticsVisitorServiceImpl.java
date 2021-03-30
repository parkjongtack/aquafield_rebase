package com.soft.web.service.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.soft.web.dao.front.StatisticsVisitorDao;

@Service
public class StatisticsVisitorServiceImpl implements StatisticsVisitorService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Resource(name="sqlSession")
    protected SqlSession sqlSession;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean visitorDataInsert(Map<?,?> param) throws Throwable {
		//-- 접속자
		if(this.getCountVisitor(param) > 0){
			//-- 접속 누적
			this.updateVisitor(param);
		}
		else{
			//-- 신규등록
			this.insertVisitor(param);
		}
		
		
		//-- DEVICE
		if(!"".equals(param.get("DEVICE"))){
			if(this.getCountDevice(param) > 0){
				//-- 접속 누적
				this.updateDevice(param);
			}
			else{
				//-- 신규등록
				this.insertDevice(param);
			}
		}
		
		//-- OS
		if(!"".equals(param.get("OS"))){
			if(this.getCountOs(param) > 0){
				//-- 접속 누적
				this.updateOs(param);
			}
			else{
				//-- 신규등록
				this.insertOs(param);
			}
		}

		//-- 해상도
		if(!"".equals(param.get("SCREEN_X")) && !"".equals(param.get("SCREEN_Y"))){
			if(this.getCountScreen(param) > 0){
				//-- 접속 누적
				this.updateScreen(param);
			}
			else{
				//-- 신규등록
				this.insertScreen(param);
			}
		}
		
		//-- 유입페이지
		if(!"".equals(param.get("REFERER"))){
			String referer = param.get("REFERER").toString();
			String serverName = param.get("SERVER_NAME").toString();
			
			if(referer.indexOf(serverName) < 0){
				if(this.getCountReferer(param) > 0){
					//-- 접속 누적
					this.updateReferer(param);
				}
				else{
					//-- 신규등록
					this.insertReferer(param);
				}
			}
		}

		//-- 브라우저
		if(!"".equals(param.get("EI").toString()) || !"".equals(param.get("EI_VER").toString())){
			if(this.getCountBrowser(param) > 0){
				//-- 접속 누적
				this.updateBrowser(param);
			}
			else{
				//-- 신규등록
				this.insertBrowser(param);
			}
		}
		
		return true;
	}

	@Override
	public int getCountVisitor(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).getCountVisitor(param);
	}

	@Override
	public int getCountDevice(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).getCountDevice(param);
	}

	@Override
	public int getCountBrowser(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).getCountBrowser(param);
	}

	@Override
	public int getCountOs(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).getCountOs(param);
	}

	@Override
	public int getCountReferer(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).getCountReferer(param);
	}

	@Override
	public int getTotalCountReferer(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).getTotalCountReferer(param);
	}

	@Override
	public int getCountScreen(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).getCountScreen(param);
	}

	@Override
	public Map listVisitorTime(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).listVisitorTime(param);
	}

	@Override
	public List listVisitorDay(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).listVisitorDay(param);
	}

	@Override
	public List listVisitorMonth(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).listVisitorMonth(param);
	}

	@Override
	public List listVisitorDevice(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).listVisitorDevice(param);
	}

	@Override
	public List listVisitorBrowser(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).listVisitorBrowser(param);
	}

	@Override
	public List listVisitorOs(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).listVisitorOs(param);
	}

	@Override
	public List listVisitorReferer(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).listVisitorReferer(param);
	}

	@Override
	public List listVisitorScreen(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).listVisitorScreen(param);
	}

	@Override
	public int insertVisitor(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).insertVisitor(param);
	}

	@Override
	public int insertDevice(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).insertDevice(param);
	}

	@Override
	public int insertBrowser(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).insertBrowser(param);
	}

	@Override
	public int insertOs(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).insertOs(param);
	}

	@Override
	public int insertReferer(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).insertReferer(param);
	}

	@Override
	public int insertScreen(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).insertScreen(param);
	}

	@Override
	public int updateVisitor(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).updateVisitor(param);
	}

	@Override
	public int updateDevice(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).updateDevice(param);
	}

	@Override
	public int updateBrowser(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).updateBrowser(param);
	}

	@Override
	public int updateOs(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).updateOs(param);
	}

	@Override
	public int updateReferer(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).updateReferer(param);
	}

	@Override
	public int updateScreen(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).updateScreen(param);
	}

	@Override
	public int updatePage(Map param) throws Exception {
		return sqlSession.getMapper(StatisticsVisitorDao.class).updatePage(param);
	}

}
