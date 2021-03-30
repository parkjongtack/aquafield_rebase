$(document).ready(function () {

	$("body").on('change focusin focusout hover keydown keypress keyup',".idAble",function(){
		var exp = /[^a-z0-9!#\$\(\)\*,.\/:@\[\]\^_`\{\|\}~]/gi;
		removeUnableTxt($(this),exp);
	});

	$("body").on('change focusin focusout hover keydown keypress keyup', ".numAble", function(){
		var exp = /[^0-9\.]/gi;
		removeUnableTxt($(this),exp);
	});

	$("body").on('change focusin focusout hover keydown keypress keyup',".engAble",function(){
		var exp = /[^a-z\x20]/gi;
		removeUnableTxt($(this),exp);
	});

	$("body").on('change focusin focusout hover keydown keypress keyup',".korAble",function(){
		var exp = /([^가-힣ㄱ-ㅎㅏ-ㅣ\x20])/gi;
		removeUnableTxt($(this),exp);
	});

	$("body").on('change focusin focusout hover keydown keypress keyup',".korengAble",function(){
		var exp = /([^가-힣ㄱ-ㅎㅏ-ㅣa-z\x20])/gi;
		removeUnableTxt($(this),exp);
	});

	$("body").on('change focusin focusout hover keydown keypress keyup',".txtAble",function(){
		var exp = /([^가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9~!@\#$^&\()\-=+_,.?\x20\n]|-{2})/ig;
		removeUnableTxt($(this),exp);	
	});

	$("body").on('change focusin focusout hover keydown keypress keyup', ".emailAble",function(){
		var exp = /([^0-9a-zA-Z_\.-]+|-{2})/ig;
		removeUnableTxt($(this),exp);
	});

	$("body").on('change focusin focusout hover keydown keypress keyup',".pwAble",function(){
		var exp = /[^a-zA-Z0-9!#\$\(\)\*,.\/:@\[\]\^_`\{\|\}~]/gi;
		removeUnableTxt($(this),exp);
	});
	
	$("body").on('change focusin focusout hover keydown keypress keyup', ".injectAble", function(){
		var exp = /delete|update|insert|select|<script>|<script|<|>|javascript|vbscript|expression|--/gi;
		removeUnableTxt($(this),exp);
	});

});

function removeUnableTxt(p_obj, p_regexp){
	var expAbleText = p_regexp;
	var oriTxt = p_obj.val();
	var returnTxt;
	returnTxt = oriTxt.replace(expAbleText,"");
	if (returnTxt != oriTxt)
	{
		p_obj.val(returnTxt);
	}
}