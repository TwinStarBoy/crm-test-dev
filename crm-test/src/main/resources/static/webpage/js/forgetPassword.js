function forgetPassword(){
	var email = $("#email").val();
	
	$.ajax({
		url:"/crm-test/onlineManage/forgetPassword",
		data:{email:email},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "0000"){
				$("#forgetPassword_message").text("send email successfully , please check it than reset your password");
			}else{
				alert(data.returnDesc);
			}
        }
	});
}