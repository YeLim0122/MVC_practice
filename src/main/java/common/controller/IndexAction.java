package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IndexAction extends AbstractAction {
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		System.out.println("IndexAction의 execute()호출됨..");
		
		req.setAttribute("msg", "From IndexAction");
		// 뷰페이지 지정
		this.setViewPage("/index.jsp");
		
		// 이동방식 지정
		this.setRedirect(false);
		
	}
}
