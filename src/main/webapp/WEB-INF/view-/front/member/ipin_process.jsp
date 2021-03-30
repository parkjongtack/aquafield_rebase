<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<script type="text/javascript" src="/common/front/js/jquery-1.11.2.min.js"></script>
<%
	/*********************************************************************************************
		NICE평가정보 Copyright(c) KOREA INFOMATION SERVICE INC. ALL RIGHTS RESERVED
		
		서비스명 : 가상주민번호서비스 (IPIN) 서비스
		페이지명 : 가상주민번호서비스 (IPIN) 사용자 인증 정보 처리 페이지
		
				   수신받은 데이터(인증결과)를 메인화면으로 되돌려주고, close를 하는 역활을 합니다.
	**********************************************************************************************/
	
	// 사용자 정보 및 CP 요청번호를 암호화한 데이타입니다. (ipin_main.jsp 페이지에서 암호화된 데이타와는 다릅니다.)
	String sResponseData = requestReplace(request.getParameter("enc_data"), "encodeData");
	
	// ipin_main.jsp 페이지에서 설정한 데이타가 있다면, 아래와 같이 확인가능합니다.
	String sReservedParam1  = requestReplace(request.getParameter("param_r1"), "");
	String sReservedParam2  = requestReplace(request.getParameter("param_r2"), "");
	String sReservedParam3  = requestReplace(request.getParameter("param_r3"), "");
    
    
    // 암호화된 사용자 정보가 존재하는 경우
    if (!sResponseData.equals("") && sResponseData != null)
    {

%>

<script language='javascript'>

   	$.ajax({
   		async: false
   		,type: "post"
   		,url : '/member/ipin_result.af'
   		,data : {enc_data :'<%= sResponseData %>'}
   		,dataType : "html"
   		,success: function(obj){
   			//console.log(obj);
   			opener.gotoNext(obj);
   		    self.close();
   		}
   		,error: function(xhr, option, error){
   			alert("에러가 발생했습니다. 잠시 후에 다시하세요.");
   			self.close();
   		}
  	});	
	

	
</script>
<%
	} else {
%>
<script language='javascript'>
	self.close();
</script>
<%
	}
%>
<%!
public String requestReplace (String paramValue, String gubun) {
        String result = "";
        
        if (paramValue != null) {
        	
        	paramValue = paramValue.replaceAll("<", "&lt;").replaceAll(">", "&gt;");

        	paramValue = paramValue.replaceAll("\\*", "");
        	paramValue = paramValue.replaceAll("\\?", "");
        	paramValue = paramValue.replaceAll("\\[", "");
        	paramValue = paramValue.replaceAll("\\{", "");
        	paramValue = paramValue.replaceAll("\\(", "");
        	paramValue = paramValue.replaceAll("\\)", "");
        	paramValue = paramValue.replaceAll("\\^", "");
        	paramValue = paramValue.replaceAll("\\$", "");
        	paramValue = paramValue.replaceAll("'", "");
        	paramValue = paramValue.replaceAll("@", "");
        	paramValue = paramValue.replaceAll("%", "");
        	paramValue = paramValue.replaceAll(";", "");
        	paramValue = paramValue.replaceAll(":", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll("#", "");
        	paramValue = paramValue.replaceAll("--", "");
        	paramValue = paramValue.replaceAll("-", "");
        	paramValue = paramValue.replaceAll(",", "");
        	
        	if(gubun != "encodeData"){
        		paramValue = paramValue.replaceAll("\\+", "");
        		paramValue = paramValue.replaceAll("/", "");
            paramValue = paramValue.replaceAll("=", "");
        	}
        	
        	result = paramValue;
            
        }
        return result;
  }
%>