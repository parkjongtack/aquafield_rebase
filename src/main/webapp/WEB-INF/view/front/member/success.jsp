<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<script type="text/javascript" src="/common/front/js/jquery-1.11.2.min.js"></script>
<script>
    var obj = '${parameter}';
    //var data = $.parseJSON(obj);
	opener.gotoNext(obj);
    self.close();
</script>