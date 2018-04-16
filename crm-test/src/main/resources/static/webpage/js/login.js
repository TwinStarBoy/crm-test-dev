function login(){
	var username = $("#username").val();
	var password = $("#password").val();
	$.ajax({
		url:"/crm-test/onlineManage/login?accessToken=2332",
		data:{username:username,password:password},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "0000"){
				window.location.href = "loginSuccess.html?username=" + data.lists[0].username;
			}else{
				alert(data.returnDesc);
			}
        }
	});
}

