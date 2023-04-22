package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// 상속
// 재정의
// req에 저장 msg, "From TestAction"
// hello.jsp 뷰페이지 지정, forward 이동하도록 설정

public class TestAction extends AbstractAction {
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res)
	throws Exception {
		
		req.setAttribute("msg", "From TestAction");
		
		// 뷰페이지 지정
		this.setViewPage("/hello.jsp");
		// 이동방식 지정
		this.setRedirect(false);
	}

}
