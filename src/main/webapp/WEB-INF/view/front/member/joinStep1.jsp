<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
	
	<div id="login_wrap" class="wrap_line">
	   	<div class="inner">
			<h2>회원가입</h2>
        	<div class="login_box">
				<div class="content on" style="left: 0%;">
					<section id="bx_signup_step">
					    <div class="con_type1">
					        <div class="signup_step">
					            <ul>
					                <li class="on"><div><img src="../common/front/images/ico/ico_signup_step1.png" alt="약관동의"></div><span>약관동의</span></li>
					                <li><div><img src="../common/front/images/ico/ico_signup_step2.png" alt="필수정보"></div><span>정보입력</span></li>
					                <li><div><img src="../common/front/images/ico/ico_signup_step4.png" alt="가입완료"></div><span>가입완료</span></li>
					            </ul>
					        </div>
							<span class="necessary" style="color: #D96C77; float: right;">* 필수항목</span>
					        <form id="step01" name="step01" action="" method="post">
								<input type="hidden" name="obj" value="">
								<input type="hidden" name="objName" value="">
								<input type="hidden" name="objGender" value="">
								<input type="hidden" name="objBirth" value="">
								<input type="hidden" name="objMNum" value="">
						   		<input type="hidden" name="point" value="POINT01">
					            <div class="area_terms">
					                <div class="bx_term">
					                    <h3 class="chk_motion lst_check3">
					                        <span class="" style="color: #D96C77;">*</span><span>홈페이지 이용약관</span>
					                        <label class="on"><span>동의합니다</span>
												<input type="checkbox" name="chkTerm" value="Y">
												<span class="bx_chk"></span>
											</label>
					                    </h3>
					                    <div class="detail">
	                        				<div class="in" style="text-align:left;">
												<p1>제 1조 (목적)</p1>
												<p class="last">이 약관은 ㈜신세계건설(이하 "회사")가 제공하는 아쿠아필드 웹사이트 관련 제반 서비스의 이용과 관련하여 회사와 회원과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.</p>
												<p1>제 2조 (정의)</p1>
												<p>이 약관에서 사용하는 용어의 정의는 다음과 같습니다.</p>
												<ol class="last">
													<li>"서비스"라 함은 구현되는 단말기(PC, TV, 휴대형단말기 등의 각종 유무선 장치를 포함)와 상관없이 "회원"이 이용할 수 있는 아쿠아필드 웹사이트 관련 서비스를 의미합니다.</li>
													<li>"회원"이라 함은 회사의 "서비스"에 접속하여 이 약관에 따라 "회사"와 이용계약을 체결하고 "회사"가 제공하는 "서비스"를 이용하는 고객을 말합니다.</li>
													<li>"아이디(ID)"라 함은 "회원"의 식별과 "서비스" 이용을 위하여 "회원"이 정하고 "회사"가 승인하는 이메일 형식의 문자와 숫자의 조합을 의미합니다.</li>
													<li>"비밀번호"라 함은 "회원"이 부여 받은 "아이디와 일치되는 "회원"임을 확인하고 비밀보호를 위해 "회원" 자신이 정한 문자 또는 숫자의 조합을 의미합니다.</li>
													<li>“온라인 회원”이라 함은 “회원”이 아쿠아필드 홈페이지에서정상적인 회원가입을 거쳐 온라인예약/결제 활동을 할 수 있는 회원을 의미합니다.</li>
													<li>"게시물"이라 함은 "회원"이 "서비스"를 이용함에 있어 "서비스상"에 게시한 부호ㆍ문자ㆍ음성ㆍ음향ㆍ화상ㆍ동영상 등의 정보 형태의 글, 사진, 동영상 및 각종 파일과 링크 등을 의미합니다. </li>
												</ol>

												<p1>제 3조 (약관의 게시와 개정)</p1>
												<ol class="last">
													<li>"회사"는 이 약관의 내용을 "회원"이 쉽게 알 수 있도록 서비스 초기 화면에 게시합니다.</li>
													<li>"회사"는 "약관의규제에관한법률", "정보통신망이용촉진및정보보호등에관한법률(이하 "정보통신망법")" 등 관련법을 위배하지 않는 범위에서 이 약관을 개정할 수 있습니다.</li>
													<li>"회사"가 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 제1항의 방식에 따라 그 개정약관의 적용일자 30일 전부터 적용일자 전일까지 공지합니다. 다만, 회원에게 불리한 약관의 개정의 경우에는 공지 외에 일정기간 서비스 내 전자우편, 로그인시 동의창 등의 전자적 수단을 통해 따로 명확히 통지하도록 합니다.</li>
													<li>회사가 전항에 따라 개정약관을 공지 또는 통지하면서 회원에게 30일 기간 내에 의사표시를 하지 않으면 의사표시가 표명된 것으로 본다는 뜻을 명확하게 공지 또는 통지하였음에도회원이 명시적으로 거부의 의사표시를 하지 아니한 경우 회원이 개정약관에 동의한 것으로 봅니다.</li>
													<li>회원이 개정약관의 적용에 동의하지 않는 경우 회사는 개정 약관의 내용을 적용할 수 없으며, 이 경우 회원은 이용계약을 해지할 수 있습니다. 다만, 기존 약관을 적용할 수 없는 특별한 사정이 있는 경우에는 회사는 이용계약을 해지할 수 있습니다.</li>
												</ol>

												<p1>제 4 조 (약관의 해석) </p1>
												<ol class="last">
													<li>"회사"는 "오프라인구매"에 대해서는 별도의 이용정책을 둘 수 있으며, 온라인 예약과 관련하여 현재 약관에 명시된 사항을 적용합니다.</li>
													<li>이 약관에서 정하지 아니한 사항이나 해석에 대해서는 시설이용에 관한 정책 및 관계법령 또는 상관례에 따릅니다.</li>
												</ol>

												<p1>제 5 조 (이용계약 체결) </p1>
												<ol class="last">
													<li>이용계약은 "회원"이 되고자 하는 자(이하 "가입신청자")가 약관의 내용에 대하여 동의를 한 다음 회원가입신청을 하고 "회사"가 이러한 신청에 대하여 승낙함으로써 체결됩니다.</li>
													<li>"회사"는 "가입신청자"의 신청에 대하여 "서비스" 이용을 승낙함을 원칙으로 합니다. 다만, "회사"는 다음 각 호에 해당하는 신청에 대하여는 승낙을 하지 않거나 사후에 이용계약을 해지할 수 있습니다.</li>
													<li>가입신청자가 이 약관에 의하여 이전에 회원자격을 상실한 적이 있는 경우, 다만, 회원자격 상실 후 3개월이 경과한 자로서 "회사"의 회원 재가입 승낙을 얻은 경우에는 예외로 함.</li>
													<li>실명이 아니거나 타인의 명의를 이용한 경우 </li>
													<li>허위의 정보를 기재하거나, "회사"가 제시하는 내용을 기재하지 않은 경우 </li>
													<li>이용자의 귀책사유로 인하여 승인이 불가능하거나 기타 규정한 제반 사항을 위반하며 신청하는 경우 </li>
													<li>제1항에 따른 신청에 있어 "회사"는 "회원"의 종류에 따라 전문기관을 통한 실명확인 및 본인인증을 요청할 수 있습니다. </li>
													<li>"회사"는 서비스관련 설비의 여유가 없거나, 기술상 또는 업무상 문제가 있는 경우에는 승낙을 유보할 수 있습니다. </li>
													<li>제2항과 제4항에 따라 회원가입신청의 승낙을 하지 아니하거나 유보한 경우, "회사"는 원칙적으로 이를 가입신청자에게 알리도록 합니다. </li>
													<li>이용계약의 성립 시기는 "회사"가 가입완료를 신청절차 상에서 표시한 시점으로 합니다. </li>
													<li>"회사"는 "회원"에 대해 회사정책에 따라 등급별로 구분하여 이용시간, 이용횟수, 서비스 메뉴 등을 세분하여 이용에 차등을 둘 수 있습니다.</li>
												</ol>

												<p1>제 6 조 (회원정보의 변경)  </p1>
												<ol class="last">
													<li>"회원"은 개인정보관리화면을 통하여 언제든지 본인의 개인정보를 열람하고 수정할 수 있습니다. 다만, 서비스 관리를 위해 필요한 실명, 주민등록번호, 아이디 등은 수정이 불가능합니다. </li>
													<li>"회원"은 회원가입신청 시 기재한 사항이 변경되었을 경우 온라인으로 수정을 하거나 전자우편 기타 방법으로 "회사"에 대하여 그 변경사항을 알려야 합니다.  </li>
													<li>제2항의 변경사항을 "회사"에 알리지 않아 발생한 불이익에 대하여 "회사"는 책임지지 않습니다.</li>
												</ol>

												<p1>제 7 조 (개인정보보호 의무) </p1>
													<p class="last">
														"회사"는 "정보통신망법" 등 관계 법령이 정하는 바에 따라 "회원"의 개인정보를 보호하기 위해 노력합니다. 개인정보의 보호 및 사용에 대해서는 관련법 및 "회사"의 개인정보취급방침이 적용됩니다.
													</p>


												<p1>제 8 조 ("회원"의 "아이디" 및 "비밀번호"의 관리에 대한 의무)  </p1>
												<ol class="last">
													<li>"회원"의 "아이디"와 "비밀번호"에 관한 관리책임은 "회원"에게 있으며, 이를 제3자가 이용하도록 하여서는 안 됩니다.</li>
													<li>"회사"는 "회원"의 "아이디"가 개인정보 유출 우려가 있거나, 반사회적 또는 미풍양속에 어긋나거나 "회사" 및 "회사"의 운영자로 오인할 우려가 있는 경우, 해당 "아이디"의 이용을 제한할 수 있습니다.  </li>
													<li>"회원"은 "아이디" 및 "비밀번호"가 도용되거나 제3자가 사용하고 있음을 인지한 경우에는 이를 즉시 "회사"에 통지하고 "회사"의 안내에 따라야 합니다. </li>
													<li>제3항의 경우에 해당 "회원"이 "회사"에 그 사실을 통지하지 않거나, 통지한 경우에도 "회사"의 안내에 따르지 않아 발생한 불이익에 대하여 "회사"는 책임지지 않습니다.</li>
												</ol>

												<p1>제 9 조 ("회원"에 대한 통지)</p1>
												<ol class="last">
													<li>"회사"가 "회원"에 대한 통지를 하는 경우 이 약관에 별도 규정이 없는 한 서비스 내 전자우편주소, 전자쪽지 등으로 할 수 있습니다. </li>
													<li>"회사"는 "회원" 전체에 대한 통지의 경우 7일 이상 "회사"의 게시판에 게시함으로써 제1항의 통지에 갈음할 수 있습니다.</li>
												</ol>

												<p1>제 10 조 ("회사"의 의무) </p1>
												<ol class="last">
													<li>"회사"는 관련법과 이 약관이 금지하거나 미풍양속에 반하는 행위를 하지 않으며, 계속적이고 안정적으로 "서비스"를 제공하기 위하여 최선을 다하여 노력합니다.</li>
													<li>"회사"는 "회원"이 안전하게 "서비스"를 이용할 수 있도록 개인정보(신용정보 포함)보호를 위해 보안시스템을 갖추어야 하며 개인정보취급방침을 공시하고 준수합니다. </li>
													<li>"회사"는 서비스이용과 관련하여 "회원"으로부터 제기된 의견이나 불만이 정당하다고 인정할 경우에는 이를 처리하여야 합니다. "회원"이 제기한 의견이나 불만사항에 대해서는 게시판을 활용하거나 전자우편 등을 통하여 "회원"에게 처리과정 및 결과를 전달합니다.</li>
												</ol>


												<p1>제 11 조 ("회원"의 의무)  </p1>
												<p>"회원"은 다음 행위를 하여서는 안 됩니다. </p>
												<ol class="last">
													<li>신청 또는 변경 시 허위내용의 등록 </li>
													<li>타인의 정보도용 </li>
													<li>"회사"가 게시한 정보의 변경 </li>
													<li>"회사"가 정한 정보 이외의 정보(컴퓨터 프로그램 등) 등의 송신 또는 게시 </li>
													<li>"회사"와 기타 제3자의 저작권 등 지적재산권에 대한 침해 </li>
													<li>"회사" 및 기타 제3자의 명예를 손상시키거나 업무를 방해하는 행위 </li>
													<li>외설 또는 폭력적인 메시지, 화상, 음성, 기타 공서양속에 반하는 정보를 "서비스"에 공개 또는 게시하는 행위 </li>
													<li>회사의 동의 없이 영리를 목적으로 "서비스"를 사용하는 행위 </li>
													<li>기타 불법적이거나 부당한 행위 </li>
													<li>"회원"은 관계법, 이 약관의 규정, 이용안내 및 "서비스"와 관련하여 공지한 주의사항, "회사"가 통지하는 사항 등을 준수하여야 하며, 기타 "회사"의 업무에 방해되는 행위를 하여서는 안 됩니다.</li>
												</ol>

												<p1>제 12 조 ("서비스"의 제공 등) </p1>
												<ol class="last">
													<li>
														회사는 회원에게 아래와 같은 서비스를 제공합니다.
														<ul style="margin-left:10px;">
															<li>①	아쿠아필드소개 서비스</li>
															<li>②	시설 이용안내 서비스</li>
															<li>③	1:1 문의 서비스</li>
															<li>④	온라인예약 서비스 </li>
															<li>⑤	기타 "회사"가 추가 개발하거나 다른 회사와의 제휴계약 등을 통해 "회원"에게 제공하는 일체의 서비스 </li>
														</ul>
													</li>
													<li>회사는 "서비스"를 일정범위로 분할하여 각 범위 별로 이용가능시간을 별도로 지정할 수 있습니다. 다만, 이러한 경우에는 그 내용을 사전에 공지합니다.  </li>
													<li>"서비스"는 연중무휴, 1일 24시간 제공함을 원칙으로 합니다.  </li>
													<li>. "회사"는 컴퓨터 등 정보통신설비의 보수점검, 교체 및 고장, 통신두절 또는 운영상 상당한 이유가 있는 경우 "서비스"의 제공을 일시적으로 중단할 수 있습니다. 이 경우 "회사"는 제9조["회원"에 대한 통지]에 정한 방법으로 "회원"에게 통지합니다. 다만, "회사"가 사전에 통지할 수 없는 부득이한 사유가 있는 경우 사후에 통지할 수 있습니다.</li>
													<li>"회사"는 서비스의 제공에 필요한 경우 정기점검을 실시할 수 있으며, 정기점검시간은 서비스제공화면에 공지한 바에 따릅니다.</li>
												</ol>

												<p1>제 13 조 ("서비스"의 변경)  </p1>
												<ol class="last">
													<li>"회사"는 상당한 이유가 있는 경우에 운영상, 기술상의 필요에 따라 제공하고 있는 전부 또는 일부 "서비스"를 변경할 수 있습니다.</li>
													<li>"서비스"의 내용, 이용방법, 이용시간에 대하여 변경이 있는 경우에는 변경사유, 변경될 서비스의 내용 및 제공일자 등은 그 변경 전 7일 이상 해당 서비스 초기화면에 게시하여야 합니다. </li>
													<li>"회사"는 무료로 제공되는 서비스의 일부 또는 전부를 회사의 정책 및 운영의 필요상 수정, 중단, 변경할 수 있으며, 이에 대하여 관련법에 특별한 규정이 없는 한 "회원"에게 별도의 보상을 하지 않습니다.</li>
												</ol>

												<p1>제 14 조 (정보의 제공 및 광고의 게재) </p1>
												<ol class="last">
													<li>"회사"는 "회원"이 "서비스" 이용 중 필요하다고 인정되는 다양한 정보를 공지사항이나 전자우편 등의 방법으로 "회원"에게 제공할 수 있습니다. 다만, "회원"은 관련법에 따른 거래관련 정보 및 고객문의 등에 대한 답변 등을 제외하고는 언제든지 전자우편 등에 대해서 수신 거절을 할 수 있습니다. </li>
													<li>제1항의 정보를 전화 및 모사전송기기에 의하여 전송하려고 하는 경우에는 "회원"의 사전 동의를 받아서 전송합니다. 다만, "회원"의 거래관련 정보 및 고객문의 등에 대한 회신에 있어서는 제외됩니다. </li>
													<li> "회사"는 "서비스"의 운영과 관련하여 서비스 화면, 홈페이지, 전자우편 등에 광고를 게재할 수 있습니다. 광고가 게재된 전자우편 등을 수신한 "회원"은 수신거절을 "회사"에게 할 수 있습니다.</li>
												</ol>

												<p1>제 15 조 (계약해제, 해지 등) </p1>
												<ol class="last">
													<li>"회원"은 언제든지 서비스초기화면의 고객센터 또는 내 정보 관리 메뉴 등을 통하여 이용계약 해지 신청을 할 수 있으며, "회사"는 관련법 등이 정하는 바에 따라 이를 즉시 처리하여야 합니다.  </li>
													<li>"회원"이 계약을 해지할 경우, 관련법 및 개인정보취급방침에 따라 "회사"가 회원정보를 보유하는 경우를 제외하고는 해지 즉시 "회원"의 모든 데이터는 소멸됩니다.</li>
												</ol>

												<p1>제 16 조 (이용제한 등) </p1>
												<ol class="last">
													<li>"회사"는 "회원"이 이 약관의 의무를 위반하거나 "서비스"의 정상적인 운영을 방해한 경우, 경고, 일시정지, 영구이용정지 등으로 "서비스" 이용을 단계적으로 제한할 수 있습니다.  </li>
													<li>"회사"는 전항에도 불구하고, "주민등록법"을 위반한 명의도용 및 결제도용, "저작권법" 및 "컴퓨터프로그램보호법"을 위반한 불법프로그램의 제공 및 운영방해, "정보통신망법"을 위반한 불법통신 및 해킹, 악성프로그램의 배포, 접속권한 초과행위 등과 같이 관련법을 위반한 경우에는 즉시 영구이용정지를 할 수 있습니다. </li>
													<li>"회사"는 본 조의 이용제한 범위 내에서 제한의 조건 및 세부내용은 이용제한정책 및 개별 서비스상의 운영정책에서 정하는 바에 의합니다. </li>
													<li>본 조에 따라 "서비스" 이용을 제한하거나 계약을 해지하는 경우에는 "회사"는 제9조["회원"에 대한 통지]에 따라 통지합니다. </li>
													<li>"회원"은 본 조에 따른 이용제한 등에 대해 "회사"가 정한 절차에 따라 이의신청을 할 수 있습니다. 이 때 이의가 정당하다고 "회사"가 인정하는 경우 "회사"는 즉시 "서비스"의 이용을 재개합니다. </li>
												</ol>

												<p1>제 17 조 (책임제한)</p1>
												<ol class="last">
													<li>"회사"는 천재지변 또는 이에 준하는 불가항력으로 인하여 "서비스"를 제공할 수 없는 경우에는 "서비스" 제공에 관한 책임이 면제됩니다. </li>
													<li>"회사"는 "회원"의 귀책사유로 인한 "서비스" 이용의 장애에 대하여는 책임을 지지 않습니다. </li>
													<li>"회사"는 무료로 제공되는 서비스 이용과 관련하여 관련법에 특별한 규정이 없는 한 책임을 지지 않습니다.</li>
												</ol>

												<p1>제 18 조 (준거법 및 재판관할) </p1>
												<ol class="last">
													<li>"회사"와 "회원" 간 제기된 소송은 대한민국법을 준거법으로 합니다. </li>
													<li>"회사"와 "회원"간 발생한 분쟁에 관한 소송은 민사소송법 상의 관할법원에 제소합니다.</li>
												</ol>


												<p1>부칙 </p1>
												<p class="last">1. 이 약관은 2016년 09월 01일부터 적용됩니다. </p>
					                        </div>
					                    </div>
				                	</div>
					                <div class="bx_term">
					                    <h3 class="chk_motion lst_check3">
					                        <span class="" style="color: #D96C77;">*</span><span>개인정보 수집 및 이용방침</span>
					                        <label class="on">
					                        	<span>동의합니다</span>
					                        	<input type="checkbox" name="chkTerm" value="Y">
					                        	<span class="bx_chk"></span>
					                        </label>
					                    </h3>
					                    <div class="detail">
					                        <div class="in" style="text-align:left;">
												<p1>아쿠아필드는 회원님들의 개인정보를 소중히 다루고있습니다. </p1>
												<p>&lt;아쿠아필드&gt;('www.aquafield-ssg.co.kr'이하 '아쿠아필드')은(는) 개인정보보호법에 따라 이용자의 개인정보 보호 및 권익을 보호하고 개인정보와 관련한 이용자의 고충을 원활하게 처리할 수 있도록 다음과 같은 처리방침을 두고 있습니다.</p>
												<p class="last">아쿠아필드는 회사의 개인정보처리방침을 개정하는 경우 웹사이트 공지사항(또는 개별공지)을 통하여 공지할 것입니다.</p>
												<p class="last">○ 본 방침은부터 2016년 9월 1일부터 시행됩니다.</p>

												<p2>1. 개인정보의 처리 목적</p2>
												<p>아쿠아필드는 개인정보를 다음의 목적을 위해 처리합니다. 처리한 개인정보는 다음의 목적이외의 용도로는 사용되지 않으며 이용 목적이 변경될 시에는 사전동의를 구할 예정입니다.</p>
												<p3>가. 홈페이지 회원가입 및 관리</p3>
												<p>회원 가입의사 확인, 회원제 서비스 제공에 따른 본인 식별•인증, 회원자격 유지•관리, 제한적 본인확인제 시행에 따른 본인확인, 서비스 부정이용 방지 등을 목적으로 개인정보를 처리합니다.</p>
												<p3>나. 재화 또는 서비스 제공</p3>
												<p>서비스 제공, 청구서 발송, 콘텐츠 제공, 요금결제•정산 등을 목적으로 개인정보를 처리합니다.</p>
												<p3>다. 마케팅 및 광고에의 활용</p3>
												<p class="last">이벤트 및 광고성 정보 제공 및 참여기회 제공, 인구통계학적 특성에 따른 서비스 제공 및 광고 게재 , 접속빈도 파악 또는 회원의 서비스 이용에 대한 통계 등을 목적으로 개인정보를 처리합니다.</p>

												<p2>2. 개인정보의 처리 및 보유 기간</p2>
												<p3>①아쿠아필드는 법령에 따른 개인정보 보유•이용기간 또는 정보주체로부터 개인정보를 수집시에 동의 받은 개인정보 보유,이용기간 내에서 개인정보를 처리,보유합니다.</p3>
												<p3>②각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.</p3>
												<p4>1. &lt;제화 또는 서비스 제공&gt;</p4>
												<p>&lt;제화 또는 서비스 제공&gt;와 관련한 개인정보는 수집.이용에 관한 동의일로부터&lt;5년&gt;까지 위 이용목적을 위하여 보유.이용됩니다.</p>
												<p>- 보유근거 : 전자상거래 등에서의 소비자보호에 관한 법률</p>
												<p class="last">- 관련법령 : 대금결제 및 재화 등의 공급에 관한 기록 : 5년</p>

												<p2>3. 개인정보의 제3자 제공에 관한 사항</p2>
												<p3>①아쿠아필드는 정보주체의 동의, 법률의 특별한 규정 등 개인정보 보호법 제17조 및 제18조에 해당하는 경우에만 개인정보를 제3자에게 제공합니다.</p3>
												<p3>②아쿠아필드는 다음과 같이 개인정보를 제3자에게 제공하고 있습니다.</p3>
												<p4>1. &lt;(주)케이에스넷&gt;</p4>
												<p>- 개인정보를 제공받는 자 : (주)케이에스넷</p>
												<p>- 제공받는 자의 개인정보 이용목적 : 이메일, 휴대전화번호, 이름, 신용카드정보, 은행계좌정보, 서비스 이용 기록, 결제기록</p>
												<p class="last">- 제공받는 자의 보유이용기간: 5년</p>

												<p2>4. 정보주체의 권리,의무 및 그 행사방법 이용자는 개인정보주체로서 다음과 같은 권리를 행사할 수 있습니다.</p2>
												<p3>① 정보주체는 아쿠아필드에 대해 언제든지 다음 각 호의 개인정보 보호 관련 권리를 행사할 수 있습니다.</p3>
												<p4>1. 개인정보 열람요구</p4>
												<p4>2. 오류 등이 있을 경우 정정 요구</p4>
												<p4>3. 삭제요구</p4>
												<p4>4. 처리정지 요구</p4>
												<p3>② 제1항에 따른 권리 행사는아쿠아필드에 대해 개인정보 보호법 시행규칙 별지 제8호 서식에 따라 서면, 전자우편, 모사전송(FAX) 등을 통하여 하실 수 있으며 &lt;기관/회사명&gt;(‘사이트URL’이하 ‘사이트명) 은(는) 이에 대해 지체 없이 조치하겠습니다.</p3>
												<p3>③ 정보주체가 개인정보의 오류 등에 대한 정정 또는 삭제를 요구한 경우에는 &lt;기관/회사명&gt;(‘사이트URL’이하 ‘사이트명) 은(는) 정정 또는 삭제를 완료할 때까지 당해 개인정보를 이용하거나 제공하지 않습니다.</p3>
												<p3 class="last">④ 제1항에 따른 권리 행사는 정보주체의 법정대리인이나 위임을 받은 자 등 대리인을 통하여 하실 수 있습니다. 이 경우 개인정보 보호법 시행규칙 별지 제11호 서식에 따른 위임장을 제출하셔야 합니다.</p3>

												<p2>5. 처리하는 개인정보의 항목 작성</p2>
												<p3>①아쿠아필드는 다음의 개인정보 항목을 처리하고 있습니다.</p3>
												<p4>1. &lt;홈페이지 회원가입 및 관리&gt;</p4>
												<p>- 필수항목 :이메일, 휴대전화번호, 비밀번호, 접속 아이디, 이름</p>
												<p class="last">- 선택항목 :생년월일, 자택주소, 자택전화번호, 직장명, 직장주소, 직장전화번호, 직업, 기념일 등</p>

												<p2>6. 개인정보를인정보의 파기</p2>
												<p3>아쿠아필드는 원칙적으로 개인정보 처리목적이 달성된 경우에는 지체없이 해당 개인정보를 파기합니다. 파기의 절차, 기한 및 방법은 다음과 같습니다</p3>
												<p>- 이용자가 입력한 정보는 목적 달성 후 별도의 DB에 옮겨져(종이의 경우 별도의 서류) 내부 방침 및 기타 관련 법령에 따라 일정기간 저장된 후 혹은 즉시 파기됩니다. 이 때, DB로 옮겨진 개인정보는 법률에 의한 경우가 아니고서는 다른 목적으로 이용되지 않습니다.</p>
												<p>-파기한 이용자의 개인정보는 개인정보의 보유기간이 경과된 경우에는 보유기간의 종료일로부터 5일 이내에, 개인정보의 처리 목적 달성, 해당 서비스의 폐지, 사업의 종료 등 그 개인정보가 불필요하게 되었을 때에는 개인정보의 처리가 불필요한 것으로 인정되는 날로부터 5일 이내에 그 개인정보를 파기합니다.</p>
												<p>- 파기방법</p>
												<p class="last">전자적 파일 형태의 정보는 기록을 재생할 수 없는 기술적 방법을 사용하며, 종이에 출력된 개인정보는 분쇄기로 분쇄하거나 소각을 통하여 파기합니다.</p>

												<p2>7. 개인정보의 안전성 확보 조치</p2>
												<p3>아쿠아필드는 개인정보보호법 제29조에 따라 다음과 같이 안전성 확보에 필요한 기술적/관리적 및 물리적 조치를 하고 있습니다.</p3>
												<p4>1. 정기적인 자체 감사 실시</p4>
												<p>개인정보 취급 관련 안정성 확보를 위해 정기적(분기 1회)으로 자체 감사를 실시하고 있습니다.</p>
												<p4>2. 개인정보 취급 직원의 최소화 및 교육</p4>
												<p>개인정보를 취급하는 직원을 지정하고 담당자에 한정시켜 최소화 하여 개인정보를 관리하는 대책을 시행하고 있습니다.</p>
												<p4>3. 내부관리계획의 수립 및 시행</p4>
												<p>개인정보의 안전한 처리를 위하여 내부관리계획을 수립하고 시행하고 있습니다.</p>
												<p4>4. 해킹 등에 대비한 기술적 대책</p4>
												<p>아쿠아필드는 해킹이나 컴퓨터 바이러스 등에 의한 개인정보 유출 및 훼손을 막기 위하여 보안프로그램을 설치하고 주기적인 갱신•점검을 하며 외부로부터 접근이 통제된 구역에 시스템을 설치하고 기술적/물리적으로 감시 및 차단하고 있습니다.</p>
												<p4>5. 개인정보의 암호화</p4>
												<p>이용자의 개인정보는 비밀번호는 암호화 되어 저장 및 관리되고 있어, 본인만이 알 수 있으며 중요한 데이터는 파일 및 전송 데이터를 암호화 하거나 파일 잠금 기능을 사용하는 등의 별도 보안기능을 사용하고 있습니다.</p>
												<p4>6. 접속기록의 보관 및 위/변조 방지</p4>
												<p>개인정보처리시스템에 접속한 기록을 최소 6개월 이상 보관, 관리하고 있으며, 접속 기록이 위/변조 및 도난, 분실되지 않도록 보안기능 사용하고 있습니다.</p>
												<p4>7. 개인정보에 대한 접근 제한</p4>
												<p>개인정보를 처리하는 데이터베이스시스템에 대한 접근권한의 부여,변경,말소를 통하여 개인정보에 대한 접근통제를 위하여 필요한 조치를 하고 있으며 침입차단시스템을 이용하여 외부로부터의 무단 접근을 통제하고 있습니다.</p>
												<p4>8. 문서보안을 위한 잠금장치 사용</p4>
												<p>개인정보가 포함된 서류, 보조저장매체 등을 잠금장치가 있는 안전한 장소에 보관하고 있습니다.</p>
												<p4>9. 비인가자에 대한 출입 통제</p4>
												<p class="last">개인정보를 보관하고 있는 물리적 보관 장소를 별도로 두고 이에 대해 출입통제 절차를 수립, 운영하고 있습니다.</p>

												<p2>8. 개인정보 보호책임자 작성</p2>
												<p3>①아쿠아필드는 개인정보 처리에 관한 업무를 총괄해서 책임지고, 개인정보 처리와 관련한 정보주체의 불만처리 및 피해구제 등을 위하여 아래와 같이 개인정보 보호책임자를 지정하고 있습니다.</p3>
												<p4>개인정보 보호책임자</p4>
												<p>성명 : 김훈환</p>
												<p>직책 : 상무</p>
												<p>연락처 : 031-8072-8800</p>
												<p>이메일 : aquafield@shinsegae.com</p>
												<p>※ 개인정보 보호 담당부서로 연결됩니다.</p>

												<p4>개인정보 보호 담당부서</p4>
												<p>부서명  : 아쿠아운영팀</p>
												<p>담당자  : 김관</p>
												<p>직위  : 팀장</p>
												<p>연락처 : 031-8072-8800</p>
												<p>이메일 : aquafield@shinsegae.com</p>
												<p3 class="last">② 정보주체께서는 아쿠아필드의 서비스(또는 사업)을 이용하시면서 발생한 모든 개인정보 보호 관련 문의, 불만처리, 피해구제 등에 관한 사항을 개인정보 보호책임자 및 담당부서로 문의하실 수 있습니다. 아쿠아필드는 정보주체의 문의에 대해 지체 없이 답변 및 처리해드릴 것입니다.</p3>

												<p2>9. 개인정보 처리방침 변경</p2>
												<p3 class="last">① 이 개인정보처리방침은 시행일로부터 적용되며, 법령 및 방침에 따른 변경내용의 추가, 삭제 및 정정이 있는 경우에는 변경사항의 시행 7일 전부터 공지사항을 통하여 고지할 것입니다.</p3>
					                        </div>
					                    </div>
					                </div>
									<div class="bx_term">
					                    <h3 class="chk_motion lst_check3">
					                        <span>개인정보 마케팅 활용에 관한 사항 동의(선택)</span>
					                        <label class="on">
					                        	<span>동의합니다</span>
					                        	<input type="checkbox" name="chkTerm2" value="Y">
					                        	<span class="bx_chk"></span>
					                        </label>
					                    </h3>
					                    <div class="detail">
					                        <div class="in" style="text-align:left;">
												<p>· 이용항목: 휴대전화번호, 주소, 이메일, 성별</p>
												<p>· 이용목적: 이벤트 및 상품 안내</p>
												<p>· 보유 및 이용 기간: 회원의 개인정보는 개인정보의 수집 및 이용목적이 달성되면 파기하는 것을 원칙으로 합니다.<br/>수집하는 개인정보의 이용기간은 회원가입일로부터 탈퇴시 까지입니다.<br/>상세사항은 홈페이지 개인정보처리방침을 참조바랍니다.</p>
												<p>※ 위 사항에 대한동의를 거부할 수 있으나, 이에 대한 동의가 없을 경우 당사의 홍보 및 마케팅 정보를 제공받지 못할 수 있음을 알려드립니다.</p>
											</div>
										</div>
									</div>
					                <div class="agree_all">
					                    <c:if test="${POINT_CODE eq 'POINT01' }">
					                    	<a href="/hanam/index.af#/service/index.af?page=privacy" class="agree_btn">지난 개인정보 보기</a>
					                    </c:if>
					                    <c:if test="${POINT_CODE eq 'POINT03' }">
					                    	<a href="/goyang/index.af#/service/index.af?page=privacy" class="agree_btn">지난 개인정보 보기</a>
					                    </c:if>
					                    
					                    <div class="chk_motion lst_check3">
					                        <label class="on" style="font-weight:900;">
					                        	<span>약관 및 개인정보 이용방침에 모두 동의합니다</span>
				                        		<input type="checkbox" name="chkAllTerm" id="chkAllTerm">
				                        		<span class="bx_chk"></span>
					                        </label>
					                    </div>
					                </div>
					            </div>
					        </form>
					    </div>
					    <div class="opt_certification">
					        <dl>
					            <dt>인증 방법을 선택해 주세요</dt>
					            <dd class="chk_motion lst_check3">
					                <label><input type="radio" id="certPhone" name="optCertification" value="phone"><span class="bx_chk"></span> <span>휴대폰</span></label>
					                <!-- <label><input type="radio" id="certIpin" name="optCertification" value="ipin"><span class="bx_chk"></span> <span>아이핀</span></label> -->
					            </dd>
					        </dl>
					    </div>
						<div class="btn_group">
							<a href="javascript:goJoinStpe2();" class="btn gray">확인</a>
							<a href="/member/loginMain.af" class="btn s_blue">취소</a>
						</div>
		
		<script type="text/javascript">
		    setChkAndRadio();
		    $("#chkAllTerm").click(function(){
		        var inputs = $('input[name="chkTerm"], input[name="chkTermOptSMS"], input[name="chkTermOptMarketing"], input[name="chkTerm2"]');
		        if($(this).prop('checked')) inputs.prop('checked',true);
		        else {inputs.prop('checked',false);}
		        setChkAndRadio();
		    });
		    
		    /* 개인정보 보기  */
		    function Privacy() {
				window.open('/service/privacy.af', 'popupChk', 'width=1300, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
		    }
			
		    /* 약관 및 실명인증 */
		    function goJoinStpe2() {
				var inputs = $('input[name="chkTerm"]'), len = inputs.length;
				var cnt = 0;
				for (var i = 0 ; i < len ; i++) {
					if(inputs.eq(i).prop('checked')) cnt++;
				}
				if (len > cnt) {
					alert('약관을 체크를 확인해주세요.');
					return;
				}
				//실명 인증
		        if($('div.content.on #certPhone').length > 0) {
		            if($('#certPhone').is(':checked')) {
		            	certiJoin('checkplus','/member/checkplus.sf');
		                //_this.realNameCert();
		            } else if($('#certIpin').is(':checked')) {
		            	certiJoin('ipin','/member/ipin.af');
		                //_this.realNameCert();
		            } else {
		                alert('실명 인증 방법을 선택해주세요.');
		                return;
		            }
		        } else {
		           //memberPop.addCont({url: "/member/signupStep2.af"});
		        }
			};
		    /* 
			 function goJoinStpe2() { 
				$('#step01').attr('action', '/member/joinStep2.af');
				$('#step01').submit();
			}
			 */
			
		    function certiJoin(type,url){
		    	$.ajax({
			   		async: false
			   		,type: "post"
			   		,url : url
			   		,dataType : "html"
			   		,success: function(obj){
							if(type == "ipin"){
								document.form_ipin.enc_data.value = obj;
								fnIpinPopup();
							}else{
								document.form_chk.EncodeData.value = obj;
								fnPopup();
							}
			   		}
			   		,error: function(xhr, option, error){
			   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
			   		}
		   		});
		    };

		   
		   function gotoNext(obj){
			   var result = jQuery.parseJSON(obj);

			   document.step01.obj.value = result.sDupInfo;
			   document.step01.objName.value = result.sName;
			   document.step01.objGender.value = result.sGender;
			   document.step01.objBirth.value = result.sBirth;
			   document.step01.objMNum.value = result.sMobileNumber;
			   
			   
				var formData = $("#step01").serialize();

		    	$.ajax({
			   		async: false
			   		,type: "post"
			   		,url : '/member/duplicateChk.af'
			   		,data: formData
			   		,dataType : "html"
			   		,success: function(obj){
						$("#bx_signup_step").append(obj);
			   		}
			   		,error: function(xhr, option, error){
			   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
			   		}
		   		});
		   		
		   };

		   function goStep2(){
			   $('#step01').attr('action', '/member/joinStep2.af');
			   $('#step01').submit();
		   }
		   
		   $(".section.main").height();
		</script>
	</section>

	<script type="text/javascript">
		window.name ="Parent_window";
		
		function fnIpinPopup(){
			window.open('', 'popupIPIN2', 'width=450, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
			document.form_ipin.target = "popupIPIN2";
			document.form_ipin.action = "https://cert.vno.co.kr/ipin.cb";
			document.form_ipin.submit();
		}
		
		function fnPopup(){
			window.open('', 'popupChk', 'width=500, height=550, top=100, left=100, fullscreen=no, menubar=no, status=no, toolbar=no, titlebar=yes, location=no, scrollbar=no');
			document.form_chk.action = "https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb";
			document.form_chk.target = "popupChk";
			document.form_chk.submit();
		}
	</script>
	<!-- ############### Ipin 인증  #######################-->
	<form name="form_ipin" method="post">
		<input type="hidden" name="m" value="pubmain">
	    <input type="hidden" name="enc_data" value="">
	    <input type="hidden" name="param_r1" value="">
	    <input type="hidden" name="param_r2" value="">
	    <input type="hidden" name="param_r3" value="">
	</form>
	<!-- ############### Ipin 인증  #######################-->
	
	<!-- ############### 안심본인인증 ####################### -->
	<form name="form_chk" method="post">
		<input type="hidden" name="m" value="checkplusSerivce">
		<input type="hidden" name="EncodeData" value="">
		<input type="hidden" name="param_r1" value="">
		<input type="hidden" name="param_r2" value="">
		<input type="hidden" name="param_r3" value="">
	</form>
	<!-- ############### 안심본인인증 ####################### -->

</div>

        </div>
	    </div>
	</div>
	
	
