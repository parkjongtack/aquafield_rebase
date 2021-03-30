<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp" %>
<%
String ie678 = "";
String ua=request.getHeader("User-Agent");
System.out.println(ua);
if(ua.indexOf("MSIE 6") > 0 || ua.indexOf("MSIE 7") > 0 || ua.indexOf("MSIE 8") > 0 ) {
	ie678 = "map_img.jsp";
}else{
	//ie678 = "map_img.jsp";
	ie678 = "map_svg.jsp";
}
%>
<div class="contentArea active">
	<div id="facilities" class="content v2">
		<div class="content-inner">
			<div class="content-box">
				<div class="inner">
					<button class="btn_close layer_close" onclick="window.location='#/facilities/';">닫기</button>
					<div class="box-content">
						<div class="content on">
							<jsp:include page="<%= ie678 %>" flush="false" />
						</div>
					</div>
				</div>
			</div>
			<div class="content-cate">
				<div class="inner">
					<div class="cate_title">
						<div class="inner">
							<p>02</p>
							<h1>Facilities</h1>
						</div>
					</div>
					<div class="cate_list iscContent cate02">
						<div class="inner">
							<ul>
								<li class="cate_spa">
									<a href="#/facilities/index.af?depth1=2&depth2=2">
										<div class="txt">
											<div class="inner">
												<strong>찜질스파</strong>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_in_water">
									<a href="#/facilities/index.af?depth1=2&depth2=1">
										<div class="txt">
											<div class="inner">
												<strong>실내 워터파크</strong>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_fnb">
									<a href="#/facilities/index.af?depth1=2&depth2=4">
										<div class="txt">
											<div class="inner">
												<strong>F&B</strong>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_af">
									<a href="#/facilities/index.af?depth1=2&depth2=5">
										<div class="txt">
											<div class="inner">
												<strong>기타시설</strong>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_out_water">
									<a href="#/facilities/index.af?depth1=1">
										<div class="txt">
											<div class="inner">
												<strong>야외 워터파크</strong>
											</div>
										</div>
									</a>
								</li>
								<li class="cate_sauna">
									<a href="#/facilities/index.af?depth1=2&depth2=3">
										<div class="txt">
											<div class="inner">
												<strong>사우나</strong>
											</div>
										</div>
									</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>