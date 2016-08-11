$(document).ready(function(){
	$("#addForm").hide();
	$("#delForm").hide();
	$("#code_err1").hide();
	$("#code_err2").hide();
	$("#sang_err").hide();
	$("#su_err").hide();
	$("#dan_err").hide();
	$("#dcode_err").hide();
	
	$("#btn_addSangpum").click(function(){
		$("#addForm").slideToggle("fast");
		$("#txtCode").focus();
	});
	$("#btn_delSangpum").click(function(){
		$("#delForm").slideToggle("fast");
		$("#delCode").focus();
	});
	
	$("#btn_dispAll").click(dispAll);		//전체 자료 보기
	$("#btn_insertData").click(insertData);		//추가
	$("#btn_deleteData").click(deleteData);		//삭제
	
	$("#btn_insertCancel").click(function(){		//추가취소
		$("#txtCode").val("");
		$("#txtSang").val("");
		$("#txtSu").val("");
		$("#txtDan").val("");
		$("#addForm").slideToggle("fast");
	});
	$("#btn_deleteCancel").click(function(){		//식제취소
		$("#delCode").val("");
		$("#delForm").slideToggle("fast");
	});
	
});

function dispAll(){
	$.getJSON("jq15all.jsp", function(data){
	      $("#disp").empty();
	      //alert(data);
	      var str = "<table><tr><th>코드</th><th>품명</th><th>수량</th><th>단가</th></tr>";
	      $.each(data, function(index, enter){
	    	  str += "<tr>";
	    	  str += "<td>"+enter.code+"</td>"
	    	  str += "<td>"+enter.sang+"</td>"
	    	  str += "<td>"+enter.su+"</td>"
	    	  str += "<td>"+enter.dan+"</td>"
	    	  str+="</tr>";
	      });
	      str += "</table>";
	      $("#disp").append(str);
	   });
	
}
function insertData(){
	var code = $("#txtCode").val();
	var sang = $("#txtSang").val();
	var su = $("#txtSu").val();
	var dan = $("#txtDan").val();
	
	$("#code_err1").hide();
	$("#code_err2").hide();
	//code check
	if(code.length < 1){
		$("#code_err1").show();
		return false;
	}else{
		$("#code_err1").hide();
	}
	//숫자 여부 생략...
	
	//sang check
	if(sang.length < 1){
		$("#sang_err").show();
		return false;
	}else{
		$("#sang_err").hide();
	}
	
	//su check
	if(isNaN(su) || su.length<1){
		$("#su_err").show();
		return false;
	}else{
		$("#su_err").hide();
	}
	
	//dan check
	if(isNaN(dan) || dan.length<1){
		$("#dan_err").show();
		return false;
	}else{
		$("#dan_err").hide();
	}
	
	//추가작업 진행...
	$.ajax({
		type:"post",
		url:"jq15insert.jsp",
		data:{"code":code,"sang":sang,"su":su,"dan":dan},
		success:function(data){
			//alert(data);
			if(data.trim() === "f"){
				alert("상품추가 실패!");
				return false;
			}
			alert("상품 추가 성공!");
			$("#txtCode").val("");
			$("#txtSang").val("");
			$("#txtSu").val("");
			$("#txtDan").val("");
			dispAll();		//추가 후 전체자료 보기
		},
		error:function(){
			alert("상품추가에러");
		}
	});
}
function deleteData(){
	var dcode = $("#delCode").val();
	if(isNaN(dcode) || dcode.length < 1){
		$("#dcode_err").show();
		return false;
	}else{
		$("#dcode_err").hide();
	}
	//삭제 작업 진행
	$.ajax({
		type:"post",
		url:"jq15delete.jsp",
		data:{"dcode":dcode},
		success:function(data){
			//alert(data);
			if(data.trim() === "f"){
				alert("상품 삭제 실패!");
				return false;
			}
			alert("상품 삭제 성공!");
			$("#delCode").val("");
			$("#delForm").slideToggle("fast");
			dispAll();		//삭제 후 전체자료 보기
		},
		error:function(){
			alert("상품삭제에러");
		}
	});
}