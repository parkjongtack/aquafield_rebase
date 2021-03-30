<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div id="scrollTop"><img src="/common/admin/images/common/btn_top.png"></div>
<section id="pop_bx_common"></section><!-- //pop_bx_common -->
<script type="text/javascript">
	function showPopEditMyProfilePanel(){
		ajaxShowPopCont({
			url : "/admin/ajax_edit_my_profile.jsp",
		});
	}
</script>