<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
response.setContentType("text/html");
request.setCharacterEncoding("utf-8");
response.setContentType("text/html; charset=utf-8");
%>
<fmt:requestEncoding value="utf-8" />
<%
if(session.getAttribute("NO_LOGIN") != null){
	//session.removeAttribute("NO_LOGIN");
%>
<!DOCTYPE html>
<html lang="ko">
<meta charset="utf-8">
	<script>
	alert("로그아웃 되었거나 로그인 되지 않았습니다.");
	location.href="/member/logout.af";
	</script>
<body>
</body>
</html>
<%
	//return;
}
%>
