package common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import user.model.UserVO;

abstract public class AbstractAction implements Action {
	
	// execute() 추상메소드를 가짐
	private String viewPage;	// 보여줄 뷰 페이지 이름
	private boolean isRedirect;	// true이면 redirect 이동, false면 forward 이동
	
	public UserVO getLoginUser(HttpServletRequest req) {
		// 세션 얻어오기
		HttpSession ses = req.getSession();
		UserVO user = (UserVO)ses.getAttribute("loginUser");
		return user;
	}
	
	
	// setter, getter
	public String getViewPage() {
		return viewPage;
	}
	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	
	public boolean isRedirect() {
		return isRedirect;
	}
	public void setRedirect(boolean isRedirect) {
		this.isRedirect = isRedirect;
	}
	
}// -----------------------------
