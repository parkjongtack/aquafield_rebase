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
	                        		</div>
	                        		<div class="tab-pane">
	                        			<h2>주차안내</h2>
	                        			<dl>
	                        				<dt><p>주차요금</p></dt>
	                        				<dd class="traffic">
					                            <ul>
													<li>스타필드 안성의 주차요금은 무료입니다.</li>
													<li>트레이더스 이용 고객은 지하주차장을 이용해 주시기 바랍니다.</li>
													<!-- <li>쇼핑몰 운영시간 (10:00 ~ 22:00) 이후 주차 고객은 지하 1층/2층/3층 주차장 이용가능</li> -->
												</ul>
					                        </dd>
	                        			</dl>
	                        			<dl>
	                        				<dt><p>주차위치별 가까운 주요매장 안내</p></dt>
	                        				<dd class="traffic">
					                            <ul>
													<li>B2 : 트레이더스, 모던하우스</li>
													<li>B1 : 키즈스플래쉬, 자동차 수리/세차</li>
													<li>1F : 고메스트리트, 시티마켓, SPA(H&M, ZARA, 스파오, 유니클로)</li>
													<li>2F : 일렉트로마트, 신세계팩토리, 토이킹덤, 영풍문고, BMW, 제네시스</li>
													<li>3F : 메가박스, 아쿠아필드, 스포츠몬스터</li>
												</ul>
					                        </dd>
	                        			</dl>
				                        <h2>주차진입로</h2>
				                        <dl>
				                        	<dt><p>북측 진입로(안성IC 방면)</p></dt>
		                        			<dd class="traffic">
					                            <p>지하 / 별관 주차장 진입로</p>
					                        </dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>남측 진입로(진사도서관 방면)</p></dt>
		                        			<dd class="traffic">
		                        				<p>지하 / 별관 주차장 진입로</p>
		                        			</dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>서측 진입로(삼천리도시가스 방면)</p></dt>
		                        			<dd class="traffic">
		                        				<p>본관 주차장 진입로</p>
		                        			</dd>
		                        		</dl>
	                        		</div>
	                        		<div class="tab-pane">
										<h2>매장 오시는 길</h2>
	                        			<dl>
		                        			<dt><p>본관 지상주차장</p></dt>
		                        			<dd class="traffic">
		                        				<p><span>F1</span> – 1 HALL 엘리베이터/ ‘까사미아’, ‘더앨리’ 앞 에스컬레이터 이용</p>
		                        				<p><span>F2</span> – 1 HALL 엘리베이터/ ‘BMW’, ‘신세계팩토리스토어’ 앞 에스컬레이터 이용</p>
		                        			</dd>
		                        		</dl>
										<dl>
		                        			<dt><p>본관 지하주차장</p></dt>
		                        			<dd class="traffic">
		                        				<p><span>B1</span> - 1,5번 HALL 엘리베이터/에스컬레이터 이용 (주차구역 : 3~6번 라인)</p>
		                        				<p><span>B2</span> - 1,5번 HALL 엘리베이터/에스컬레이터 이용 (주차구역 : 3~6번 라인)</p>
		                        			</dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>별관 주차장, 야외주차장</p></dt>
		                        			<dd class="traffic">
		                        				<p><span>1F, M1F</span> - 스타필드 1층 입구 전방 약 500m ‘까사미아’, ‘더앨리’ 앞 에스컬레이터/엘리베이터 이용</p>
		                        				<p><span>2F, M2F</span> - 스타필드 2층 입구 전방 약 500m ‘BMW’, ‘신세계팩토리스토어’ 앞 에스컬레이터/엘리베이터 이용</p>
		                        				<p><br/>※ 도보 이용 고객은 1F 정문 게이트 통과 → 게이트 앞 에스컬레이터 이용 3층까지</p>
		                        			</dd>
		                        		</dl>
		                        		<!-- <dl>
		                        			<dt><p>쇼핑몰에서 오시는 길</p></dt>
		                        			<dd class="traffic">
		                        				<p><span>1F</span> - ‘뉴에라’ 앞 1번 에스컬레이터 이용 / 1번 에스컬레이터 이용 </p>
					                            <p style="padding-left: 25px;">‘카카오프렌즈’ 앞 2번 엘리베이터 / 2번 에스컬레이터 이용</p>
					                            <p><span>2F</span> - ‘불가리’ 앞 1번 에스컬레이터 이용 / 1번 에스컬레이터 이용</p>
					                            <p style="padding-left: 25px;">‘캐나다 구스’ 앞 2번 엘리베이터 / 2번 에스컬레이터 이용</p>
		                        			</dd>
		                        		</dl> -->
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
		        map.setLevel(level + 0,{
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
							$('#addressBx').show();
							removeMarker(markers);
							marker = aquaMarkers(map);
							zoomOut();
							marker.setMap(map);
						}else if($(this).hasClass('parking')){
							$('#aquaEnter').addClass('show');
							$('#aquaEnter').addClass('show_parking');
							$('#addressBx').hide();
							
						}else if($(this).hasClass('way_aqua')){
							$('#aquaEnter').addClass('show');
							$('#aquaEnter').removeClass('show_parking');
							$('#addressBx').show();
							TweenMax.fromTo($('#aquaEnter'),0.5,{ alpha : 0 },{alpha : 1, ease: Power1.easeInOut});
						}
					
				}
			})
        </script>
    </section>
