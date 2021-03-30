var encrypt = function(){
	var key, iv;
	this.setConf = function(key, iv){
		this.key = key;
		this.iv = iv;
	}
	this.encrypt = function(){
		var rkEncryptionKey = CryptoJS.enc.Base64.parse(this.key);
		var rkEncryptionIv = CryptoJS.enc.Base64.parse(this.iv);
		var resData=JSON.stringify(reservationPop2.resData);
		var encrypted=''+CryptoJS.AES.encrypt(resData,rkEncryptionKey,{mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.Pkcs7,iv:rkEncryptionIv});
		var rand = document.forms['f'].rand.value;
		encrypted = encrypted.substr(0, parseInt(rand.substr(0,2)))+this.key+encrypted.substr(parseInt(rand.substr(0,2)), parseInt(rand.substr(2,2)))+this.iv+encrypted.substr(parseInt(rand.substr(0,2))+parseInt(rand.substr(2,2)));
		$('#rsData').val(encrypted);
	}
}