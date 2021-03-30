<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<section id="contents">
	<div class="contents_bx_type1">
		<div class="contents_bx_inner">
			<div id="path" class="fixed">
				<div class="pathInner">
					<h2>운영관리자</h2>
					<div class="btns_area">
						<a href="/secu_admaf/admin/manager/operations_manager_write.jsp" class="btn_pack btn_mo d_gray"><img src="/admaf/images/common/ico_add.png">생성</a>
					</div>
				</div>
			</div>
			<div class="tb_type2">
				<h3>조건검색</h3>
				<table>
					<tbody>
						<tr>
							<th>이	름</th>
							<td><input type="text" name="" class="ipt2 w100p"/></td>
							<th>등록일</th>
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
						<col width="30%">
						<col width="15%">
						<col width="15%">
						<col width="10%">
					</colgroup>
				 	<thead>
				 		<tr>
							<th>이	름</th>
							<th>부	서</th>
							<th>E-Mail</th>
							<th>등록일</th>
							<th>최종접속일</th>
							<th>관	리</th>
						</tr>
				 	</thead>
					<tbody>
						<tr>
							<td>박길동</td>
							<td class="left">영업관리팀</td>
							<td class="left">pgildong@daim.net</td>
							<td>2015.07.24</td>
							<td>2015.07.24</td>
							<td><a href="/secu_admaf/admin/manager/operations_manager_view.jsp" class="link">보기</a></td>
						</tr>
						<tr>
							<td>홍길동</td>
							<td class="left">아쿠아본사</td>
							<td class="left">hgildong@daim.net</td>
							<td>2015.07.24</td>
							<td>2015.07.24</td>
							<td><a href="/secu_admaf/admin/manager/operations_manager_view.jsp" class="link">보기</a></td>
						</tr>
						<tr>
							<td>김길동</td>
							<td class="left">아쿠아 프론트</td>
							<td class="left">kgildong@daim.net</td>
							<td>2015.07.24</td>
							<td>2015.07.24</td>
							<td><a href="/secu_admaf/admin/manager/operations_manager_view.jsp" class="link">보기</a></td>
						</tr>
						<tr>
							<td>박길동</td>
							<td class="left">㈜ 스타벅스</td>
							<td class="left">pgildong1@daim.net</td>
							<td>2015.07.24</td>
							<td>2015.07.24</td>
							<td><a href="/secu_admaf/admin/manager/operations_manager_view.jsp" class="link">보기</a></td>
						</tr>
						<tr>
							<td>홍길동</td>
							<td class="left">㈜ 삼성물산</td>
							<td class="left">hgildong2@daim.net</td>
							<td>2015.07.24</td>
							<td>2015.07.24</td>
							<td><a href="/secu_admaf/admin/manager/operations_manager_view.jsp" class="link">보기</a></td>
						</tr>
						<tr>
							<td>김길동</td>
							<td class="left">아쿠아 경영지원팀</td>
							<td class="left">kgildong3@daim.net</td>
							<td>2015.07.24</td>
							<td>2015.07.24</td>
							<td><a href="/secu_admaf/admin/manager/operations_manager_view.jsp" class="link">보기</a></td>
						</tr>
						<tr>
							<td>박길동</td>
							<td class="left">아쿠아 프론트</td>
							<td class="left">pgildong1@daim.net</td>
							<td>2015.07.24</td>
							<td>2015.07.24</td>
							<td><a href="/secu_admaf/admin/manager/operations_manager_view.jsp" class="link">보기</a></td>
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
