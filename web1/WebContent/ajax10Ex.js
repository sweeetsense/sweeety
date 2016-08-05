var xhr;
var xhr2;
var xhr3;
var checkFirst = loopSend = false;
var lastKeyword = "";

function sijak(){
	if(checkFirst == false){
		setTimeout("sendKeyword()",1000);
		loopSend = true;
	}
}
function sendKeyword(){
	if(loopSend == false) return;
	
	var keyWord = document.frm.keyword.value;
	//console.log(keyWord);
	if(keyWord == ""){
		lastKeyword = "";
		hide("suggest");
	}else if(keyWord != lastKeyword){
		lastKeyword = keyWord;
		if(keyWord != ""){
			var para = "keyword="+keyWord;
			xhr = new XMLHttpRequest();
			xhr.open("post","ajax10xmlEx.jsp",true);
			xhr.onreadystatechange = function(){
				if(xhr.readyState == 4){
					if(xhr.status == 200){
						process();
					}else{
						alert("실패: "+xhr.status);
					}
				}
			}
			xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xhr.send(para);
		}else{
			hide("suggest");
		}
	}
	setTimeout("sendKeyword()",1000);
}
function process(){
	var reData = xhr.responseXML;
	//alert(reData);
	var sawonNode = reData.getElementsByTagName("sawon");
	var sawon_ibsailNode = reData.getElementsByTagName("sawon_ibsail");
	var sawon_nameNode = reData.getElementsByTagName("sawon_name");
	var str = "";

	for(var i=0;i<sawonNode.length;i++){
		str+= "<a href=\"javascript:func('"+sawon_ibsailNode[i].childNodes[0].nodeValue+"')\">"
		+sawon_ibsailNode[i].childNodes[0].nodeValue+"</a></br>";
	}
	//alert(str);
	document.getElementById("suggest").innerHTML = str;
}
function func(data){
	
	var year = "year="+data;
	//alert(year);
	xhr2 = new XMLHttpRequest();
	xhr2.open("post","ajax10xml2Ex.jsp",true);
	xhr2.onreadystatechange = function(){
		if(xhr2.readyState == 4){
			if(xhr2.status == 200){
				process2();
			}else{
				alert("실패2: "+xhr2.status);
			}
		}
	}
	xhr2.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr2.send(year);
}
function process2(){
	var datas = xhr2.responseXML; 
	var sawonNode = datas.getElementsByTagName("sawon");
	var sawon_nameNode = datas.getElementsByTagName("sawon_name");
	var str = "";
	var sel_obj = document.getElementById("selbox");
	for(var i=0; i < sel_obj.options.length; i++) 
		sel_obj.options[i] = null;

	for(var i=0;i<sawon_nameNode.length;i++){
		document.getElementById("selbox").options[i] = new Option(sawon_nameNode[i].childNodes[0].nodeValue,sawon_nameNode[i].childNodes[0].nodeValue);
	}
	//alert(str);
}	

function gogek(){
	var sawon_name= "sawon_name="+document.getElementById("selbox").value;
	xhr2 = new XMLHttpRequest();
	xhr2.open("post","ajax10xml3Ex.jsp",true);
	xhr2.onreadystatechange = function(){
		if(xhr2.readyState == 4){
			if(xhr2.status == 200){
				process3();
			}else{
				alert("실패2: "+xhr2.status);
			}
		}
	}
	xhr2.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr2.send(sawon_name);
}
function process3(){
	var datas = xhr2.responseXML;
	var gogekNode = datas.getElementByTagName("gogek");
	var gogek_noNode = datas.getElementsByTagName("gogek_no");
	var gogek_nameNode = datas.getElementsByTagName("gogek_name");
	var gogek_juminNode = datas.getElementsByTagName("gogek_jumin");
	var gogek_telNode = datas.getElementsByTagName("gogek_tel");
	alert(gogek_noNode.length);
	var str = "";
	
}
function hide(ele){
	var e = document.getElementById(ele);
	if(e) e.style.display= "none";
}
function show(ele){
	var e = document.getElementById(ele);
	if(e) e.style.display="";
}