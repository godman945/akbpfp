$(document).ready(function(){
	
	$('#userInvite').click(function(){
		window.location = "accountUserInvite.html";
	});

	$("#search").click(function(){
		$("#userForm").submit();
	});
	
});

function checkAdm(id){

	$("#userForm").attr("action", "accountAdmModify.html");
	$("#userId").val(id);
	$("#userForm").submit();
}

function modifyUser(id){

	$("#userForm").attr("action", "accountUserModify.html");
	$("#userId").val(id);
	$("#userForm").submit();
}

function checkDelete(id){

	$.fancybox({
		'href'     :'deleteUserMsg.html'		                    
	});
	
	$("#userId").val(id);
	
}

function deleteUser(){
	var id = $("#userId").val();

    // 刪除使用者
	$.ajax({
		url: "accountUserDelete.html",
		data: {
			userId: id
		},
		type: "post",
		dataType: "json",
		success: function(response, state){
			$("#tr_"+id+"").remove();
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！！");
		}
	});
}

function reinvite(id, email){
	
	$.ajax({
		url: "reInviteUser.html",
		data: {
			userId: id
		},
		type: "post",
		dataType: "json",
		success: function(response, state){
			
			if(response.sendMail){
				$.fancybox({
					'href'     :'inviteUserMsg.html?userEmail='+email+"&userId="+id		                    
				});
				$("#a_"+id).remove();
				$("#lab_"+id).html("尚未接受邀請");
			}else{
				$.fancybox({
					'href'     :'inviteUserFailMsg.html'                  
				});
				$("#a_"+id).remove();
				$("#lab_"+id).html("該使用者mail信箱已有關鍵字廣告帳戶");
			}
			
			
		},
		error: function(xtl) {
			alert("系統繁忙，請稍後再試！！");
		}
	});	
}