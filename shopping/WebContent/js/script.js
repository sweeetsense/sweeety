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

function memUpdate(id){
	//관리자에서 회원 수정할 때
	//alert(id);
	document.updateFrm.id.value = id;
	document.updateFrm.submit();
}

function memberUpdateAdmin(){
	document.updateFormAdmin.submit();
}

function memberUpdateCancelAdmin(){
	location.href = "membermanager.jsp";
}

function productDetail(no){
	//관리자에서 상품 처리 시
	//alert(no);
	document.detailFrm.no.value=no;
	document.detailFrm.submit();
}

function productUpdate(no){
	document.updateFrm.no.value=no;
	document.updateFrm.submit();
}

function productDelete(no){
	if(confirm("정말 삭제할까요?")){
		document.deleteFrm.no.value=no;
		document.deleteFrm.submit();
	}
}

function cartUpdate(form){
	//카트 처리용
	form.flag.value="update";
	form.submit();
}

function cartDelete(form){
	form.flag.value="del";
	form.submit();
}

function orderDetail(no){
	//관리자 주문 상세보기
	document.detailFrm.no.value=no;
	document.detailFrm.submit();
}

function orderUpdate(form){
	document.detailFrm.flag.value="update";
	document.detailFrm.submit();
}

function orderDelete(form){
	document.detailFrm.flag.value="delete";
	document.detailFrm.submit();
}
