<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>사용기록</h2>
				</div>
			</div>
			<div class="tb_type2">
				<h3>조건검색</h3>
				<table>
					<tbody>
						<tr>
							<th>이	름</th>
							<td><input type="text" name="" class="ipt2 w100p"/></td>
							<th>접근메뉴</th>
							<td>
								<select name="" id="" class="customized-select">
									<option value="0">전체</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>행	위</th>
							<td>
								<select name="" id="" class="customized-select">
									<option value="0">전체</option>
								</select>
							</td>
							<th>일자선택</th>
							<td><input type="" name="" class="ipt2 datepicker"/> ~ <input type="" name="" class="ipt2 datepicker"/></td>
						</tr>
						<tr>
							<th colspan="4">
								<div class="tb_btn_area center">
									<button class="btn_pack btn_mo d_gray">Search</button>
								</div>
							</th>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="tb_type1">
				<h3>검색결과</h3>
				<div class="result_summary">Total : <strong>773</strong></div>
				<table>
				 	<colgroup>
						<col width="10%">
						<col width="15%">
						<col>
						<col>
						<col width="10%">
						<col width="10%">
						<col width="10%">
						<col width="10%">
					</colgroup>
				 	<thead>
				 		<tr>
							<th>이	름</th>
							<th>부	서</th>
							<th>ID</th>
							<th>접근메뉴</th>
							<th>행	위</th>
							<th>자료번호</th>
							<th>IP</th>
							<th>시	간</th>
						</tr>
				 	</thead>
					<tbody>
						<tr>
							<td>박길동</td>
							<td class="left">영업관리팀</td>
							<td class="left">pgildong@daim.net</td>
							<td>관리자정보</td>
							<td>열람</td>
							<td class="right"></td>
							<td>124.05.123.1</td>
							<td>2015.07.24<br/>12:32:00</td>
						</tr>
						<tr>
							<td>홍길동</td>
							<td class="left">아쿠아본사</td>
							<td class="left">hgildong@daim.net</td>
							<td>관리자정보</td>
							<td>삭제</td>
							<td class="right"><a href="#" class="link">12</a></td>
							<td>124.05.123.1</td>
							<td>2015.07.24<br/>12:32:00</td>
						</tr>
						<tr>
							<td>김길동</td>
							<td class="left">아쿠아본사</td>
							<td class="left">kgildong@daim.net</td>
							<td>관리자정보</td>
							<td>생성</td>
							<td class="right"><a href="#" class="link">35</a></td>
							<td>124.05.123.1</td>
							<td>2015.07.24<br/>12:32:00</td>
						</tr>
						<tr>
							<td>박길동</td>
							<td class="left">㈜ 스타벅스</td>
							<td class="left">pgildong1@daim.net</td>
							<td>회원정보</td>
							<td>수정</td>
							<td class="right"><a href="#" class="link">34531</a></td>
							<td>124.05.123.1</td>
							<td>2015.07.24<br/>12:32:00</td>
						</tr>
						<tr>
							<td>홍길동</td>
							<td class="left">㈜ 삼성물산</td>
							<td class="left">hgildong2@daim.net</td>
							<td>예약내역 관리</td>
							<td>열람</td>
							<td class="right"><a href="#" class="link">123452</a></td>
							<td>124.05.123.1</td>
							<td>2015.07.24<br/>12:32:00</td>
						</tr>
						<tr>
							<td>길길동</td>
							<td class="left">아쿠아 경영지원팀</td>
							<td class="left">hgildong@daim.net</td>
							<td>상품관리</td>
							<td>수정</td>
							<td class="right"></td>
							<td>124.05.123.1</td>
							<td>2015.07.24<br/>12:32:00</td>
						</tr>
						<tr>
							<td>박길동</td>
							<td class="left">아쿠아 프론트</td>
							<td class="left">pgildong4@daim.net</td>
							<td>고객문의</td>
							<td>생성</td>
							<td class="right"><a href="#" class="link">12234</a></td>
							<td>124.05.123.1</td>
							<td>2015.07.24<br/>12:32:00</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="paginate">
				<a href="#" class="btn prev disable">PREV</a>
				<a href="#" class="num on">1</a>
				<a href="#" class="num">2</a>
				<a href="#" class="num">3</a>
				<a href="#" class="num">4</a>
				<a href="#" class="num">5</a>
				<a href="#" class="num">6</a>
				<a href="#" class="num">7</a>
				<a href="#" class="num">8</a>
				<a href="#" class="num">9</a>
				<a href="#" class="num">10</a>
				<a href="#" class="btn next">NEXT</a>
			</div>
		</div>
	</div>
</section>
<script type="text/javascript">
	
</script>
