package board.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.*;
import common.controller.AbstractAction;

public class BoardListAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) 
	throws Exception {
		
		BoardDAO dao = new BoardDAO();
		
		// 총 게시글 수 가져오기
		int totalCount = dao.getTotalCount();
		
		// 글 목록 가져오기
		List<BoardVO> boardArr = dao.listBoard();
		
		req.setAttribute("boardArr", boardArr);
		req.setAttribute("totalCount", totalCount);
		
		this.setViewPage("/board/boardList.jsp");
		this.setRedirect(false);
	}

}
