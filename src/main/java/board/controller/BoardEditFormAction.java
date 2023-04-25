package board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardVO;
import common.controller.AbstractAction;

public class BoardEditFormAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		String numStr = req.getParameter("num");
		
		if (numStr == null || numStr.trim().isEmpty()) {
			this.setViewPage("../boardList.do");
			this.setRedirect(true);
			return;
		}
		int num = Integer.parseInt(numStr);
		
		BoardDAO dao = new BoardDAO();
		BoardVO board = dao.viewBoard(num);
		
		req.setAttribute("board", board);
		this.setViewPage("/board/boardEdit.jsp");
		this.setRedirect(false);

	}

}
