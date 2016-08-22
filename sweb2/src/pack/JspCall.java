package pack;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

@WebServlet("/irum.go")
public class JspCall extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String data = request.getParameter("data");
		//넘어온 자료로 이런저런 일을 하다가..
		
		//파일을 호출하는 방법 1: redirect - client를 통해 server에 있는 파일 호출
		//배열안됨, string만 가능
		//response.sendRedirect("jspcall.jsp?data="+data);
		
		//파일을 호출하는 방법 2: forward - server에서 server에 있는 파일 호출
		//배열, 숫자 기타등등 인자값을 넘길 수 있다.
		request.setAttribute("mydata", data+" 안녕");
		/*RequestDispatcher dispatcher = request.getRequestDispatcher("jspcall.jsp");		//싱글톤
		dispatcher.forward(request, response);		//인자 값으로 받아온 request를 그대로 jsp에 전송
		*/
		request.getRequestDispatcher("jspcall.jsp").forward(request, response);
	}

}
