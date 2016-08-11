$(document).ready(function(){
	$("#btnAll").click(function(){
		$("#disp").empty();
		$.ajax({
			type:"post",
			url:"jq13Ex.jsp",
			dataType:"xml",
			success:function(data){
				var str="코드 상품명 수량 단가 금액<br/>";
				var count=0;
				//alert(data);
				$(data).find("sangpum").each(function(){
					str+= $(this).find("code").text()+" ";
					str+= $(this).find("sang").text()+" ";
					str+= $(this).find("su").text()+" ";
					str+= $(this).find("dan").text()+" ";
					str+= $(this).find("su").text() * $(this).find("dan").text()+"<br/>"
					count++;
				});
				$("#disp").append(str);
				$("#disp").append("총 "+count+"건 ");
			},
			error:function(){
				alert("에러 발생"+status);
			}
		});
	});
	$("#search").on("click",function(){
		$("#disp").empty();
		if($("#name").val() == ""){
			alert("상품명을 입력해주세요");
			return;
		}
		var err=$.ajax({
			type:"post",
			url:"jq13Ex.jsp",
			data:"name="+$("#name").val(),
			dataType:"xml",
			success:function(data){
				var str="코드 상품명 수량 단가 금액<br/>";
				$(data).find("sangpum").each(function(){
					str+=$(this).find("code").text()+" ";
					str+=$(this).find("sang").text()+" ";
					str+=$(this).find("su").text()+" ";
					str+=$(this).find("dan").text()+" ";
					str+=$(this).find("su").text() * $(this).find("dan").text()+"<br/>"
				});
				$("#disp").append(str);
			}
		});
		err.fail(function(statusCode){
			alert("처리실패: "+statusCode);
		});
	});
});