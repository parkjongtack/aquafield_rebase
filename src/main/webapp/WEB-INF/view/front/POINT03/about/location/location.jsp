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
        		<div id="aquaParking">

        		</div>
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
                        				<!-- <img src="/common/front/images/${POINT_CODE }/facilities/img_pop_none.jpg"> -->
	                        			<h2>자동차 이용시</h2>
				                        <dl>
				                        	<dt><p>서울지역</p></dt>
				                        	<dd class="traffic">
					                            <table class="traffic1">
					                                <tbody>
					                                    <tr>
					                                        <th class="local">은평 : </th>
					                                        <td><span>연신내역</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>구파발 방면</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>동산육교 좌회전</span></td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">서대문 : </th>
					                                        <td><span>수색역</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>행신교차로</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>원흥지하차도</span></td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">마포 : </th>
					                                        <td><span>강변북호(일산방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>북로JC(행신/원흥냉면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>원흥지하차도</span></td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">종로 : </th>
					                                        <td><span>종로</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>독립문역</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>연신내역</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>동산고가밑 좌회전</span></td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">강서 : </th>
					                                        <td><span>가양대교</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>강변북로(일산방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>북로JC(행신/원흥반면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>원흥지하차도</span></td>
					                                    </tr>
					                                </tbody>
					                            </table>
											</dd>
				                        </dl>
				                        <dl>
				                        	<dt><p>경기지역</p></dt>
				                        	<dd class="traffic">
				                        		<table class="traffic1">
				                        			<tbody>
					                                    <tr>
					                                        <th class="local">김포(경기남부) : </th>
					                                        <td>
						                                        <span>김포JC(일산방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>고양IC</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>원당지하차도</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>고양대로(구파발방면)</span>
															</td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">일산/덕양구(경기북부) : </th>
					                                        <td><span>원당역</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>고양대로(구파발방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>스차필드 고양</span>
															</td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">파주(경기북부) : </th>
					                                        <td><span>제2자유로(서울방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>강매IC(행신방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>원흥지하차도</span>
															</td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">의정부/양주(경기북부) : </th>
					                                        <td><span>호원IC(일산방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>통일로IC(서울,삼송역 방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>동산육교밑 우회전</span>
															</td>
					                                    </tr>
					                                    <tr>
					                                        <th class="local">인천(경기남부) : </th>
					                                        <td><span>서울외곽순환고속도로(일산방면)</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>고양IC</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>원당지하차도</span>
																<span class="arrow"><img src="/common/front/images/ico/ico_location_arr.png" alt="" /></span>
																<span>고양대로(서울,구파발방면)</span>
															</td>
					                                    </tr>
				                        			</tbody>
				                        		</table>
				                        	</dd>
				                        </dl>
				                        <h2>버스 이용시</h2>
				                        <dl>
				                        	<dd class="traffic last">
				                        		<div class="bus_route red">
					                            	<div class="bus_num">9703</div>
					                            	<div class="route_time">
					                            		배차간격 <span>6-18분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li>
					                            			세종문화회관
					                            		</li>
					                            		<li>
					                            			숭례문
					                            		</li>
					                            		<li>
					                            			불관역
					                            		</li>
					                            		<li>
					                            			구파발역
					                            		</li>
					                            		<li class="sf">
					                            			창릉동주민센터,용사촌입구
					                            		</li>
					                            		<li class="sf">
					                            			동산마을22단지
					                            		</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_route blue">
					                            	<div class="bus_num">705</div>
					                            	<div class="route_time">
					                            		배차간격 <span>15-20분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li>
					                            			광화문
					                            		</li>
					                            		<li>
					                            			남대문시장,회현역
					                            		</li>
					                            		<li>
					                            			불광역
					                            		</li>
					                            		<li>
					                            			구파발역
					                            		</li>
					                            		<li class="sf">
					                            			창릉동주민센터,용사촌입구
					                            		</li>
					                            		<li class="sf">
					                            			동산마을22단지
					                            		</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_route blue">
					                            	<div class="bus_num">706</div>
					                            	<div class="route_time">
					                            		배차간격 <span>13-20분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li>
					                            			서울역환승센터
					                            		</li>
					                            		<li>
					                            			숭례문
					                            		</li>
					                            		<li>
					                            			불광역
					                            		</li>
					                            		<li>
					                            			구파발역
					                            		</li>
					                            		<li class="sf">
					                            			창릉동주민센터,용사촌입구
					                            		</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_route green">
					                            	<div class="bus_num">42</div>
					                            	<div class="route_time">
					                            		배차간격 <span>60분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li>
					                            			세수동
					                            		</li>
					                            		<li>
					                            			삼송역 4번출구
					                            		</li>
					                            		<li>
					                            			농협 하나로클럽
					                            		</li>
					                            		<li class="sf">
					                            			창릉동주민센터,용사촌입구
					                            		</li>
					                            		<li class="sf">
					                            			스파필드 고양
					                            		</li>
					                            	</ul>
					                            </div>

					                            <div class="bus_route green">
					                            	<div class="bus_num">48</div>
					                            	<div class="route_time">
					                            		배차간격 <span>20-40분</span>
					                            	</div>
					                            	<ul class="route_line">
					                            		<li>
					                            			삼송마을 20단지
					                            		</li>
					                            		<li>
					                            			세수동
					                            		</li>
					                            		<li>
					                            			삼송역 4번출구
					                            		</li>
					                            		<li>
					                            			농협 하나로클럽
					                            		</li>
					                            		<li class="sf">
					                            			호반 22단지 정문
					                            		</li>
					                            	</ul>
					                            </div>
											 </dd>
				                        </dl>
				                        <dl>
				                        	<dd class="traffic bycycle">
					                            <h2>지하철 이용시</h2>
					                            <table class="traffic5">
					                                <tbody>
														<tr>
															<th class="local">지하철 3호선 삼송역 출구에서 500M 직진(도보 8분 소요)</th>
														</tr>
					                                </tbody>
					                            </table>
					                         </dd>
				                        </dl>
	                        		</div>
	                        		<div class="tab-pane">
	                        			<!-- <img src="/common/front/images/${POINT_CODE }/facilities/img_pop_none.jpg"> -->
	                        			<h2>주차안내</h2>
	                        			<dl>
	                        				<dt><p>주차요금</p></dt>
	                        				<dd class="traffic">
	                        					<ul>
													<li>스타필드 고양점의 주차요금은 무료입니다.</li>
													<li>트레이더스 이용 고객은 지하주차장을 이용해 주시기 바랍니다.</li>
												</ul>
					                        </dd>
	                        			</dl>
	                        			<dl>
	                        				<dt><p>주차위치별 가까운 주요매장 안내</p></dt>
	                        				<dd class="traffic">
					                            <ul>
													<li><strong>B2 :</strong> 이마트 트레이더스</li>
													<li><strong>B1 :</strong> PK마켓, PK키친, 노브랜드, 메종티시아, 부츠, 유니클로, 한샘, 자주</li>
													<li><strong>1F :</strong> 고메스트리트, 신세계 팩토리스토어, 몰리스펫샵, H&M, ZARA, 카카오프렌즈</li>
													<li><strong>2F :</strong> 일렉트로마트, BMW, 현대자동차, 골프존마켓, 하우디, 펀시티, 스타필드 맨즈</li>
													<li><strong>3F :</strong> 잇토피아, 토이킹덤, 토이킹덤플레이, 마리스베이비서클, 위너플레이, 영풍문고, 베이비엔젤스,<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;신세계 아카데미, 스타필드 키즈</li>
													<li><strong>4F :</strong> 아쿠아필드, 뷰티빌리지, 스포츠 몬스터, 챔피언1250, 메가박스, 데이골프</li>
												</ul>
					                        </dd>
	                        			</dl>
				                        <h2>주차진입로</h2>
				                        <dl>
				                        	<dt><p>북측 진입로</p></dt>
		                        			<dd class="traffic">
					                            <p>지상주차장 진입로</p>
					                        </dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>남측 진입로</p></dt>
		                        			<dd class="traffic">
		                        				<p>지하주차장 진입로</p>
		                        			</dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>서측 진입로</p></dt>
		                        			<dd class="traffic">
		                        				<p>지하 주차장 진입로</p>
		                        			</dd>
		                        		</dl>
	                        		</div>
	                        		<div class="tab-pane">
	                        			<!-- <img src="/common/front/images/${POINT_CODE }/facilities/img_pop_none.jpg"> -->
										<h2>매장 오시는 길</h2>
	                        			<dl>
		                        			<dt><p>4층 지상주차장 이용 시</p></dt>
		                        			<dd class="traffic">
		                        				<p>3번 HALL로 들어오셔서 200m정도 이동</p>
		                        			</dd>
		                        		</dl>
		                        		<dl>
		                        			<dt><p>지하 및 지상주차장 이용 시</p></dt>
		                        			<dd class="traffic">
		                        				<p>3층 잇토피아 앞 에스컬레이터 탑승하여 4층 이동</p>
		                        			</dd>
		                        		</dl>
		                        		<!-- 20180411 추가 -->
		                        		<dl>
		                        			<dt><p>스타필드몰 운영시간(10~22시) 외 주차장 이용안내</p></dt>
		                        			<dd class="traffic">
		                        				<p class="wordBraek">
			                        				10시 이전 방문 고객 : 북측 주차장 진입 후 4층 3번홀 주차 
			                        				<br>22시 이후 방문 고객 : 북측 주차장 진입 후 4층 3번홀 주차 
		                        				</p>
		                        			</dd>
		                        		</dl>
		                        		<dl>
		                        			<!-- <dt><p>도보이용 고객은 몰리스펫(1번 게이트) 출입구를 통해서<br> 엘리베이터 이용을 부탁드립니다.</p></dt> -->
		                        			<dt><p>도보이용 고객은 북측(3번 게이트) 출입구를 통해서<br> 엘리베이터 이용을 부탁드립니다.</p></dt>
		                        		</dl>
		                        		<br>
		                        		<dl>
		                        			<dt><p>23시 이후는 3번 GATE 출입구를 통해서<br> 엘리베이터 이용을 부탁드립니다.</p></dt>
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
    				<li>주   소   :  경기 고양시 덕양구 고양대로 1955(스타필드 고양 4층)</li>
    				<li>연락처   :  031-5173-4500</li>
    			</ul>
			</div>
		</div>
		<input type="hidden" id="pointCode" value="${POINT_CODE }"/>
        <script type="text/javascript">
        	var marker;
        	var markers = [];
        	var pointCode = $("#pointCode").val();
		    function drawMap(){
				var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
				    mapOption = { 
				        center: new daum.maps.LatLng(37.646976308152716, 126.89477840618908), // 지도의 중심좌표
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
				imageOption = {offset: new daum.maps.Point(65, 100)}; // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

				// 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
				var	markerImage = new daum.maps.MarkerImage(imageSrc, imageSize, imageOption),
				markerPosition = new daum.maps.LatLng(37.646976308152716, 126.89477840618908); // 마커가 표시될 위치입니다
				
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
						$('#aquaParking').removeClass('show');
						// 배열에 추가된 마커들을 지도에 표시하거나 삭제하는 함수입니다
						removeMarker(markers);
						marker = aquaMarkers(map);
						marker.setMap(map);
					}else if($(this).hasClass('parking')){
						/* $('#aquaEnter').removeClass('show');
						$('#aquaParking').addClass('show');
						TweenMax.fromTo($('#aquaParking'),0.5,{ alpha : 0 },{alpha : 1, ease: Power1.easeInOut}); */
						$('#aquaEnter').removeClass('show');
						removeMarker(markers);
						var positions = [
						    {
						        title: '서측 진입로', 
						        latlng: new daum.maps.LatLng(37.64581583493756, 126.8917829863507)
						    },
						    {
						        title: '남측 진입로', 
						        latlng: new daum.maps.LatLng(37.646092123310005, 126.89594108817083)
						    },
						    {
						        title: '북측 진입로', 
						        latlng: new daum.maps.LatLng(37.6472652027834, 126.89291967394544)
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
						$('#aquaParking').removeClass('show');
						TweenMax.fromTo($('#aquaEnter'),0.5,{ alpha : 0 },{alpha : 1, ease: Power1.easeInOut});
					}
				}
			})
        </script>
    </section>
