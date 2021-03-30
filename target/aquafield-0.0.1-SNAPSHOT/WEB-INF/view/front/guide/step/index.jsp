<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp" %>
<div class="contentArea active">
	<div id="guide" class="content step">
		<div class="content-inner">
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
			contentBox.setCont({url : '/guide/step/step'+p+'.af'});

			pageContent.pageList = {
				'1' : {title:'이용순서 Step01', step:'Step01<br/>매표소'},
				'2' : {title:'이용순서 Step02', step:'Step02<br/>컨시어즈'},
				'3' : {title:'이용순서 Step03', step:'Step03<br/>물품샵'},
				'4' : {title:'이용순서 Step04', step:'Step04<br/>신발장'},
				'5' : {title:'이용순서 Step05', step:'Step05<br/>락커룸'},
				'6' : {title:'이용순서 Step06', step:'Step06<br/>사우나'},
				'7' : {title:'이용순서 Step07', step:'Step07<br/>워터파크'},
				'8' : {title:'이용순서 Step07', step:'Step07<br/>찜질스파'},
				'9' : {title:'이용순서 Step08', step:'Step08<br/>F&B'},
				'10' : {title:'이용순서 Step09', step:'Step09<br/>사우나'},
				'11' : {title:'이용순서 Step10', step:'Step10<br/>락커름'},
				'12' : {title:'이용순서 Step11', step:'Step11<br/>정산소'},
				'13' : {title:'이용순서 Step12', step:'Step12<br/>신발장'},
				'14' : {title:'이용순서 Step13', step:'Step13<br/>물품샵'},
				'15' : {title:'이용순서 Step14', step:'Step14<br/>정산소'},
				'16' : {title:'이용순서 Step15', step:'Step15<br/>출구'}
			}
			pageContent.setPaging();


			$('.content-box').on('click', '.addCont .btn', function(e) {
                if(!$(this).hasClass('active')){
                	var contentH = $(this).next().height();
                    $(this).addClass('active');
                    $(this).next().css({height: 0, display: 'block'});
                    $(this).next().animate({height: contentH},500, function() {
                    	$(this).css({height: ''});
                    	iscrollFn.setting();
                    });
                }else{
                    $(this).removeClass('active');
                    $(this).next().animate({height: 0},500, function() {
                        $(this).css({height: '', display: ''});
                        iscrollFn.setting();
                    });
                }
			});
		</script>
	</div>
</div>