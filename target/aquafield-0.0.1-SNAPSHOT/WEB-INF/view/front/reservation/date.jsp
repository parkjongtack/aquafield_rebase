<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%><%@ include file="../../common/taglibs.jsp" %><button class="btn_close">닫기</button><div id="reservation_date" class="in">    <header class="hd_type2">        <h2>방문일을 선택해주세요</h2>    </header>   	<form method="post" onsubmit="return false;">    <section class="">        <input type="hidden" name="date" id="date" value="">        <div id="datepicker"></div>        <div class="notice">            <h3>※ 켈린더 사용안내</h3>            <p><span class="res_not">11</span> : 예약할수 없는 날짜입니다.</p>            <p><span class="res_ok">11</span> : 예약이 가능한 날짜입니다.</p>            <p><span class="res_event">11</span> : 이벤트가 진행중인 날짜입니다.</p>        </div>        <div class="event_box">            <h3>9월 진행중인 이벤트 </h3>            <p>- 스파 힐링요가 (9월 15일 ~ 9월 25일)</p>            <p>- 불꽃놀이 in 워터파크 (9월 28일) </p>        </div>    </section>    </form>    <script type="text/javascript">        setChkAndRadio();        $('.customized-select').customSelect();        var emptydaysObj = $.parseJSON( '${emptydays}');        var seasondaysObj = $.parseJSON( '${seasondays}');        var reservedaysObj = $.parseJSON( '${reservedays}');        var eventdaysObj = $.parseJSON( '${eventdays}');                var emptydays = {};        $.each(emptydaysObj, function(index){			emptydays[emptydaysObj[index].YYYYMMDD] = ({title:emptydaysObj[index].TITLE});        });           var eventdays = {};        $.each(eventdaysObj, function(index){        	eventdays[eventdaysObj[index].YYYYMMDD] = ({title:eventdaysObj[index].TITLE});        });                 var minday = 1;        var today = new Date();        var lastDay = new Date(today.getFullYear(), today.getMonth(), 0);        var betweenDay = Math.floor((today.getTime() - lastDay.getTime())/1000/60/60/24);        if(reservedaysObj.length == 0){        	minday = betweenDay;        }                $( "#datepicker" ).datepicker({            dayNamesMin: ['sun', 'mon', 'tue', 'wed', 'thu', 'fri', 'sat'],            dateFormat: "yymmdd",            minDate: 1,            onSelect: function(date){                $("#date").val(date);                reservationPop.setResData('date','#date');            },            beforeShowDay: function(day) {                var result;                var eventday = eventdays[$.datepicker.formatDate("yymmdd", day)];                var emptyday = emptydays[$.datepicker.formatDate("yymmdd", day)];//                var seasonday = seasondays[$.datepicker.formatDate("yymmdd", day)];                  if (eventday) {                    result =  [true, "date-eventday", eventday.title];                }                                if (emptyday) {                    result =  [false, "date-eventday", emptyday.title];                }                //                 if (seasonday) {//                     result =  [true, "date-seasonday", seasonday.title];//                 }                                                if(!result) {                    result = [true, ""];                }                                return result;            }        });        reservationPop.getResData('date','#date');        $( "#datepicker" ).datepicker( "setDate", $("#date").val() );    </script></div>