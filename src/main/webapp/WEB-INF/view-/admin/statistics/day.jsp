<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>접속통계</h2>
				</div>
			</div>
			<div class="condi_search inc_date">
	 			<ul class="clearfix">
					<li><a href="/secu_admaf/admin/statistics/index.af">Hour</a></li>
					<li class="on"><a href="/secu_admaf/admin/statistics/day.af">Day</a></li>
					<li><a href="/secu_admaf/admin/statistics/month.af">Month</a></li>
				</ul>
				<form name="searchForm" id="searchForm" method="post" action="/secu_admaf/admin/statistics/day.af" onsubmit="return false;">
				<div class="condi_date multi_search">
					<ul>
						<li class="on">
								<select name="yy" id="yy" class="customized-select"></select>
								<script type="text/javascript">
								var option_html = "";
								var selected;
								var todayYears = new Date().getFullYear();
								for(var i = todayYears; i >= 2016; i--){
									if(i == ${resultParam.yy}) selected = " selected";
									else selected = "";
									option_html += "<option value='" + i + "'" + selected + ">" + i + "</option>";
								}
								$(document.searchForm.yy).html(option_html);
								</script>

								<select name="mm" id="mm" class="customized-select"></select>
								<script type="text/javascript">
									var option_html = "";
									for(var i = 1; i < 13; i++){
										if(i == ${resultParam.mm}) selected = " selected";
										else selected = "";
										option_html += "<option value='" + i + "'" + selected + ">" + i + "</option>";
									}
									$(document.searchForm.mm).html(option_html);
								</script>
						</li>
					</ul>
				<button type="button" id="btnSearch" class="btn_pack blue" onclick="searchRun();">Search</button>
				<div class="clear"></div>
	 			</div>
	 			</form>
	 		</div>
 			<div class="area_graph">
				<div id="chart_div" style="height:1200px;">그래프 영역</div>
	 		</div>
		</div>
 	</div>
</section>
<script>
function searchRun(){
	document.getElementById("searchForm").submit();
}
</script>
<c:if test="${not empty results}">
<script type="text/javascript">
google.charts.setOnLoadCallback(drawChart);
function drawChart() {

var data = google.visualization.arrayToDataTable([
	['날짜', '접속통계', '페이지뷰'],
<c:set var="comma" value=""/>
<c:forEach items="${results}" var="result">
		${comma}['${result.YYYYMMDD}', ${result.CNT}, ${result.PAGEVIEWCNT}]
<c:set var="comma" value=","/>
</c:forEach>
	
	]);

	var options = {
		//title: '',
		//vAxis: {title: '일별',  titleTextStyle: {color: 'red'}}
	};

	var chart = new google.visualization.BarChart(document.getElementById("chart_div"));
    chart.draw(data, options);
}
</script>
</c:if>
</div>

<section id="popup_write">
</section>