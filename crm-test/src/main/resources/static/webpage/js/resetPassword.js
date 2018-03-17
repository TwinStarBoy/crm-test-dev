function resetPassword(){
	var newPassword = $("#newPassword").val();
	
	var confirmPassword = $("#confirmPassword").val();
	
	var email = getParam("email");
	
	var token = getParam("token");
	
	$.ajax({
		url:"/crm-test/onlineManage/resetPassword",
		data:{email:email,token:token,newPassword:newPassword,confirmPassword:confirmPassword},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "00000"){
				alert("reset password successfully");
				
				var username = data.lists[0].username;
				
				autoLogin(username,newPassword)
				
			}else{
				alert(data.returnDesc);
			}
        }
	});
}


function autoLogin(username,newPassword){
	
	$.ajax({
		url:"/crm-test/onlineManage/login",
		data:{username:username,password:newPassword},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "0000"){
				window.location.href = "/crm-test/webpage/loginSuccess.html?username=" + data.lists[0].username;
			}else{
				alert(data.returnDesc);
			}
        }
	});
}