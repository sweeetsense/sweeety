package pack;

public class gugudan {
	private static gugudan gugudan = new gugudan();
	
	public static gugudan getInstance(){
		return gugudan;
	}
	
	public gugudan() {
		// TODO Auto-generated constructor stub
	}
	
	public int[] compute(int dan){
		int[] gu = new int[9];
		for(int i=1;i<10;i++){
			gu[i-1] = dan*i;
		}
		return gu;
	}
}
