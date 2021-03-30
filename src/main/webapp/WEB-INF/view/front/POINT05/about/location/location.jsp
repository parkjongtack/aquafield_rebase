<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../../common/taglibs.jsp" %>
    <section id="location">
        <div class="miniTit">
        	<h1>위치안내</h1>
            <ul>
                <li>HOME</li>
                <li>ABOUT</li>
                <li>02 위치안내</li>
            </ul>
        </div>
        <div class="leftContent">
        	<div class="inner">
        		<div id="aquaEnter">
        		
        		</div>
        		<div id="map"></div>
        	</div>
        </div>
        <div class="rightContent">
            <div class="inner">
                <div class="tit row">
                    <div class="col s12 paraShowing" data-dirc='x' data-dist='150'>
                        <div class="tabs">
                        	<header>
                        		<ul class="tab_nav">
	                        		<li class="active">
	                        			<a href="#" class="way_star"><span class="ico"></span>오시는 방법</a>
	                        		</li>
	                        		<li>
	                        			<a href="#" class="parking"><span class="ico"></span>주차안내</a>
	                        		</li>
	                        		<li>
	                        			<a href="#" class="way_aqua"><span class="ico"></span>매장 오시는 길</a>
	                        		</li>
	                        	</ul>
                        	</header>
                        	<div class="tab_cont iscContent">
                        		<div class="inner">
                        			<div class="tab-pane active">
	                        			<h2>자동차 이용시</h2>
				                        <dl>
				                        	<dt><p>안성</p></dt>
				                        	<dd class="traffic">
					                            <table class="traffic1">
					                                <tbody>
					                                    <tr>
					                                        <th class="local">안성방면 : </th>
					                                        <td><span>38번 국도 이용</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>평택 안중 방면</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>안성IC</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>스타필드 안성</span></td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">평택방면 : </th>
					                                        <td><span>38번 국도 이용</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>안성IC 방면</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>진사2길</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>스타필드 안성</span></td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">오산방면 : </th>
					                                        <td><span>경부고속도로 이용</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>안성IC</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>진사2길/1번 국도 이용</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>38번 국도</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>진사2길</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>스타필드 안성</span></td>
														</tr>
														<tr>
					                                        <th class="local">아산방면 : </th>
					                                        <td><span>45번 국도 이용</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>38번 국도</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>진사2길</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>스타필드 안성</span></td>
														</tr>
														<tr>
					                                        <th class="local">천안방면 : </th>
					                                        <td><span>경부고속도로 이용</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>안성IC</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>진사2길/1번 국도 이용</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>8번 국도</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>진사2길</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>스타필드 안성</span></td>
					                                    </tr>
					                                </tbody>
					                            </table>
											</dd>
										</dl>
										<h2>버스 이용시</h2>
				                        <dl>
				                        	<dt><p>1) 용이동 삼천리가스, 용이푸르지오 2차 앞 하차(도보 7분)</p></dt>
				                        	<dd class="traffic">
				                        		<table class="traffic1">
				                        			<tbody>
					                                    <tr>
					                                        <th class="local">일반버스 : </th>
					                                        <td>
						                                        <span>100-2, 17, 320, 320-1, 370, 380, 50, 50-9, 70, 777, 80, 80-1, 810, 91, 94, 94-1, 940, 98, 98-1</span>
															</td>
															<th class="local">시외버스 : </th>
					                                        <td>
						                                        <span>1307, 1310, 8131, 8321</span>
															</td>
					                                    </tr>
					                                     <!-- <tr>
					                                        <th class="local">덕소방면 : </th>
					                                        <td><span>경강로</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>창우로</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>미사대로</span>
															</td>
					                                    </tr> -->
				                        			</tbody>
				                        		</table>
				                        	</dd>
				                        </dl>
				                        <dl>
				                        	<dt><p>2) 스타필드, 용이동 삼천리가스 정류장 하차(도보 5분)</p></dt>
				                        	<dd class="traffic last">
				                        		<table class="traffic2">
				                        			<tbody>
					                                    <tr>
					                                        <th class="local">일반버스 : </th>
					                                        <td>
						                                        <span>100-2, 17, 370, 380, 50, 50-9, 70</span>
															</td>
														</tr>
														<tr>
					                                        <th class="local">시외버스 : </th>
					                                        <td>
						                                        <span>1307, 1310, 8131, 8321</span>
															</td>
					                                    </tr>
				                        			</tbody>
				                        		</table>
				                        	</dd>
										</dl>
										<h2>버스 + 지하철 이용시</h2>
				                        <dl>
				                        	<dt><p>1) 평택역 하차</p></dt>
				                        	<dd class="traffic">
				                        		<table class="traffic1">
				                        			<tbody>
														<tr class="ml_10">
															<th class="local">1. 평택시외버스터미널 정류장 <span class="arrow_blue">▶</span> 스타필드, 용이동 삼천리가스 정류장</th>
														</tr>
														<tr class="ml_20">
					                                        <th class="local">일반버스 : </th>
					                                        <td>
						                                        <span>70, 370, 380</span>
															</td>
														</tr>
														<tr class="ml_20">
															<th class="local">시외버스 : </th>
					                                        <td>
						                                        <span>1307, 1310, 8131, 8321</span>
															</td>
														</tr>
														<tr class="ml_10">
															<th class="local">2. 평택역, AKPLAZA 정류장 <span class="arrow_blue">▶</span> 스타필드, 용이동 삼천리가스 정류장</th>
														</tr>
														<tr class="ml_20">
					                                        <th class="local">일반버스 : </th>
					                                        <td>
						                                        <span>50, 50-9, 100-2</span>
															</td>
					                                    </tr>
					                                     <!-- <tr>
					                                        <th class="local">덕소방면 : </th>
					                                        <td><span>경강로</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>창우로</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>미사대로</span>
															</td>
					                                    </tr> -->
				                        			</tbody>
				                        		</table>
				                        	</dd>
										</dl>
										<dl>
				                        	<dt><p>2) 지제역 하차</p></dt>
				                        	<dd class="traffic">
				                        		<table class="traffic1">
				                        			<tbody>
														<tr class="ml_10">
															<th class="local">1. 평택지제역 정류장 <span class="arrow_blue">▶</span> 스타필드, 용이동 삼천리가스 정류장</th>
														</tr>
														<tr class="ml_20">
					                                        <th class="local">일반버스 : </th>
					                                        <td>
						                                        <span>50, 50-9</span>
															</td>
														</tr>
														<tr class="ml_10">
															<th class="local">2. 평택역, AKPLAZA 정류장 <span class="arrow_blue">▶</span> 스타필드, 용이동 삼천리가스 정류장</th>
														</tr>
														<tr class="ml_20">
					                                        <th class="local">일반버스 : </th>
					                                        <td>
						                                        <span>50, 50-9, 100-2</span>
															</td>
					                                    </tr>
					                                     <!-- <tr>
					                                        <th class="local">덕소방면 : </th>
					                                        <td><span>경강로</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>창우로</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>미사대로</span>
															</td>
					                                    </tr> -->
				                        			</tbody>
				                        		</table>
				                        	</dd>
				                        </dl>
				                        <!-- <h2>버스 이용시</h2>
				                        <dl>
				                        	<dd class="traffic last">
				                        		<div class="bus_route red">
					                            	<div class="bus_num">9302</div>
					                            	<div class="route_time">
					                            		배차간격 <span>8-15분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li class="import">
								                            잠실역 환승센터
					                            		</li>
					                            		<li>
					                            			올림픽대로
					                            		</li>
					                            		<li>
					                            			미사강변
					                            		</li>
					                            		<li>
					                            			풍산지구
					                            		</li>
					                            		<li class="sf">
					                            			스타필드
					                            		</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_route red">
					                            	<div class="bus_num">9303</div>
					                            	<div class="route_time">
					                            		배차간격 <span>12-20분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li class="import">
					                            			강남역<div class="comment">(2호선,신분당선)</div>
					                            		</li>
					                            		<li class="import">
					                            			역삼역<div class="comment">(2호선)</div>
					                            		</li>
					                            		<li class="import">
					                            			선릉역<div class="comment">(2호선,분당선)</div>
					                            		</li>
					                            		<li class="import">
					                            			삼성역<div class="comment">(2호선)</div>
					                            		</li>
					                            		<li class="import">
					                            			잠실역<div class="comment">(2호선, 8호선)</div>
					                            		</li>
					                            		<li>
					                            			미사강변신도시
					                            		</li>
					                            		<li class="sf">
					                            			스타필드
					                            		</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_route red">
					                            	<div class="bus_num">9303-1</div>
					                            	<div class="route_time">
					                            		배차간격 평일 <span>60분</span>, 주말 <span>120분</span>
					                            	</div>
					                            	<ul class="route_line">
							                            <li class="import">잠실역</li>
							                            <li>올림픽대로</li>
							                            <li>미사강변</li>
							                            <li>풍산지구</li>
							                            <li class="sf">스타필드</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_station">
					                            	<div class="tit">한국아파트 정거장 통과 노선 (도보 10분)</div>
					                            	<div class="cont"><span class="green">30</span> <span class="comment">(8호선)</span>, <span class="green">30-1</span> <span class="comment">(5호선, 8호선)</span>, <span class="green">30-3</span> <span class="comment">(5호선, 8호선)</span>, <span class="green">30-5</span> <span class="comment">(5호선, 8호선)</span>, <span class="green">112</span> <span class="comment">(2호선, 5호선, 8호선)</span>, <span class="blue">341</span> <span class="comment">(2호선, 5호선, 8호선)</span>, <span class="red">9301</span> <span class="comment">(1호선, 2호선, 3호선, 5호선, 8호선)</span></div>
					                            </div>

					                            <div class="bus_route green">
					                            	<div class="bus_num">23</div>
					                            	<div class="route_time">
					                            		배차간격 <span>10분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li>
					                            			내촌차고지
					                            		</li>
					                            		<li class="import">
					                            			금곡역<br/> 1번출구<div class="comment">(경춘선)</div>
					                            		</li>
					                            		<li class="import">
					                            			도농역<br/> 2번출구<div class="comment">(경의중앙선)</div>
					                            		</li>
					                            		<li class="import">
					                            			구리역<br/> 2번출구<div class="comment">(경의중앙선)</div>
					                            		</li>
					                            		<li>
					                            			구리경찰서
					                            		</li>
					                            		<li class="import">
					                            			금곡역<br/> 5,6번출구<div class="comment">(8호선)</div>
					                            		</li>
					                            		<li class="sf">
					                            			스타필드
					                            		</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_route green">
					                            	<div class="bus_num">81</div>
					                            	<div class="route_time">
					                            		배차간격 <span>10분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li class="import">
					                            			삼일동역<div class="comment">(5호선)</div>
					                            		</li>
					                            		<li>
					                            			미시강변 28단지
					                            		</li>
					                            		<li>
					                            			덕풍시장
					                            		</li>
					                            		<li>
					                            			신장시장
					                            		</li>
					                            		<li>
					                            			하남시청
					                            		</li>
					                            		<li class="sf">
					                            			스타필드
					                            		</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_route yellow">
					                            	<div class="bus_num">50</div>
					                            	<div class="route_time">
					                            		배차간격 <span>5분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li class="import">
					                            			하남<br/> 종합 운동장
					                            		</li>
					                            		<li>
					                            			하남고등학교
					                            		</li>
					                            		<li>
					                            			미시강변<br/> 22단지
					                            		</li>
					                            		<li>
					                            			덕풍시장(중)
					                            		</li>
					                            		<li>
					                            			하남시청
					                            		</li>
					                            		<li class="sf">
					                            			스타필드
					                            		</li>
					                            		<li class="import">
					                            			팔당역<div class="comment">(경의중앙선)</div>
					                            		</li>
					                            	</ul>
					                            </div> -->
					                            <!-- <table class="traffic3">
					                                <tbody>
						                                <tr>
						                               		<th class="local">스타필드 하남 정류장 : </th>
						                                    <td><span class="red">9302</span>, <span class="red">9303</span><span>(9월 신설예정)</span>, <span class="green">81</span>, <span class="green">23</span> </td>
						                                </tr>
					                                </tbody>
					                            </table>
					                            <table class="traffic4">
					                                <tbody>
						                                <tr>
						                                	<th class="local">창우초교 / 부영아파트 정류장 : <br><span>(하차 후 도보 5분)</span></th>
						                                    <td><span class="red">9301</span>, <span class="red">341</span>, <span class="green">112</span>, <span class="green">30</span>, <span class="green">30-5</span>, <span class="green">20</span>, <span class="green">10</span>, <span class="green">5</span>, <span class="green">2</span>, <span class="green">2-1</span>, <span class="green">1</span> </td>
						                                </tr>
					                                </tbody>
					                            </table>
											 </dd>
				                        </dl> -->
				                        <!-- <dl>
				                        	<dd class="traffic bycycle">
					                            <h2>자전거 이용시</h2>
					                            <table class="traffic5">
					                                <tbody>
														<tr>
															<th class="local">반포한강공원 / 탄천합수부 출발 : </th>
															<td>
															    <span>미사리조정경기장덕풍교</span> <span>건너서</span> <span>우측</span> <span>상단</span> <span>자전거</span> <span>도로</span>
															</td>
														</tr>
					                                </tbody>
					                            </table>
					                         </dd>
					                         <dd class="traffic_caption">
					                         	<table class="traffic6">
					                         		<tbody>
					                         			<tr>
					                         				<th>* 자전거 보관소 위치 :</th>
					                         				<td> 1층 지상 (남서측 광장 앞, 데블스도어 앞, 와츠 사이클링 앞)</td>
					                         			</tr>
					                         		</tbody>
					                         	</table>
					                         </dd>
				                        </dl> -->
	                        		</div>
	                        		<div class="tab-pane">
	                        			<h2>주차안내</h2>
	                        			<dl>
	                        				<dt><p>스타필드 하남 주차장 이용</p></dt>
	                        				<dd class="traffic">
					                            <ul>
													<li>쇼핑몰 운영시간 전에는 지상(3층,4층)층만 이용 가능</li>
													<li>쇼핑몰 운영시에는 지하/지상 주차장 이용 가능</li>
													<li>쇼핑몰 운영시간 (10:00 ~ 22:00) 이후 주차 고객은 지하 1층/2층/3층 주차장 이용가능</li>
												</ul>
					                        </dd>
	                        			</dl>
	                        			<dl>
	                        				<dt><p>주차요금</p></dt>
	                        				<dd class="traffic">
					                            <p>스타필드 하남의 주차요금은 무료 (상기 정책은 스타필드 주차정책에 따라 변경될 수도 있습니다.)</p>
					                        </dd>
	                        			</dl>
				                        <h2>주차진입로</h2>
				                        <dl>
				                        	<dt><p>동측 진입로(미사대로 방면)</p></dt>
		                        			<dd class="traffic">
					                            <p>지하·지상 주차장 진입로 | 백화점 VIP 발렛</p>
												<p>서울 (강남 / 잠실 / 광진 / 천호 / 강동) / 구리 / 남양주 / 덕소 방면</p>
					                        </dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>남측 진입로(하남 IC 방면)</p></dt>
		                        			<dd class="traffic">
		                        				<p>지하 주차장 진입로</p>
		                        			</dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>서측 진입로(상일 IC 방면)</p></dt>
		                        			<dd class="traffic">
		                        				<p>지하 주차장 진입로</p>
		                        			</dd>
		                        		</dl>
	                        		</div>
	                        		<div class="tab-pane">
										<h2>매장 오시는 길</h2>
	                        			<dl>
		                        			<dt><p>지상 주차장에서 오시는 길</p></dt>
		                        			<dd class="traffic">
		                        				<p>쇼핑몰 입구 게이트 좌측 11번 엘리베이터이용 (1층 ~ 3층 운영)</p>
		                        			</dd>
		                        		</dl>
										<dl>
		                        			<dt><p>지하 주차장에서 오시는 길</p></dt>
		                        			<dd class="traffic">
		                        				<p><span>B1</span> - 1번 홀 엘리베이터 / 에스컬레이터 이용 (주차구역 : 1J, 2J, 3J)</p>
		                        				<p><span>B2</span> - 1번 홀 엘리베이터 / 에스컬레이터 이용 (주차구역 : 15J, 13L)</p>
		                        				<p><span>B3</span> - 1번 홀 엘리베이터 / 에스컬레이터 이용 (주차구역 : 17J, 18J)</p>
		                        			</dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>쇼핑몰 영업시간</p></dt>
		                        			<dd class="traffic">
		                        				<p class="wordBraek">월 ~ 목 10:00 - 21:00, 금 ~ 일 10:00 ~ 22:00 외에는 1번홀 엘리베이터만 이용 가능합니다.</p>
		                        			</dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>쇼핑몰에서 오시는 길</p></dt>
		                        			<dd class="traffic">
		                        				<p><span>1F</span> - ‘뉴에라’ 앞 1번 에스컬레이터 이용 / 1번 에스컬레이터 이용 </p>
					                            <p style="padding-left: 25px;">‘카카오프렌즈’ 앞 2번 엘리베이터 / 2번 에스컬레이터 이용</p>
					                            <p><span>2F</span> - ‘불가리’ 앞 1번 에스컬레이터 이용 / 1번 에스컬레이터 이용</p>
					                            <p style="padding-left: 25px;">‘캐나다 구스’ 앞 2번 엘리베이터 / 2번 에스컬레이터 이용</p>
		                        			</dd>
		                        		</dl>
	                        		</div>
                        		</div>
                        	</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div id="addressBx">
			<div class="inner">
				<ul>
    				<li>주   소   :  경기 안성시 공도읍 서동대로 3930-39 (스타필드 안성)</li>
    				<li>연락처   :  031-8092-1900</li>
    			</ul>
			</div>
		</div>
		<input type="hidden" id="pointCode" value="${POINT_CODE}"/>
        <script type="text/javascript">
        	var marker;
        	var markers = [];
        	var pointCode = $("#pointCode").val();
		    function drawMap(){
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div
				    mapOption = {
				        center: new daum.maps.LatLng(36.9945375, 127.1466519), // 지도의 중심좌표
				        level: 4 // 지도의 확대 레벨
				    };
		
				var map = new daum.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
				
				
				
				marker = aquaMarkers(map);
				return map;
		    };
		    
		    map = drawMap();
		    
		 	// 지도 레벨은 지도의 확대 수준을 의미합니다
		    // 지도 레벨은 1부터 14레벨이 있으며 숫자가 작을수록 지도 확대 수준이 높습니다
		    function zoomIn() {
		        // 현재 지도의 레벨을 얻어옵니다
		        var level = map.getLevel();
		        
		        // 지도를 1레벨 내립니다 (지도가 확대됩니다)
		        map.setLevel(level - 1,{
		            animate: {
		                duration: 500
		            }
		        });
		        
		        // 지도 레벨을 표시합니다
		        //displayLevel();
		    }
		
		    function zoomOut() {
		        // 현재 지도의 레벨을 얻어옵니다
		        var level = map.getLevel();
		        
		        // 지도를 1레벨 올립니다 (지도가 축소됩니다)
		        map.setLevel(level + 1,{
		            animate: {
		                duration: 500
		            }
		        });
		        
		        // 지도 레벨을 표시합니다
		        //displayLevel();
		    }
		    
		    function removeMarker(){
		    	for (var i = 0; i < markers.length; i++) {
			    	markers[i].setMap(null);
			    }
		    }
		    
		    function aquaMarkers(map){
		    	var	imageSrc = '../common/front/images/'+pointCode+'/about/map_marker.png', // 마커이미지의 주소입니다
				imageSize = new daum.maps.Size(131, 93), // 마커이미지의 크기입니다
				imageOption = {offset: new daum.maps.Point(27, 69)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

				// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
				var	markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imageOption),
				markerPosition = new daum.maps.LatLng(36.9945375, 127.1466519); // 마커가 표시될 위치입니다
				
				// 마커를 생성합니다
		    	marker = new daum.maps.Marker({
		    		map: map // 마커를 표시할 지도
				    ,position: markerPosition
					,image: markerImage // 마커이미지 설정
				});
		    	markers.push(marker);
				return marker;
		    }
		    
			$('.tab_nav a').click(function (e) {
				e.preventDefault();
				var re_class = $(this).attr('class');
				if(re_class.indexOf('parking') != -1 || re_class.indexOf('way_aqua') != -1){
					alert('서비스 준비중입니다.');
					return false;
				}else{
					if(!$(this).parent().hasClass('active')) {
						var idx = $('.tab_nav li').index($(this).parent()),
							t = $(this).closest('.tabs').find('.tab-pane');
						$(this).parent().siblings(".active").removeClass("active").end().addClass("active");
						TweenMax.to(t,0.25,{alpha : 0, ease: Power1.easeInOut, onComplete : function(){
							t.eq(idx).siblings(".active").removeClass("active").end().addClass("active");
							TweenMax.to(t.eq(idx),0.25,{alpha : 1, ease: Power1.easeInOut});
						}});

						if($(this).hasClass('way_star')){
							$('#aquaEnter').removeClass('show');
							// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
							removeMarker(markers);
							marker = aquaMarkers(map);
							zoomOut();
							marker.setMap(map);
						}else if($(this).hasClass('parking')){
							$('#aquaEnter').removeClass('show');
							removeMarker(markers);
							var positions = [
								{
									title: '동측 진입로',
									latlng: new daum.maps.LatLng(37.5446182, 127.225806)
								},
								{
									title: '남측 진입로',
									latlng: new daum.maps.LatLng(37.5443109, 127.2244432)
								},
								{
									title: '서측 진입로',
									latlng: new daum.maps.LatLng(37.5457757, 127.2215538)
								},
								{
									title: '지하 지상 주차장 진입로 | 백화점 VIP 발렛',
									latlng: new daum.maps.LatLng(37.5432382, 127.2243621)
								}
							];
							
							for (var i = 0; i < positions.length; i ++) {
								// 마커를 생성합니다
								marker = new daum.maps.Marker({
									map: map, // 마커를 표시할 지도
									position: positions[i].latlng, // 마커를 표시할 위치
									title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
								});
								markers.push(marker);
								marker.setMap(map);
							}
							zoomIn();
						}else if($(this).hasClass('way_aqua')){
							$('#aquaEnter').addClass('show');
							TweenMax.fromTo($('#aquaEnter'),0.5,{ alpha : 0 },{alpha : 1, ease: Power1.easeInOut});
						}
					}
				}
			})
        </script>
    </section>
