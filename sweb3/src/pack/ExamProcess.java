package pack;

public class ExamProcess {
	private ExamBean examBean;
	
	public void setExamBean(ExamBean examBean) {
		this.examBean = examBean;
	}
	
	public int getTot(){
		return examBean.getKor() + examBean.getEng();
	}
	
	public double getAvg(){
		return (examBean.getKor() + examBean.getEng()) / 2.0;
	}
}
