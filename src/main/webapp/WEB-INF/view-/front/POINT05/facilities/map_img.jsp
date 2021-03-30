<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="../../../common/taglibs.jsp" %>
<div class="miniTit">
    <ul>
        <li>HOME</li>
        <li>ABOUT</li>
        <li>시설안내</li>
    </ul>
</div>
<div class="list_floors detail">
	<ul>
		<li class="f1"><div class="tit"><img src="/common/front/images/${POINT_CODE }/facilities/RF_tit.png" alt="RF"></div><ul class="nav_sect"></ul><div class="in"><div class="bx_svg"><img src="/common/front/images/${POINT_CODE}/facilities/img_rf.png" alt="RF_MAP"><div class="bx_pin"></div></div></div></li>
		<li class="f2">
			<div class="tit"><img src="/common/front/images/${POINT_CODE }/facilities/4F_tit.png" alt="4F"></div>
			<ul class="nav_sect"></ul>
			<div class="in">
				<div class="bx_svg">
					<div class="bx_pin"></div>
					<div class="faci point05">
						<div id="floor_4f_3" style="width:95.5%;height:97.6%;bottom: 2.3%;left: 10.8%;"></div>
						<div id="floor_4f_2" style="width:50.2%;height:52%;top: 0;left: 0;"></div>
						<div id="floor_4f_5" style="width:58.3%;height:58.5%;top: 23.2%;left: 0;"></div>
						<div id="floor_4f_1" style="width: 20.7%;height: 29%;top: 0.1%;left: 43.3%;"></div>
						<div id="floor_4f_4" style="width: 40%;height:25%;bottom: 0.5%;left: 23.8%;"></div>
					</div>
					<img src="/common/front/images/${POINT_CODE }/facilities/img_4f.png" alt="4F_MAP"/>
				</div>
			</div>
		</li>
	</ul>
</div>
<nav class="nav_floor">
	<ul>
		<li class="on"><a href="#/facilities/index.af?depth1=1" data-idx="0">RF</a></li>
		<li><a href="#/facilities/index.af?depth1=2" data-idx="1">4F</a></li>
		<li><a href="#/facilities/index.af?depth1=3" data-idx="2">3F</a></li>
	</ul>
</nav>
<div class="zoomArea">
	<button class="in" onclick="">zoom in</button>
	<button class="out" onclick="">zoom out</button>
</div>
<script type="text/javascript">

FacilityFn.prototype.setSectClick = function(i, j){ //각 시설들 호버효과 및 클릭 이벤트 등록
	var unit = this.liArr[i].faci[j].el, _this = this;
	if(unit) unit.setAttribute('class','hover_able');
	$(unit).on('click',function(e){ //섹션 클릭했을때
		_this.zoomSect.call(this, e, i, j, 1);
		location.href = '#/facilities/index.af?depth1=2&depth2='+(j+1);
	});
};

FacilityFn.prototype.getUnitPositon = function(el){
	var self_rec = $(el), padding = 1.06;
	return {w : self_rec.width()*padding, h : self_rec.height()*padding, l : self_rec.offset().left-self_rec.width()*(padding-1)/2, t : self_rec.offset().top-self_rec.height()*(padding-1)/2};
};

var facility = new FacilityFn({
	dth1 : '${param.depth1}',
	dth2 : '${param.depth2}',
	imgType : 'img',
	transforms : false
});
</script>
