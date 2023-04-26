package common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import user.model.UserVO;

/**
 * 로그인 여부를 체크하는 필터
 */
@WebFilter({ "/user/*", "/admin/*" })
public class LoginCheckFilter extends HttpFilter implements Filter {

	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		
		// 세션에 저장되어 있는 loginUser가 있는지 체크 => 없으면 return
		UserVO user = (UserVO)session.getAttribute("loginUser");
		if (user == null) {
			String loc = req.getContextPath()+"/login.do";
			
			req.setAttribute("msg", "로그인 해야 이용할 수 있습니다.");
			req.setAttribute("loc", loc);
			
			RequestDispatcher disp = req.getRequestDispatcher("/message.jsp");
			disp.forward(req, response);
			
			return;
		}
		// 로그인했다면 다음 필터 또는 컨트롤러로 넘어감
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
