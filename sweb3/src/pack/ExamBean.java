package pack;

public class ExamBean {		//formbean: client 에서 전달 되는 값이 복수일 결우 이를 클래스 type으로 처리
	private String name;
	private int kor, eng;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	
	
}
