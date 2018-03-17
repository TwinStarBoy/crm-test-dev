function emailVerify(){

	var email = getParam("email");
	var emailVerifyCode = $("#emailVerifyCode").val();
	$.ajax({
		url:"/crm-test/onlineManage/emailVerify",
		data:{emailVerifyCode:emailVerifyCode,email:email},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "0000"){
				alert(data.returnDesc);
				window.location.href = "/crm-test/webpage/loginSuccess.html";
			}else{
				alert(data.returnDesc);
			}
        }
	});
}