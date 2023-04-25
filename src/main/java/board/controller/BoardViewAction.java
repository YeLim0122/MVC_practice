package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardVO;
import common.controller.AbstractAction;

public class BoardViewAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 1. 글 번호 (num) 받기
		String numStr = req.getParameter("num");
		
		// 2. 유효성 체크 => boardList.do로 redirect 이동
		if (numStr == null || numStr.trim().isEmpty()) {
			this.setViewPage("boardList.do");
			this.setRedirect(true);		// FrontController가 이동
			return;
		}
		int num = Integer.parseInt(numStr.trim());
		
		BoardDAO dao = new BoardDAO();
		
		// 3_1. 조회수 증가
		boolean b = dao.updateReadnum(num);
		
		// 3. dao의 viewBoard(글번호) 메소드 호출
		BoardVO board = dao.viewBoard(num);
		
		// 4. req에 저장
		req.setAttribute("board", board);	// 키값으로 el표현식 출력하기 때문에 중요!
		
		// 5. 뷰 페이지 지정, 이동 방식 지정
		this.setViewPage("/board/boardView.jsp");
		this.setRedirect(false);

	}

}
