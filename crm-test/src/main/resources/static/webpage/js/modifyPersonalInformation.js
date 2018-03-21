
function scanPersonalInformation(){
	var username = getParam("username");
	
	$.ajax({
		url:"/crm-test/customerEdit/scanPersonalInformation",
		data:{username:username},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "0000"){
				$("#username").val(data.lists[0].username);
				$("#password").val(data.lists[0].password);
				$("#mobile").val(data.lists[0].mobile);
				$("#identification").val(data.lists[0].identification);
				$("#id").val(data.lists[0].id);
			}else{
				alert(data.returnDesc);
			}
        }
	});
}


function modifyPersonalInformation(){
	var id = $("#id").val();
	var username = $("#username").val();
	var password = $("#password").val();
	var mobile = $("#mobile").val();
	var identification = $("#identification").val();
	
	$.ajax({
		url:"/crm-test/customerEdit/modifyPersonalInformation",
		data:{username:username,password:password,mobile:mobile,identification:identification,id:id},
		type: 'POST',
		success:function(data){
			console.log(data);
			if(data.returnCode == "0000"){
				alert("modify successfully");
				scanPersonalInformation();
			}else{
				alert(data.returnDesc);
			}
        }
	});
}