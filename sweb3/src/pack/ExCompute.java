package pack;

public class ExCompute {
	private ExBean exbean;
	
	public void setExbean(ExBean exbean) {
		this.exbean = exbean;
	}
	
	public int getRp(){
		return (int)(exbean.getPrice()-(exbean.getPrice()*exbean.getDiscount()*0.01));
	}
	
}
