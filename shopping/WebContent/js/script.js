function zipCheck(){
	url = "zipcheck.jsp?check=y";
	
	window.open(url, "zip", "toolbar=no, width=600, height=300, top=200, left=100, status=yes, scrollbars=yes, menubar=no");
}

function idCheck(){
	if(regForm.id.value === "" ){
		alert("id를 입력하시오");
		regForm.id.focus();
	}else{
		url = "idcheck.jsp?id="+regForm.id.value;
		window.open(url, "id", "width=300, height=150, top=150, left=100");
	}
}

function inputCheck(){
	//입력자료 오류 검사(생략)
	
	document.regForm.submit();
}

function memberUpdate(){
	//쇼핑몰 로그인 후 자신의 정보 수정 시..
	//입력자료 오류 검사(생략)
	document.updateForm.submit();
}

function memberUpdateCancel(){
	//수정 취소 시..
	location.href="../guest/guest_index.jsp";
}

function memberDelete(){
	alert("회원 탈퇴는 나중에..");
}