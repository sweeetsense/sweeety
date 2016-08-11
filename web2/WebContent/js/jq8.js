$(document).ready(function(){
	$("#id_error").hide();
	$("#age_error1").hide();
	$("#age_error2").hide();
	$("#p_error1").hide();
	$("#p_error2").hide();
	
	$("input#btnSend").click(function(){
		//id검사
		var id = $("#userid").val();
		//alert(id);
		if(id.length < 1){
			$("#id_error").show();
			return false;
		}else{
			$("#id_error").hide();
		}
		
		//age검사
		var age = $("#age").val();
		if(age.length < 1){
			$("#age_error1").show();
			return false;
		}else{
			$("#age_error1").hide();
		}
		//age 숫자만 허용
		for(var i=0;i<age.length;i++){
			var data = age.charAt(i).charCodeAt(0);
			//alert(data);
			if(data < 48 || data >57){
				$("#age_error2").show();
				break;
			}else{
				$("#age_error2").hide();
			}
		}
		
		//비번 검사
		var pwd1 = $("#pwd1").val();
		if(pwd1.length<1){
			//$("#p_error1").show();
			$("#pwd1").next().show();	//next() ->next sibling
			return false;
		}else{
			//$("#p_error1").hide();
			$("#pwd1").next().hide();
		}
		var pwd2 = $("#pwd2").val();
		if(pwd1 != pwd2){
			$("#pwd2").next().show();
			return false;
		}else{
			$("#pwd2").next().hide();
		}
		
		$("#frm").attr("action", "sfile/jq8.jsp").submit();
	});
});