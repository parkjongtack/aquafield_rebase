<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
    <div class="iscContent">
        <div class="inner">
        <section id="cs_write">
            <header>
                <h3><img class="ico" src="/common/front/images/ico/ico_cs_view.png"><span>문의사항을 입력해 주세요. <br/>담당자 확인 후 답변 드리도록 하겠습니다.</span></h3>
            </header>
            <form id="insform" name="insform" action="" method="post" onsubmit="return false;">
              <div class="tb_type1 mb30">
                <table>
                  <colgroup>
                    <col width="15%">
                    <col width="75%">
                  </colgroup>
                  <tbody>
                  	<tr>
                      <th>지점</th>
                      <td>
                        <div class="select st2">
                          <select name="pointCode" id="pointCode" class="customized-select w150">
                            <option value="">지점 선택</option>
                            <c:forEach items="${pointList}" var="point">
                              <option value="${point.CODE_ID}">${point.CODE_NM}</option>
                            </c:forEach>
                          </select>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <th>문의유형</th>
                      <td>
                        <div class="select st2">
                          <select name="askType" id="askType" class="customized-select w150">
                            <option value="">문의유형 선택</option>
                            <c:forEach items="${typeList}" var="type">
                              <option value="${type.CODE_ID}">${type.CODE_NM}</option>
                            </c:forEach>
                          </select>
                        </div>
                      </td>
                    </tr>
                    <tr>
                      <th>제  목</th>
                      <td>
                        <input type="text" class="ipt" id="title" name="title" onblur="">
                      </td>
                    </tr>
                    <tr>
                      <th>내  용</th>
                      <td class="textArea">
                        <textarea class="txa" id="content" name="content"></textarea>
                      </td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </form>
            <div class="btns center">
              <button class="btn_pack btn_mo brown" id="btnSave">확인</button>
            </div>
            <!-- <div class="inputBox">
                <div class="inner">
                             <div class="title">
                        <div class="select st2">
                            <select name="askType" id="askType" class="customized-select w150">
                                  <option value="">문의유형 선택</option>
                                              <c:forEach items="${typeList}" var="type">
                                  <option value="${type.CODE_ID}">${type.CODE_NM}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input type="text" class="ipt" id="title" name="title" onblur="">
                    </div>
                    <div class="textArea">
                        <textarea class="txa" id="content" name="content"></textarea>
                    </div>
                </div>
            </div> -->
            <script type="text/javascript">
                $('.customized-select').customSelect();
                
                function askSave(){
                  if($("#pointCode").val() == ''){alert('지점을 선택해 주시기 바랍니다.'); $("#pointCode").focus(); return false;}
               	  if($("#askType").val() == ''){alert('문의유형을 선택해 주시기 바랍니다.'); $("#askType").focus(); return false;}
                  if($("#askType").val() == ''){alert('문의유형을 선택해 주시기 바랍니다.'); $("#askType").focus(); return false;}
                  if($.trim($("#title").val()) == ''){alert('문의내역 제목을 작성해 주시기 바랍니다.'); $("#title").focus(); return false;}
                  if($.trim($("#content").val()) == ''){alert('문의내용 작성해 주시기 바랍니다.'); $("#content").focus(); return false;}

                  $.ajax({
                type: "post"
                    ,url : '/mypage/ajaxCsIns.af'
                    ,data : $("#insform").serialize()
                    ,dataType : "html"
                    ,success: function(obj){
                eval(obj);
                    }
                    ,error: function(xhr, option, error){
                      alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
                    }
                  });

                };
                
                //고객문의 중복 등록이 안되도록 click 중복 방지 처리
        		$("#btnSave").unbind("click").bind("click", function(){
        			$(this).attr("disabled", "disabled");
        			askSave();
        		});
            </script>
        </section>
      </div>
    </div>