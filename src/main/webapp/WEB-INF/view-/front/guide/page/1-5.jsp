<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp" %>
<section id="guide1">
    <header>
    	<h1>이용정보</h1>
    </header>
    <div class="iscContent">
    	<div class="inner">
    		<article>
    			<div class="row">
                    <h2>이용시간</h2>
                    <div class="row usetime">
                        <div class="col s6">
                            <img src="/common/front/images/guide/guide1_water_bg.jpg">
                            <div class="side_box side_box1">
	                            <div class="inner">
	                            	<h3 class="title">워터파크</h3>
	                            	<ul class="cnt">
	                            		<li>am 10:00 ~ pm 07:00(실내)</li>
	                            		<li>am 11:00 ~ pm 06:00(실외)</li>
	                            	</ul>
	                            </div>
                            </div>
                        </div>
                        <div class="col s6">
                            <img src="/common/front/images/guide/guide1_spa_bg.jpg">
                            <div class="side_box side_box2">
                            	<div class="inner">
	                            	<h3 class="title">찜질스파</h3>
	                            	<ul class="cnt">
	                            		<li>am 06:00 ~ am 12:00</li>
	                            	</ul>
                            	</div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <h2>이용요금</h2>
                    <div class="tb_type1">
                        <table>
                            <colgroup>
                                <col width="18%">
                                <col width="20.5%">
                                <col width="20.5%">
                                <col width="20.5%">
                                <col width="20.5%">
                            </colgroup>
                            <thead>
                                <tr>
                                    <th rowspan="2">구분</th>
                                    <th colspan="2">찜질스파</th>
                                    <th rowspan="2">워터파크</th>
                                </tr>
                                <tr>
                                    <th>주중</th>
                                    <th>주말/공휴일</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td class="center">대인</td>
                                    <td class="center">20,000원</td>
                                    <td class="center">22,000원</td>
                                    <td class="center">38,000원</td>
                                </tr>
                                <tr>
                                    <td class="center">소인</td>
                                    <td class="center">16,000원</td>
                                    <td class="center">18,000원</td>
                                    <td class="center">30,000원</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <ul class="notice">
                    	<li class="c-red">대인 : 만 13세 이상 / 소인 : 36개월 ~ 만 12 세</li>
                        <li>입장 후 최대 6시간까지 이용가능하며, 이용시간 초과 시 시간당 5,000원의 추가 요금을 받습니다. (찜질스파, 워터파크 동일)</li>
                        <li>36개월 미만의 유아는 무료입장이 가능합니다.</li>
                        <li>국가유공자 및 장애인은 본인 및 동반 1인까지 20% 할인 됩니다.</li>
                        <li>티켓 발권 후 20분이내 신발장을 이용해 주십시오.(미이용시 신발장이 자동으로 잠깁니다)</li>
                        <li>모든 할인은 중복적용이 불가 합니다.</li>
                    </ul>
                    <div class="notice_caption">유아, 소인, 국가유공자, 장애인 입장 시 반드시 증빙서류를 지참 하셔야 합니다.</div>
                </div>
    		</article>
    	</div>
    </div>
    <!-- <footer></footer> -->
    <script type="text/javascript">
        $('.wordBraek').wordBreakKeepAll();
    </script>
    <style type="text/css">
        #guide1 .tb_type1 table, #guide1 .tb_type1 table td { padding: 10px 5px; }

        @media screen and (max-width:767px){
            #guide1 .tb_type1 > table > thead > tr:last-child th { font-size: 1rem; }
        }
    </style>
</section>