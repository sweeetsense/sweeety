$(document).ready(function(){
	$("#ok").on("click", function(){
		if($("#name").val() == ""){
			alert("부서명을 입력해주세요");
			return;
		}
		$.ajax({
			type:"post",
			url:"jq13Ex2.jsp",
			data:{"busername":$("#name").val()},
			dataType:"json",
			success:function(data){
				//alert("성공");
				var str="<table border=1><tr><th>사번</th><th>직원명</th><th>직급</th><th>관리고객</th>";
				var count = 0;
				$.each(data, function(index, entry){
					if(entry.count>0){
						str += "<tr><td><a href='#' id='gogek'>"+entry.sawon_no+"</a></td>";						
					}else{
						str += "<tr><td>"+entry.sawon_no+"</td>";
					}
					str += "<td>"+entry.sawon_name+"</td>";
					str += "<td>"+entry.sawon_jik+"</td>";
					str += "<td>"+entry.count+"</td>";
					count++;
				});
				str+="</table>"
				$("#disp").empty();
				$("#disp").append(str);
				$("#disp").append("인원수: "+count+"명");
			},
			error:function(){
				alert("에러");
			}
		});
		$(document).on("click","a",function(){
			$.ajax({
				type:"post",
				url:"jq13Ex2gogek.jsp",
				data:{"sawonno":$(this).text()},
				dataType:"json",
				success:function(data){
					var str = "<table border=1><tr><th>고객명</th><th>전화번호</th><th>성별</th><th>나이</th>";
					$.each(data, function(index, entry){
						str += "<tr><td>"+entry.gogek_name+"</td>";
						str += "<td>"+entry.gogek_tel+"</td>";
						str += "<td>"+entry.gogek_gen.trim()+"</td>";
						str += "<td>"+entry.age+"</td>";
					});
					str+="</table>";
					$("#disp2").empty();
					$("#disp2").append(str);
				},
				error:function(){
					alert("에러");
				}
			});
		});
	});
	
		
});
/*function func(sawonnum){
	
	$.ajax({
		type:"post",
		url:"jq13Ex2gogek.jsp",
		data:{"sawonno":sawonnum},
		dataType:"json",
		success:function(data){
			var str = "<table border=1><tr><th>고객명</th><th>전화번호</th><th>성별</th><th>나이</th>";
			$.each(data, function(index, entry){
				str += "<tr><td>"+entry.gogek_name+"</td>";
				str += "<td>"+entry.gogek_tel+"</td>";
				str += "<td>"+entry.gogek_gen.trim()+"</td>";
				str += "<td>"+entry.age+"</td>";
			});
			str+="</table>";
			$("#disp2").empty();
			$("#disp2").append(str);
		},
		error:function(){
			alert("에러");
		}
	});
}*/
