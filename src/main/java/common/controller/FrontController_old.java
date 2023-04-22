package common.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FrontController

@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "config", value = "파일경로")
		}) */
public class FrontController_old extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

	
	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// System.out.println("process()");
		
		// 1) 클라이언트의 요청 URI를 분석해보자
		String uri = req.getRequestURI();
		System.out.println("uri: "+uri);
		// uri: /MVCWeb/index.do ==> index.jsp 보여주기
		//		/MVCWeb/test.do  ==> hellow.jsp 보여주기
		
		String ctx = req.getContextPath();	// "/MVCWeb"
		String cmd = uri.substring(ctx.length());
		System.out.println("cmd: "+cmd);
		
		boolean isRedirect = true;
		String viewPage = "";
		
		if(cmd.equals("/index.do")) {
			req.setAttribute("msg", "Hi MVCWeb Index Page");
			viewPage="index.jsp";
		}
		else if(cmd.equals("/test.do")) {
			req.setAttribute("msg", "Hello MVCWeb Hello Page");
			viewPage="hello.jsp";
		}
		// 페이지 이동 -----------
		if (isRedirect) {
			// redirect 방식으로 이동 ==> 브라우저 url을 바꾸고, 새로운 request를 만듦.
			res.sendRedirect(viewPage);
		}
		else {
			// forward 방식으로 이동 ==> 서버 내부에서 이동. 브라우저 url 바뀌지 않음.
			RequestDispatcher disp = req.getRequestDispatcher(viewPage);
			
			disp.forward(req, res);
		}
		
	}


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

}
