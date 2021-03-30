<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../common/taglibs.jsp" %>
<section id="main">
	<div class="out_box">
		<section id="contents">
			<div class="contents_bx_type1">
				<div class="contents_bx_inner">
					<div id="path" class="fixed">
						<div class="pathInner">
							<h2>Dashboard</h2>
						</div>
					</div>
					<br/>
		 			<div class="area_graph">
		 				<div id="visitor" style="height:300px;line-height:300px;">그래프 영역</div>
		 			</div>			
					<div class="area_table tb_type1">
						<table>
						 	<colgroup>
		 						<col width=15%/>
		 						<col />
		 						<col />
		 					</colgroup>
						 	<thead>
		                        <tr>
		                            <th>일 &nbsp;&nbsp;자</th>
		                            <th>사용자</th>
		                            <th>페이지뷰</th>
		                        </tr>
						 	</thead>
							<tbody>
							<c:forEach items="${resultsVisitor}" var="result">
		                    	<tr>
		                    		<th>${result.IYEARS}-${result.IMONTHS}-${result.IDAYS}</th>
		                    		<td><fmt:formatNumber value="${result.VISITORCNT}" pattern="#,###.##"/></td>
		                    		<td><fmt:formatNumber value="${result.PAGEVIEWCNT}" pattern="#,###.##"/></td>
		                    	</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>	
				</div>
			</div>
		</section>
	</div>
</section><!-- login -->
<script type="text/javascript">
		google.charts.setOnLoadCallback(visitChart);

	    function visitChart() {

	      var data = new google.visualization.DataTable();
	      data.addColumn('string', ' ');
	      data.addColumn('number', '사용자');
	      data.addColumn('number', '페이지뷰');

	      data.addRows([
			<c:set var="comma" value=""/>
			<c:forEach items="${resultsVisitor}" var="result">
			${comma}['${result.IYEARS}-${result.IMONTHS}-${result.IDAYS}', ${result.VISITORCNT}, ${result.PAGEVIEWCNT}]
			<c:set var="comma" value=","/>
			</c:forEach>
	      ]);
      
	      var options = {
	    	vAxis: {format: 'decimal'},
	        axes: {
	          x: {
	            0: {side: 'bottom'}
	          }
	        }
	      };

	      var chart = new google.charts.Line(document.getElementById('visitor'));
	      chart.draw(data, google.charts.Bar.convertOptions(options));
	    }
</script>


<section id="popup_write">
</section>
