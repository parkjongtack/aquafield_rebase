package com.soft.web.service.admin;

import java.util.List;
import java.util.Map;

public interface AdminSaleStatisticsService {
	
	//매출(상품별)통계
	public List<Map> getSaleStatistics(Map param);

}
