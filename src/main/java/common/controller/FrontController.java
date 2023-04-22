package common.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*FrontController : *.do 패턴의 모든 요청을 받아들인다.
 * - Command.properties 파일에 있는 매핑 정보를 읽어들여 해당 요청uri와 매핑되어 있는
 *   SubController(XXXAction)을 찾아 객체화 한 뒤 Hash Map에 저장하고,
 *   해당 객체의 메소드(execute)를 호출한다.
 * - 서브 컨트롤러는 해당 작업을 수행한 뒤에 다시 FrontController로 돌아와 보여줘야 할 View
 *   페이지(JSP) 정보를 넘긴다.
 * - FrontController는 해당 뷰페이지로 이동시킨다. (forward방식 이동 or redirect방식 이동) 
 * 
 * init() --> service() --> destroy()	=> init과 destroy는 한번만 호출
 * 			  service()
 * 				...
 * */

@WebServlet(
		urlPatterns = { "*.do" }, 
		initParams = { 
				@WebInitParam(name = "config", 
						value = "C:\\myjava\\workspace\\MVCWeb\\src\\main\\webapp\\WEB-INF\\Command.properties")
		})
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private HashMap<String, Object> cmdMap = new HashMap<>();
	
	
	@Override
	public void init(ServletConfig conf) throws ServletException {
		System.out.println("init()...");
		String props = conf.getInitParameter("config");
		System.out.println("props === "+props);
		
		Properties pr = new Properties();
		try {
			FileInputStream fis = new FileInputStream(props);
			pr.load(fis);
			fis.close();
			// String val = pr.getProperty("/index.do");
			// System.out.println("val: "+val);
			
			Set<Object> set = pr.keySet();
			if (set == null) return;
			for (Object key:set) {
				String cmd = key.toString();	// "/index.do"
				String className = pr.getProperty(cmd);	
				// ==> "common.controller.IndexAction"
				
				if (className != null) {
					className = className.trim();
				}
				System.out.println(cmd+": "+className);
				
				// className을 실제 객체로 인스턴스화
				Class<?> cls = Class.forName(className);
				Object cmdInstance = cls.getDeclaredConstructor().newInstance(); 
				// 문자열로 구성된 클래스명의 객체를 생성해준다.
				
				///////////////////////////////
				cmdMap.put(cmd, cmdInstance);
				//////////////////////////////
			}	// for ---------------------
			
			System.out.println("cmdMap.size(): "+cmdMap.size());
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);	//브라우저에 에러 출력
		}
		
	}	// init() ----------------------------
	
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

	
	private void process(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		// System.out.println("process()");
		
		// 1) 클라이언트의 요청 URI를 분석해보자
		String cmd = req.getServletPath();
		System.out.println("cmd: "+cmd);
		
		Object instance = cmdMap.get(cmd);
		if (instance == null) {
			System.out.println("Action이 null");
			throw new ServletException("Action이 null입니다.");
		}
		System.out.println("instance ==> "+instance);
		
		/////////////////////////////////////////////
		AbstractAction action = (AbstractAction)instance;
		////////////////////////////////////////////
		
		try {
			action.execute(req, res);
			String viewPage=action.getViewPage();	// 뷰페이지 얻기
			boolean isRedirect = action.isRedirect();	// 이동방식 얻기
			
			if (isRedirect) {
				// redirect방식 이동
				res.sendRedirect(viewPage);
			}
			else {
				// forward 이동
				RequestDispatcher disp = req.getRequestDispatcher(viewPage);
				disp.forward(req, res);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
		
	}	// -------------------------------------------


	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		process(req, res);
	}

}
