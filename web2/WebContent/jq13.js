$(document).ready(function(){
	$("#btn1").click(function(){		//xml
		$.ajax({
			type:"get",
			url:"jq13_1.jsp",
			dataType:"xml",
			success:function(data){
				//$("#disp").text(data);
				var str="";
				$(data).find("person").each(function(){
					str +=$(this).find("irum").text()+"&nbsp;&nbsp;";
				});
				$("#disp").empty();
				$("#disp").append(str);
			},
			error:function(){
				$("#disp").text("에러 발생")
			},
			statusCode:{
				200:function(){
					//alert("읽기 성공");
				},
				404:function(){
					alert("파일 찾기 실패");
				},
				500:function(){
					alert("파일 오류");
				},
			}
		});
	});
	
	$("#btn2").bind("click",function(){		//xml - para
		var my=$.ajax({
			type:"get",
			url:"jq13_2.jsp",
			data: "irum=" + "관우",       //복수개일 경우 data: "irum=" + "관우" + "&nai=" +"23",
	        //data:{"irum":"장비"},         //자바스크립트 객체 배열타입
			dataType:"xml",
			success:function(data){
				//$("#disp").text(data);
				var str="";
				$(data).find("person").each(function(){
					str +=$(this).find("irum").text()+"&nbsp;&nbsp;";
				});
				$("#disp").empty();
				$("#disp").append(str);
			}
		});
		my.fail(function(status){
			$("#disp").text("처리 실패"+status);
		});
	});
	
	$("#btn3").click(function(){		//json
		$.ajax({
			type:"get",
			url:"jq13_3.jsp",
			dataType:"json",
			success:function(data){
				//alert(data);
				var str="";
				$.each(data, function(index, entry){
					str += entry["name"]+", "+entry.age; 		//entry.age는 자바스크립트에서만 가능
				});
				$("#disp").empty();
				$("#disp").append(str);
			},
			error:function(){
				$("#disp").text("에러 발생")
			}
		});
	});
	
	$("#btn4").click(function(){		//json
		$.ajax({
			type:"post",
			url:"jq13_4.jsp",
			data:{"irum":"유비", "nai":"33"},
			dataType:"json",
			success:function(data){
				//alert(data);
				var str="";
				$.each(data, function(index, entry){
					str += entry.name+","+entry.age; 		//entry.age는 자바스크립트에서만 가능
				});
				$("#disp").empty();
				$("#disp").append(str);
			},
			error:function(){
				$("#disp").text("에러 발생")
			}
		});
	});

});