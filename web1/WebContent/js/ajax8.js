var xhr, ele, popup, disp;

function goFunc(element){
	ele = element;
	disp = document.getElementById("disp");
	popup = document.getElementById("popup");
	
	xhr = new XMLHttpRequest();
	xhr.open("post","ajax8.jsp",true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState==4){
			if(xhr.status == 200){
				var datas = xhr.responseXML;
				alert(datas)
				setOffsets();	//툴팁박스 출력 위치 설정 함수
				var make = datas.getElementsByTagName("make")[0].firstChild.nodeValue;
				var row = createRow(make);
				disp.appendChild(row);
				
			}else{
				alert("goFunc 오류: "+xhr.status);
			}
		}
	}
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("buser="+ele.id);
}
function createRow(make){
	var row = document.createElement("tr");
	var col = document.createElement("td");
	var text = document.createTextNode(make);
	col.appendChild(text);
	row.appendChild(col);
	return row;
}

function setOffsets() {
    var end = ele.offsetWidth;
    /*
    부모노드 폭, offsetHeight:높이
    offsetWidth 속성 : offsetParent 설정에 따라 모체의 레이아웃이나 좌표에 상대적으로 계산된 너비 위치를 픽셀단위로 반환한다. 
    */
 
    var top = calcOffset(ele, "offsetTop");
    popup.style.left = end + 15 + "px";
    popup.style.top = top + "px";
    popup.style.border = "black 1px solid";
}
 
function calcOffset(field, attr) {
  var offset = 0;
  while(field) {
    offset += field[attr];    //부모 노드의 offsetTop 속성을 계속 더해 준다.
    field = field.offsetParent;
    /*
    offsetTop 속성 : offsetParent 설정에 따라 모체의 레이아웃이나 좌표에 상대적으로 계산된 수직 위치를 픽셀단        위로 반환한다.
    */
  }
  return offset;
}
function clsFunc(){
	for(var i=disp.childNodes.length-1;i>=0;i--){
		disp.removeChild(disp.childNodes[i]);
	}
	
	popup.style.border = "none";
}