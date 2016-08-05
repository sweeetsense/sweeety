var xhr, ele, popup, disp, disp2, popup2, file;
file="ajax8Exxml.jsp";

function XML(){
	file = "ajax8Exxml.jsp";
}
function JSON(){
	file = "ajax8Exjson.jsp";
}
function goFunc(element){
	ele = element;
	disp = document.getElementById("disp");
	popup = document.getElementById("popup");
	disp2 = document.getElementById("disp2");
	popup2 = document.getElementById("popup2");
//	alert(ele.id);
	
	xhr = new XMLHttpRequest();
	
	xhr.open("post", file, true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4){
			if(xhr.status == 200){
				process1();
			}
		}
	}
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("gen="+ele.id);
}

function process1(){
	var datas = xhr.responseXML;
	var gogekNode = datas.getElementsByTagName("gogek");
	var gogek_nameNode = datas.getElementsByTagName("gogek_name");
	var gogek_telNode = datas.getElementsByTagName("gogek_tel");
	var sawon_nameNode = datas.getElementsByTagName("sawon_name");
	var buser_telNode = datas.getElementsByTagName("buser_tel");
	
	var str = "<table border=1><tr><th>고객명</th><th>고객전화</th><th>관리직원명</th><th>부서전화</th></tr>";
	for(var i=0;i<gogekNode.length;i++){
		str += "<tr><td>"+gogek_nameNode[i].childNodes[0].nodeValue+"</td>"+
		"<td>"+gogek_telNode[i].childNodes[0].nodeValue+"</td>"+
		"<td>"+sawon_nameNode[i].childNodes[0].nodeValue+"</td>"+
		"<td>"+buser_telNode[i].childNodes[0].nodeValue+"</td></table>";
	}
	document.getElementById("disp").innerHTML = str;
}
function clsFunc(){
	for(var i=disp.childNodes.length-1;i>=0;i--){
		disp.removeChild(disp.childNodes[i]);
	}
	
}
