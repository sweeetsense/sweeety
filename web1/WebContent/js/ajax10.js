var xhr;
var checkFirst = loopSend = false;
var lastKeyword = "";

function sijak(){
	//alert("aa");
	if(checkFirst == false){
		setTimeout("sendKeyword()",1000);
		loopSend = true;
	}
}

function sendKeyword(){
//	alert("kbs");
	if(loopSend == false) return;
	
	var keyWord = document.frs.keyword.value;
	//console.log(keyWord);
	
	if(keyWord == ""){
		lastKeyword = "";
		hide("suggest");
	}else if(keyWord != lastKeyword){
		lastKeyword = keyWord;
		
		if(keyWord != ""){
			var para = "keyword="+keyWord;
			xhr = new XMLHttpRequest();
			xhr.open("post", "ajax10.jsp",true);
			xhr.onreadystatechange = function(){
				 if (xhr.readyState == 4) {
			         if (xhr.status == 200) {
			            process();
			         }else{
			            alert("실패 : " + xhr.status);   
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
	var reData = xhr.responseText;
	//alert(reData);
	var result = reData.split("|");
	//alert("전체 건수" + result[0]);
	//alert("전체 자료" + result[1]);
	if(result[0]>0){
		var datas = result[1].split(",");
		var imsi = "";
		for(var i=0;i<datas.length;i++){
			imsi += "<a href=\"javascript:func('"+datas[i]+"')\">"+datas[i]+"</a><br/>";
		}
		var listView = document.getElementById("suggestlist");
		listView.innerHTML = imsi;
	}
	
	show("suggest");
}

function func(data){
	frs.sel.value = data;
	loopSend = checkFirst = lastKeyword = false;
	lastKeyword = "";
	frs.keyword.value="";
	frs.keyword.focus();
	hide("suggest");
}

function hide(ele){
	var e = document.getElementById(ele);
	if(e) e.style.display = "none";
}

function show(ele){
	var e = document.getElementById(ele);
	if(e) e.style.display = "";
}
