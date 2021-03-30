<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div id="scrollTop"><img src="/admaf/images/common/btn_top.png"></div>
<section id="pop_bx_common"></section><!-- //pop_bx_common -->
<script type="text/javascript">
	function showPopEditMyProfilePanel(){
		ajaxShowPopCont({
			url : "/secu_admaf/admin/ajax_edit_my_profile.af",
		});
	}

	$(function(){

		var pageIdx = decodeURIComponent(window.location.pathname.split('/')[2]);
		var subIdx = $("#path h2").text();

		var lnb = $("#lnb");
		lnb.find('li.'+pageIdx).addClass('on');

		$(".lnb_depth2 li").each(function() {
			if($(this).find(".menu_tit").text() == subIdx){
				$(this).addClass("on");
			}
		});
	});
</script>