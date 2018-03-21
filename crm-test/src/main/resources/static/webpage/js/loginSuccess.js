$(function(){
	isEmailVerify();
});

function logout(){
	
	
	$.ajax({
		url:"/crm-test/onlineManage/logout",
		success:function(result){
			window.location.href = "/crm-test/webpage/login.html";;
        }
	});
}

function modify(){
	window.location.href = "/crm-test/webpage/modifyPersonalInformation.html?username="+getParam("username");
}

function isEmailVerify(){
	
	var username = getParam("username");
	
	$.ajax({
		url:"/crm-test/onlineManage/isVerifyEmail",
		data:{username:username},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "00000"){
				if(data.lists[0].isEmailVerify == "0"){
					$("#email_verify_msg").html("email is not verified , <input type='button' value='send verifed email' onclick='sendVerifiedEmailReq()'/>");
				}
			}else{
				alert(data.returnDesc);
			}
        }
	});
}

function sendVerifiedEmailReq(){
	
	var username = getParam("username");
	
	$.ajax({
		url:"/crm-test/onlineManage/sendVerifyEmailReq",
		data:{username:username},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "00000"){
				alert(data.returnDesc);
			}else{
				alert(data.returnDesc);
			}
        }
	});
}