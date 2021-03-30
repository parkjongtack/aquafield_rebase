<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<script>
    var obj = '${parameter}';
	opener.gotoNext(obj);
    self.close();
</script>