<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tile" %>
<%@ include file="../../common/taglibs.jsp" %>
<tile:importAttribute name="MEMINFO"/>
<tile:importAttribute name="POINT_CODE"/>
<tile:importAttribute name="POINT_LIST"/>
<tile:importAttribute name="POINT_URL"/>
<script type="text/javascript">

	function preparePopup(){
		var pop_top 	= (screen.availHeight - 440) / 2;
		var pop_left 	= (screen.availWidth - 430) / 2;
		pop_top 		= pop_top;

		//  아쿠아필드 고양점의 온라인예약 상품은 현재 준비 중입니다. 이용에 불편을 드려서 죄송 합니다. 감사 합니다.
		window.open("/common/front/popup_171025.jsp", "온라인 예약 공지",
				"scrollbars=no, width=405,  height=396, toolbar=no, enubar=no, directories=no, status=no, resizeable=no, location=no, top="+pop_top+", left=500");
	}
	
	function point01(num) {
		location.href = "/hanam/index.af#/guide/index.af?page=1";
		
		if(num == 1) {
			location.href = "/hanam/index.af#/event/eventIndex.af?cate=1";
		} else {
			location.href = "/hanam/index.af#/event/eventIndex.af?cate=2";																			
		}
	}
	
	function point03(num) {
		location.href = "/goyang/index.af#/guide/index.af?page=1";
		
		if(num == 1) {
			location.href = "/goyang/index.af#/event/eventIndex.af?cate=1";
		} else {
			location.href = "/goyang/index.af#/event/eventIndex.af?cate=2";																			
		}
	}

	function point05(num) {
		location.href = "/anseong/index.af#/guide/index.af?page=1";
		
		if(num == 1) {
			location.href = "/anseong/index.af#/event/eventIndex.af?cate=1";
		} else {
			location.href = "/anseong/index.af#/event/eventIndex.af?cate=2";
		}
	}	
	
</script>
<header id="header">
	<div class="inner">
		<div id="topMenu">
			<div class="inner">
				<ul class="leftUtil">
					<li class="siteMap">
						<a href="javascript:void(0);" class="btn_sitemap"><img src="/common/front/images/btn/btn_menu.png" alt="전체메뉴"></a>
					</li>
					<li class="selectPoint">
						<c:forEach var="pointList" items="${POINT_LIST}" varStatus="status">
							<input type="radio" class="radioPoint" name="branch_choice" id="area_ch${status.count }" value="${pointList.code_id }" <c:if test="${POINT_CODE eq pointList.code_id }">checked</c:if>/>
							<label for="area_ch${status.count }"><span>아쿠아필드 </span>${pointList.code_nm }</label>
							<input type="hidden" name="${pointList.code_id }" value="${pointList.code_url }" />
						</c:forEach>
					</li>
				</ul>
				<div class="logo">
					<a href="${POINT_URL}">
<<<<<<< HEAD
						<img class="pc" src="/common/front/images/common/logo.png" alt="아쿠아필드" width='100' height='50'>
=======
						<img class="pc" src="/common/front/images/common/logo.png" alt="아쿠아필드" width="100" height="50">
>>>>>>> 7120f25f79c341f89da6958775edf04083f7b11d
						<img class="mobile" src="/common/front/images/common/m_logo.png" alt="아쿠아필드">
					</a>
				</div>
				<ul class="rightUtil">
				 <c:choose>
				 	<c:when test="${MEMINFO.MEM_UID eq ''}">
				 		<li><button class="btn_login btn" onclick="location.href='/member/loginMain.af';">로그인</button></li>
				 		<li><button class="btn_book btn" onclick="alert('로그인후 이용이 가능합니다.'); location.href='/member/loginMain.af';">온라인예약</button></li>
				 		<li><button class="btn" onclick="window.customerPop = new CustomerPopFn('/service/customer.af');">고객의 소리</button></li>
				 	</c:when>
				 	<c:otherwise>
				 		<li><button class="btn_logout btn" onclick="location.href='/member/logout.af';">로그아웃</button></li>
				 		<li><button class="btn_mypage btn" onclick="location.href='#/mypage/mypage.af?page=reserve';">마이페이지</button></li>
				 		<li><button class="btn_book btn" onclick="location.href='/reserve/newResStep.af';">온라인예약</button></li>
				 		<!-- <li><button class="btn_book btn" onclick="alert('서버 점검중입니다.');">온라인예약</button></li> -->
				 		<li><button class="btn" onclick="window.memberServicePop = new MemberServicePopFn('/service/member.af');">고객의 소리</button></li>
				 	</c:otherwise>
				 </c:choose>
				</ul>
				<ul class="mobileUtil">
					<!-- <li class="selectPoint">
						<input type="radio" class="selPointCode" name="m_branch_choice" id="m_area_ch01" value="POINT01" <c:if test="${POINT_CODE eq 'POINT01'}">checked</c:if>/>
						<label for="m_area_ch01">하남</label>
						<input type="radio" class="selPointCode" name="m_branch_choice" id="m_area_ch02" value="POINT03" <c:if test="${POINT_CODE eq 'POINT03'}">checked</c:if>/>
						<label for="m_area_ch02">고양</label>
					</li> -->
					<c:choose>
						<c:when test="${MEMINFO.MEM_UID eq ''}">
							<!-- <li><button class="btn_book btn" onclick="alert('로그인후 이용이 가능합니다.'); window.memberPop = new MemberPopFn();"><img class="ico" src="/common/front/images/btn/btn_m_res.png" alt="예약"></button></li> -->
							<li><button class="btn_book btn" onclick="alert('로그인후 이용이 가능합니다.'); location.href='/member/loginMain.af';"><img class="ico" src="/common/front/images/btn/btn_m_res.png" alt="예약"></button></li>
						</c:when>
						<c:otherwise>
							<!-- <li><button class="btn_book btn" onclick="window.reservationPop = new ReservationPopFn();"><img class="ico" src="/common/front/images/btn/btn_m_res.png"></button></li> -->
							<li><button class="btn_book btn" onclick="location.href='/reserve/newResStep.af'"><img class="ico" src="/common/front/images/btn/btn_m_res.png"></button></li>
						</c:otherwise>
					</c:choose>
					<li>
						<a href="javascript:void(0);" class="btn_sitemap"><img src="/common/front/images/btn/btn_m_menu.png" alt="메뉴"></a>
					</li>
				</ul>
			</div>
		</div>
		<div id="mainMenu">
			<div class="inner">
				<div id="menuSlider">
					<div class="menuItem">
						<div class="inner">
							<ul class="menu_list">
								<li><a href="#/about/concept/index.af"><span class="lang_en">Concept</span><span class="lang_ko">컨셉소개</span></a></li>
								<li><a href="#/about/location/index.af"><span class="lang_en">Location</span><span class="lang_ko">위치안내</span></a></li>
							</ul>
							<ul class="menu_content">
								<li>
									<div class="txt">
										<h2>컨셉소개</h2>
										<p>Feeling & Healing <br/>생각을 비우다, 여유를 채우다 <br/>아쿠아필드의 모토와 홍보영상을 만나 보실 수 있습니다. </p>
									</div>
									<div class="img">
										<img src="/common/front/images/${POINT_CODE}/common/menu_img_concept.jpg" alt="컨셉소개" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>위치안내</h2>
										<p>교통수단 별 이동경로 안내와 <br/>주차 진입로를 확인하실 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/${POINT_CODE}/common/menu_img_location.jpg" alt="위치안내" />
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="menuItem">
						<div class="inner">
							<c:if test="${POINT_CODE eq 'POINT01'}">
							<ul class="menu_list">
								<li><a href="#/facilities/index.af?depth1=2&depth2=2"><span class="lang_en">Jjimjil SPA</span><span class="lang_ko">찜질스파</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=1"><span class="lang_en">Indoor Water Park</span><span class="lang_ko">실내 워터파크</span></a></li>
								<li><a href="#/facilities/index.af?depth1=1"><span class="lang_en">Outdoor Water Park</span><span class="lang_ko">실외 워터파크</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=3"><span class="lang_en">Sauna</span><span class="lang_ko">사우나</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=4"><span class="lang_en">F&B</span><span class="lang_ko">푸드코트</span></a></li>
								<li><a href="#/facilities/index.af?depth1=3"><span class="lang_en">Additional Facilities</span><span class="lang_ko">기타시설</span></a></li>
							</ul>
							<ul class="menu_content">
								<li>
									<div class="txt">
										<h2>찜질스파</h2>
										<p>360도 파노라마 영상과 함께 온열찜질을 체험할 수 있는 미디어 아트룸, 고창 황토벽돌을 사용한 고온 불가마 등 아쿠아필드만의 특색 있는 찜질∙휴양 시설의 위치와 컨셉을 미리 만나볼 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/common/menu_img_spa.jpg" alt="찜질스파" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>실내 워터파크</h2>
										<p>유수풀 부터 어린이 풀까지, 남녀노소 모두 즐길 수 있는 <br/>실내 수영 시설을 미리 만나보세요.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/common/menu_img_inwater.jpg" alt="실내 워터파크" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>실외 워터파크</h2>
										<p>야외 전망을 한눈에 바라보며 즐길 수 있는 인피니티 풀과<br/> 자쿠지! 겨울에도 즐길 수 있는 야외 워터파크의 매력을 미리 느껴보세요.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/common/menu_img_outwater.jpg" alt="실외 워터파크" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>사우나</h2>
										<p>아쿠아필드를 찾아주시는 모든 고객을 위해 준비된 정갈하고 깨끗한 사우나 시설을 확인할 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/common/menu_img_sauna.jpg" alt="사우나" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>푸드코트</h2>
										<p>워터파크, 찜질스파 고객이 모두 이용할 수 있는 F&B 코너를 소개합니다.  신세계 푸드의 다양한 대표 메뉴가 입점하여 든든하고 맛있는 음식을 제공합니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/common/menu_img_fnb.jpg" alt="푸드코트" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>기타시설</h2>
										<p>결제, 물품대여, 시설 안내를 받을 수 있는 정산소 위치를 <br/>확인 할 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/common/menu_img_af.jpg" alt="기타시설" />
									</div>
								</li>
							</ul>
							</c:if>
							<c:if test="${POINT_CODE eq 'POINT03'}">
							<ul class="menu_list">
								<li><a href="#/facilities/index.af?depth1=2&depth2=1"><span class="lang_en">Jjimjil SPA</span><span class="lang_ko">찜질스파</span></a></li>
								<li><a href="#/facilities/index.af?depth1=1"><span class="lang_en">Rooftop Pool</span><span class="lang_ko">루프탑풀</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=3"><span class="lang_en">F&B</span><span class="lang_ko">푸드코트</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=2"><span class="lang_en">Sauna</span><span class="lang_ko">사우나</span></a></li>
								<!--<li><a href="#/facilities/index.af?depth1=2"><span class="lang_en">Additional Facilities</span><span class="lang_ko">기타시설</span></a></li> -->
							</ul>
							<ul class="menu_content">
								<li>
									<div class="txt">
										<h2>찜질스파</h2>
										<p>360도 파노라마 영상과 함께 온열찜질을 체험할 수 있는 미디어 아트룸, 고창 황토벽돌을 사용한 고온 불가마 등 아쿠아필드만의 특색 있는 찜질∙휴양 시설의 위치와 컨셉을 미리 만나볼 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT03/common/menu_img_spa.jpg" alt="찜질스파" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>루프탑풀</h2>
										<p>야외 전망을 한눈에 바라보며 즐길 수 있는 인피니티 풀과<br/> 자쿠지! 매력을 미리 느껴보세요.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT03/common/menu_img_outwater.jpg" alt="루프탑풀" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>푸드코트</h2>
										<p>루프탑풀, 찜질스파 고객이 모두 이용할 수 있는 F&B 코너를 소개합니다.  신세계 푸드의 다양한 대표 메뉴가 입점하여 든든하고 맛있는 음식을 제공합니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT03/common/menu_img_fnb.jpg" alt="푸드코트" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>사우나</h2>
										<p>아쿠아필드를 찾아주시는 모든 고객을 위해 준비된 정갈하고 깨끗한 사우나 시설을 확인할 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT03/common/menu_img_sauna.jpg" alt="사우나" />
									</div>
								</li>
								<!-- <li>
									<div class="txt">
										<h2>기타시설</h2>
										<p>결제, 물품대여, 시설 안내를 받을 수 있는 정산소 위치를 <br/>확인 할 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/common/menu_img_af.jpg" alt="기타시설"/>
									</div>
								</li> -->
							</ul>
							</c:if>
							<c:if test="${POINT_CODE eq 'POINT05'}">
							<ul class="menu_list">
								<li><a href="#/facilities/index.af?depth1=2&depth2=2"><span class="lang_en">Jjimjil SPA</span><span class="lang_ko">찜질스파</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=1"><span class="lang_en">Indoor Water Park</span><span class="lang_ko">실내 워터파크</span></a></li>
								<li><a href="#/facilities/index.af?depth1=1"><span class="lang_en">Outdoor Water Park</span><span class="lang_ko">야외 워터파크</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=3"><span class="lang_en">Sauna</span><span class="lang_ko">사우나</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=4"><span class="lang_en">F&B</span><span class="lang_ko">푸드코트</span></a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=5"><span class="lang_en">Additional Facilities</span><span class="lang_ko">기타시설</span></a></li>
							</ul>
							<ul class="menu_content">
								<li>
									<div class="txt">
										<h2>찜질스파</h2>
										<p>360도 파노라마 영상과 함께 온열찜질을 체험할 수 있는 미디어 아트룸, 고창 황토벽돌을 사용한 고온 불가마 등 아쿠아필드만의 특색 있는 찜질∙휴양 시설의 위치와 컨셉을 미리 만나볼 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT05/common/menu_img_spa.jpg" alt="찜질스파" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>실내 워터파크</h2>
										<p>유수풀 부터 어린이 풀까지, 남녀노소 모두 즐길 수 있는 <br/>실내 수영 시설을 미리 만나보세요.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT05/common/menu_img_inwater.jpg" alt="실내 워터파크" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>야외 워터파크</h2>
										<p>야외 전망을 한눈에 바라보며 즐길 수 있는 인피니티 풀과<br/> 자쿠지! 겨울에도 즐길 수 있는 야외 워터파크의 매력을 미리 느껴보세요.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT05/common/menu_img_outwater.jpg" alt="야외 워터파크" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>사우나</h2>
										<p>아쿠아필드를 찾아주시는 모든 고객을 위해 준비된 정갈하고 깨끗한 사우나 시설을 확인할 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT05/common/menu_img_sauna.jpg" alt="사우나" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>푸드코트</h2>
										<p>워터파크, 찜질스파 고객이 모두 이용할 수 있는 F&B 코너를 소개합니다.  신세계 푸드의 다양한 대표 메뉴가 입점하여 든든하고 맛있는 음식을 제공합니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT05/common/menu_img_fnb.jpg" alt="푸드코트" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>기타시설</h2>
										<p>결제, 물품대여, 시설 안내를 받을 수 있는 정산소 위치를 <br/>확인 할 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/POINT05/common/menu_img_af.jpg" alt="기타시설" />
									</div>
								</li>
							</ul>
							</c:if>							
						</div>
					</div>
					<div class="menuItem">
						<div class="inner">
							<ul class="menu_list">
								<li><a href="#/guide/index.af?page=1"><span class="lang_en">Use Info</span><span class="lang_ko">이용정보</span></a></li>
								<li><a href="#/guide/index.af?page=2"><span class="lang_en">Operational Policy</span><span class="lang_ko">운영정책</span></a></li>
								<li><a href="#/guide/index.af?page=3"><span class="lang_en">Charges</span><span class="lang_ko">정산안내</span></a></li>
								<li><a href="#/guide/index.af?page=4"><span class="lang_en">Rental</span><span class="lang_ko">렌탈안내</span></a></li>
								<li><a href="#/guide/index.af?page=5"><span class="lang_en">Sequence of Use</span><span class="lang_ko">이용순서</span></a></li>
							</ul>
							<ul class="menu_content">
								<li>
									<div class="txt">
										<h2>이용정보</h2>
										<p>운영시간 및 이용요금을 확인할 수 있습니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/${POINT_CODE}/common/menu_img_info.jpg" alt="이용정보" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>운영정책</h2>
										<p>반입금지 물품, 금지 행위 등 기본적인 이용정책을 <br/>확인 하실 수 있습니다. </p>
									</div>
									<div class="img">
										<img src="/common/front/images/${POINT_CODE}/common/menu_img_policy.jpg" alt="운영정책" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>정산안내</h2>
										<p>시설내 대금 결제와 정산을 관리하는 One-Key 시스템에 <br/>대한 안내사항 입니다. </p>
									</div>
									<div class="img">
										<img src="/common/front/images/${POINT_CODE}/common/menu_img_charges.jpg" alt="정산안내" />
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>렌탈안내</h2>
										<p>대여상품의 이용요금 정보와 대여장소를 <br/>확인 하실 수 있습니다. </p>
									</div>
									<div class="img">
										<img src="/common/front/images/${POINT_CODE}/common/menu_img_rental.jpg" alt="렌탈안내" />
									</div>
								</li>
								<!-- <li>
									<div class="txt">
										<h2>자주묻는 질문</h2>
										<p>고창 황토벽돌을 사용한 고온의 찜질공간으로, 아로마 수증기와 고온에 의해 몸 스스로 열을 발생시키고 땀을 다량 배출하게 하여 노폐물 제거와 혈액순환에 도움을 줍니다.</p>
									</div>
									<div class="img">
										<img src="/common/front/images/common/menu_img_spa.jpg"/>
									</div>
								</li> -->
								<li>
									<div class="txt">
										<h2>이용순서</h2>
										<p>입장부터 퇴장까지 아쿠아필드를 처음 방문해 주시는 <br/>고객님들을 위한 이용 순서를 안내해 드립니다.  </p>
									</div>
									<div class="img">
										<img src="/common/front/images/${POINT_CODE}/common/menu_img_seq.jpg" alt="이용순서" />
									</div>
								</li>
							</ul>
						</div>
					</div>
					<div class="menuItem">
						<div class="inner">
							<ul class="menu_list">
								<!-- <li><a href="#/event/eventIndex.af?cate=1"><span class="lang_en">Notice</span><span class="lang_ko">공지사항</span></a></li>
								<li><a href="#/event/eventIndex.af?cate=2"><span class="lang_en">Event</span><span class="lang_ko">이벤트</span></a></li> -->
								<!-- <li><a href="#/event/eventIndex.af?cate=3"><span class="lang_en">Media</span><span class="lang_ko">미디어</span></a></li> -->
								
								<c:if test="${POINT_CODE eq 'POINT01'}">
									<!-- <li><a href="/hanam/index.af#/event/eventIndex.af?cate=1";>공지사항</a></li>
									<li><a href="/hanam/index.af#/event/eventIndex.af?cate=2";>이벤트</a></li> -->
									<li><a href="javascript:point01(1)";>공지사항</a></li>
									<li><a href="javascript:point01(2)";>이벤트</a></li>
								</c:if>
								<c:if test="${POINT_CODE eq 'POINT03'}">
									<li><a href="javascript:point03(1)";>공지사항</a></li>
									<li><a href="javascript:point03(2)";>이벤트</a></li>
								</c:if>
								<c:if test="${POINT_CODE eq 'POINT05'}">
									<!-- <li><a href="/hanam/index.af#/event/eventIndex.af?cate=1";>공지사항</a></li>
									<li><a href="/hanam/index.af#/event/eventIndex.af?cate=2";>이벤트</a></li> -->
									<li><a href="javascript:point05(1)";>공지사항</a></li>
									<li><a href="javascript:point05(2)";>이벤트</a></li>
								</c:if>
								
							</ul>
							<ul class="menu_content">
								<li>
									<div class="txt">
										<h2>공지사항</h2>
										<p>아쿠아필드의 운영정보와 새로운 소식들을 만나보세요.</p>
									</div>
								</li>
								<li>
									<div class="txt">
										<h2>이벤트</h2>
										<p>다양한 할인정보와 이벤트 소식들을 확인할 수 있습니다.</p>
									</div>
								</li>
								<!-- <li>
									<div class="txt">
										<h2>미디어</h2>
										<p>외부매체에 노출된 아쿠아필드 관련 기사들을 <br/>만나볼 수 있습니다.</p>
									</div>
								</li> -->
							</ul>
						</div>
					</div>
				</div>
				<div class="navArea">
					<ul class="nav">
						<li>
							<a href="javascript:void(0);">
								<span class="lang_en">About</span>
								<span class="lang_ko">아쿠아필드 소개</span>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<span class="lang_en">Facilities</span>
								<span class="lang_ko">시설안내</span>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<span class="lang_en">Operation Guide</span>
								<span class="lang_ko">이용안내</span>
							</a>
						</li>
						<li>
							<a href="javascript:void(0);">
								<span class="lang_en">News</span>
								<span class="lang_ko">아쿠아필드 소식</span>
							</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="allMenu">
			<div class="inner">
				<div class="menuTop">
					<h1><img class="ico" src="/common/front/images/btn/btn_allmenu.png" alt="전체메뉴">전체메뉴</h1>
					<c:choose>
						<c:when test="${MEMINFO.MEM_UID eq ''}">
							<div>
								<!-- <a href="javascript:void(0);" onclick="window.memberPop = new MemberPopFn();" class="login"> -->
								<a href="javascript:void(0);" onclick="location='/member/loginMain.af';" class="login">
									<span class="icon"><img src="/common/front/images/ico/ico_m_login.png" alt="LOGIN">
									</span> LOGIN
								</a>
								<span class="line">|</span>
								<a href="javascript:void(0);" onclick="window.customerPop = new CustomerPopFn('/service/customer.af');" class="customerBtn">
									고객의 소리
								</a>
							</div>
						</c:when>
					 	<c:otherwise>
							<div>
								<span class="name">${MEMINFO.MEM_NM} 님</span>
								<a href="javascript:void(0);" onclick="location.href='/member/logout.af';" class="logout">LOGOUT</a>
								<span class="line">|</span>
								<a href="javascript:void(0);" onclick="window.memberServicePop = new MemberServicePopFn('/service/member.af');" class="customerBtn">고객의 소리</a>
							</div>
						</c:otherwise>
					</c:choose>
					<button class="btn_close"></button>
				</div>
				<c:choose>
					<c:when test="${MEMINFO.MEM_UID eq ''}"></c:when>
					<c:otherwise>
						<ul class="mypageMenu">
							<li>
								<a href="#/mypage/mypage.af?page=reserve">
									<img src="/common/front/images/ico/ico_m_resifo.png">
									<p>예약정보</p>
								</a>
							</li>
							<li>
								<a href="#/mypage/mypage.af?page=cs">
									<img src="/common/front/images/ico/ico_m_cs.png">
									<p>1:1 문의</p>
								</a>
							</li>
							<li>
								<a href="#/mypage/mypage.af?page=pwd">
									<img src="/common/front/images/ico/ico_m_myinfo.png">
									<p>내 정보</p>
								</a>
							</li>
						</ul>
					</c:otherwise>
				</c:choose>
				<div class="menuList">
					<ul>
						<li>
							<a href="javascript:void(0);"><span><strong>A</strong>bout</span></a>
							<ul>
								<li><a href="#/about/concept/index.af">컨셉소개</a></li>
								<li><a href="#/about/location/index.af">위치안내</a></li>
							</ul>
						</li>
						<li>
							<a href="javascript:void(0);"><span><strong>F</strong>acilities</span></a>
							<c:if test="${POINT_CODE eq 'POINT01'}">
							<ul>
								<li><a href="#/facilities/index.af?depth1=2&depth2=2">찜질스파</a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=1">실내 워터파크</a></li>
								<li><a href="#/facilities/index.af?depth1=1">야외 워터파크</a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=3">사우나</a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=4">푸드코트</a></li>
								<li><a href="#/facilities/index.af?depth1=3">기타시설</a></li>
							</ul>
							</c:if>
							<c:if test="${POINT_CODE eq 'POINT03'}">
							<ul>
								<li><a href="#/facilities/index.af?depth1=2&depth2=1">찜질스파</a></li>
								<li><a href="#/facilities/index.af?depth1=1">루프탑풀</a></li>
								<li><a href="#/facilities/index.af?depth1=2">푸드코트</a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=2">사우나</a></li>
								<!-- <li><a href="#/facilities/index.af?depth1=2">기타시설</a></li> -->
							</ul>
							</c:if>
							<c:if test="${POINT_CODE eq 'POINT05'}">
							<ul>
								<li><a href="#/facilities/index.af?depth1=2&depth2=2">찜질스파</a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=1">실내 워터파크</a></li>
								<li><a href="#/facilities/index.af?depth1=1">야외 워터파크</a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=3">사우나</a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=4">푸드코트</a></li>
								<li><a href="#/facilities/index.af?depth1=2&depth2=5">기타시설</a></li>
							</ul>
							</c:if>
						</li>
						<li>
							<a href="javascript:void(0);"><span><strong>O</strong>peration Guide</span></a>
							<ul>
								<li><a href="#/guide/index.af?page=1">이용정보</a></li>
								<li><a href="#/guide/index.af?page=2">운영정책</a></li>
								<li><a href="#/guide/index.af?page=3">정산안내</a></li>
								<li><a href="#/guide/index.af?page=4">렌탈안내</a></li>
								<li><a href="#/guide/index.af?page=5">이용순서</a></li>
							</ul>
						</li>
						<li>
							<a href="javascript:void(0);"><span><strong>N</strong>ews</span></a>
							<ul>
								<li style="display: none;"><a href="#/event/eventIndex.af?cate=0">전체보기</a></li>
								<!-- <li><a href="#/event/eventIndex.af?cate=1">공지사항</a></li>
								<li><a href="#/event/eventIndex.af?cate=2">이벤트</a></li> -->
								<!-- <li><a href="#/event/eventIndex.af?cate=3">미디어</a></li> -->
								
								<c:if test="${POINT_CODE eq 'POINT01'}">
									<!-- <li><a href="/hanam/index.af#/event/eventIndex.af?cate=1";>공지사항</a></li>
									<li><a href="/hanam/index.af#/event/eventIndex.af?cate=2";>이벤트</a></li> -->
									<li><a href="javascript:point01(1)";>공지사항</a></li>
									<li><a href="javascript:point01(2)";>이벤트</a></li>
								</c:if>
								<c:if test="${POINT_CODE eq 'POINT03'}">
									<li><a href="javascript:point03(1)";>공지사항</a></li>
									<li><a href="javascript:point03(2)";>이벤트</a></li>
								</c:if>
								<c:if test="${POINT_CODE eq 'POINT05'}">
									<!-- <li><a href="/hanam/index.af#/event/eventIndex.af?cate=1";>공지사항</a></li>
									<li><a href="/hanam/index.af#/event/eventIndex.af?cate=2";>이벤트</a></li> -->
									<li><a href="javascript:point01(1)";>공지사항</a></li>
									<li><a href="javascript:point01(2)";>이벤트</a></li>
								</c:if>
							</ul>
						</li>
						<li class="sns">
							<ul>
								<li>
									<img src="/common/front/images/ico/ico_sns_kas_off.png" class="ico" alt="카카오 플러스 친구">
									<a href="http://pf.kakao.com/_uankxl" target="_blank">
										카카오 플러스 친구
									</a>
								</li>
								<li><img src="/common/front/images/ico/ico_sns_ins_off.png" class="ico" alt="인스타그램"><a href="https://www.instagram.com/aquafield.official/" target="_blank">인스타그램</a></li>
							</ul>
						</li>
					</ul>
					<div class="bg"></div>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="pointUrl" name="pointUrl" value="${POINT_URL}"/>
</header>
<script type="text/javascript">
/*
function preparePopup(){
	var pop_top 	= (screen.availHeight - 440) / 2;
	var pop_left 	= (screen.availWidth - 430) / 2;
	pop_top 		= pop_top;

	//  아쿠아필드 고양점의 온라인예약 상품은 현재 준비 중입니다. 이용에 불편을 드려서 죄송 합니다. 감사 합니다.
	window.open("/common/front/popup_171025.jsp", "온라인 예약 공지",
			"scrollbars=no, width=450,  height=528, toolbar=no, enubar=no,
			directories=no, status=no, resizeable=no, location=no, top="+pop_top+", left=500");
}
 */
var reserveNotiPop = function(){
	var pop_top 	= (screen.availHeight - 440) / 2;
	var pop_left 	= (screen.availWidth - 430) / 2;
	pop_top 		= pop_top;

	//  온라인 예약 임시 중단 안내
	window.open("/common/front/popup_170828.jsp", "온라인 예약 공지", "scrollbars=no, width=450,  height=528, toolbar=no, enubar=no, directories=no, status=no, resizeable=no, location=no, top="+pop_top+", left=500");
};

$.urlParam = function(name){
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results==null){
       return null;
    }
    else{
       return decodeURI(results[1]) || 0;
    }
}

function replaceUrlParam(url, paramName, paramValue){
    if(paramValue == null)
        paramValue = '';
    var pattern = new RegExp('\\b('+paramName+'=).*?(&|$)')
    if(url.search(pattern)>=0){
        return url.replace(pattern,'$1' + paramValue + '$2');
    }
    return url + (url.indexOf('?')>0 ? '&' : '?') + paramName + '=' + paramValue
}

//메인페이지 지점 변경시 지점세션 변경
$(".radioPoint").change(function(){
	var pointCode = $(this).val();
	var pointUrl = $("input[name="+pointCode+"]").val();
	$.ajax({
		type: "POST"
		,url : "/ajaxPointCodeSet.af"
		,data : {'pointCode':pointCode, 'pointUrl':pointUrl}
		,dataType : "html"
		,success: function(obj){
			//하남지점과 고양지점이 시설이 달라 depth 구조가 상이하여 고양지점에 없는 부분 고양지점으로 맞춰 url 전송
			/* if(pointCode == "POINT03"){
				if($(location).attr("href").indexOf("/facilities") != -1){
					if($.urlParam('depth1') != null){
						switch ($.urlParam('depth1')){
							case "2":
								if($.urlParam('depth2') != null){
									var depth2 = $.urlParam('depth2')
									if(depth2 == "3" || depth2 == "4" || depth2 == "5"){
										window.location.href = replaceUrlParam($(location).attr('href'),"depth2","2");
									}
								}
								break;
							case "3":
								window.location.href = replaceUrlParam($(location).attr('href'),"depth1","2");
								break;
						}
					}
				}
			}

			if($(location).attr("href").indexOf("/event/eventIndex.af") != -1){
				if($.urlParam('page') != null){
					window.location.href = replaceUrlParam($(location).attr('href'),"page","1");
				}
			} */

			//지점 변경시 메인페이지로 이동
			window.location.href = $(location).attr("protocol") + "//" + $(location).attr("host") + pointUrl;
		}
		,error: function(xhr, option, error){
			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
		}
	});
});
</script>

