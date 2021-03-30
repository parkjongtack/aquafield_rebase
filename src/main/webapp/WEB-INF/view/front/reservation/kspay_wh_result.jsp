<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String rcid		= request.getParameter("reCommConId"		);
	String rctype	= request.getParameter("reCommType"		);
	String rhash	= request.getParameter("reHash"			);
	/* rcid 없으면 결제를 끝까지 진행하지 않고 중간에 결제취소 */
	
	String	authyn =  "";
	String	trno   =  "";
	String	trddt  =  "";
	String	trdtm  =  "";
	String	amt    =  "";
	String	authno =  "";
	String	msg1   =  "";
	String	msg2   =  "";
	String	ordno  =  "";
	String	isscd  =  "";
	String	aqucd  =  "";
	String	result =  "";

	String	resultcd =  "";
	String returnVal = "";

	ksnet.kspay.KSPayWebHostBean ipg = new ksnet.kspay.KSPayWebHostBean(rcid);
	if (ipg.send_msg("1"))		//KSNET 결제결과 중 아래에 나타나지 않은 항목이 필요한 경우 Null 대신 필요한 항목명을 설정할 수 있습니다.
	{
		authyn	= ipg.getValue("authyn");
		trno	 = ipg.getValue("trno"  );
		trddt	 = ipg.getValue("trddt" );
		trdtm	 = ipg.getValue("trdtm" );
		amt		 = ipg.getValue("amt"   );
		authno = ipg.getValue("authno");
		msg1	 = ipg.getValue("msg1"  );
		msg2	 = ipg.getValue("msg2"  );
		ordno	 = ipg.getValue("ordno" );
		isscd	 = ipg.getValue("isscd" );
		aqucd	 = ipg.getValue("aqucd" );
		//temp_v	 = ipg.getValue("temp_v");
		result	 = ipg.getValue("result");

		if (null != authyn && 1 == authyn.length())
		{
			if (authyn.equals("O"))
			{
				resultcd = "0000";
				
				returnVal = "{\"authyn\":\""+authyn+"\",\"trno\":\""+trno+"\",\"trddt\":\""+trddt+"\",\"trdtm\":\""+trdtm+"\",\"amt\":\""+amt+"\",\"authno\":\""+authno+"\",\"msg1\":\""+msg1+"\",\"msg2\":\""+msg2+"\",\"ordno\":\""+ordno+"\",\"isscd\":\""+isscd+"\",\"aqucd\":\""+aqucd+"\",\"result\":\""+result+"\"}"
			}else
			{
				resultcd = authno.trim();
			}

			ipg.send_msg("3");		// 현재 결제대기 상태이며 kspay_send_msg("1")을 호출하셔야 결제가 처리됩니다.
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<script language="JavaScript">
<!--
<% if(resultcd= "0000"){ %>
	opener.mParamSet(<%=returnVal%>);
<% }else{ %>
	alert("ERROR: 결제중 에러가 발생하였습니다. 다시 시도해 주십시오.");
	self.close();
	return;
<% } %>
-->
</script>
</head>
<body onload="">

</body>
</html>