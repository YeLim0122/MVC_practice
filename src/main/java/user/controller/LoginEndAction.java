package user.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.controller.AbstractAction;
import user.model.NotUserException;
import user.model.UserDAO;
import user.model.UserVO;

public class LoginEndAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String userid = req.getParameter("userid");
		String pwd = req.getParameter("pwd");
		String saveId = req.getParameter("saveId");
		
		if (userid==null || pwd==null || userid.trim().isEmpty() || pwd.trim().isEmpty()) {
			this.setViewPage("login.do");
			this.setRedirect(true);
			
			return;
		}
		
		UserDAO userDao = new UserDAO();
		try {
			UserVO user = userDao.loginCheck(userid, pwd);
			// 아이디와 비밀번호가 일치하지 않는다면 NotUserException을 발생시킴.
			HttpSession session = req.getSession();
			if (user != null) {
				// 회원인증을 받았다면 => session에 UserVO를 저장
				session.setAttribute("loginUser", user);
				
				Cookie ck = new Cookie("uid", user.getUserid());
				if (saveId != null) {	// 아이디 저장에 체크했다면
					ck.setMaxAge(7*24*60*60);	// 7일간 유효
				} else {	// 체크하지 않았다면
					ck.setMaxAge(0);	// 쿠키 삭제
				}
				ck.setPath("/");
				res.addCookie(ck);
				
			}	// if ------------------------
			this.setViewPage("index.do");
			this.setRedirect(true);	// 세션에 저장했기 때문에 redirect 이동해도 됨.
		}
		catch(NotUserException e) {
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("loc", "javascript:history.back()");
			this.setViewPage("/message.jsp");
			this.setRedirect(false);
		}
	}

}
