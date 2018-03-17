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