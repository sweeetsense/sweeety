package pack;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class world extends HttpServlet{
	@Override
	public void init() throws ServletException {
		System.out.println("초기화 1회 수행");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("안녕은 호출 시 매번 수행");
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("post방식으로 호출 시 매번 수행");
	}
	
	@Override
	public void destroy() {
		System.out.println("서비스 종료 시 1회수행");
	}
	
}
