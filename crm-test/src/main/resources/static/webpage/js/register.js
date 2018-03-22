$(function() {
	
	$('#username').blur(function () {  
		checkUsernameUnique();
	});  
	
	$('#email').blur(function () {  
		checkEmailUnique();
	});
	
});

function checkUsernameUnique(){
	var username = $("#username").val();
	$.ajax({
		url:"/crm-test/onlineManage/checkUsernameUnique",
		data:{username:username},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "00000"){
				$("#alert_msg").html("username is available");
			}else{
				$("#alert_msg").html(data.returnDesc);
			}
        }
	});
}

function checkEmailUnique(){
	
	var email = $("#email").val();
	
	$.ajax({
		url:"/crm-test/onlineManage/checkEmailUnique",
		data:{email:email},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "00000"){
				$("#alert_msg").html("email is available");
			}else{
				$("#alert_msg").html(data.returnDesc);
			}
        }
	});
}

function register(){
	var username = $("#username").val();
	var password = $("#password").val();
	var confirmPassword = $("#confirmPassword").val();
	var mobile = $("#mobile").val();
	var identification = $("#identification").val();
	var email = $("#email").val();
	$.ajax({
		url:"/crm-test/onlineManage/register",
		data:{
			username:username,
			password:password,
			confirmPassword:confirmPassword,
			mobile:mobile,
			identification:identification,
			email:email
		},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "0000"){
				alert("send email successfully , please check email and complete registeration");
				window.location.href = "/crm-test/webpage/login.html";
			}else{
				alert(data.returnDesc);
			}
        }
	});
}