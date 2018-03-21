$(function(){
	autoLogin();
});

function autoLogin(){
	var username = getParam("username");
	var token = getParam("token");
	$.ajax({
		url:"/crm-test/onlineManage/emailAutoLogin",
		data:{username:username,token:token},
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