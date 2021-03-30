<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../common/taglibs.jsp" %>
<div class="contentArea active">
	<div id="service" class="content">
		<div class="content-inner">
			<div class="content-box">
				<div class="inner">
					<div class="box-content">
						<div class="content"></div>
    					<div class="content"></div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			window.contentBox = new contentBoxFn();
			var p = getParameter("page");
			contentBox.setCont({url : '/service/'+p+'.af'});
		</script>
	</div>
</div>
