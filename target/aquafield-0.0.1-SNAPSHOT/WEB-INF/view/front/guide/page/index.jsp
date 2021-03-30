<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp" %>
<div class="contentArea">
	<div id="guide" class="content">
		<div class="content-inner" style="height : 875px;">
			<div class="content-box">
				<div class="inner">
					<div class="box-content">
						<div class="box-nav">
							<a href="javascript:void(0);" class="box-prev">
								<span>prev</span>
							</a>
							<a href="javascript:void(0);" class="box-next">
								<span>next</span>
							</a>
						</div>
						<div class="content"></div>
    					<div class="content"></div>
    					<div class="box-paging"></div>
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			window.contentBox = new contentBoxFn();
			var p = getParameter("page");
			contentBox.setCont({url : '/guide/page'+p+'.af'});

			pageContent.pageList = {
				'1' : {title:'이용정보'},
				'2' : {title:'정산안내'},
				'3' : {title:'렌탈이용안내'},
				'4' : {title:'FAQ'},
				'5' : {title:'이용순서'}
			}
			pageContent.setPaging();
        	
		</script>
	</div>
</div>