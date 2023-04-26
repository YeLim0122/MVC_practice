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
		
		// 0. 현재 보여줄 페이지(cpage)값 받기
		String cpStr = req.getParameter("cpage");
		if (cpStr==null || cpStr.trim().isEmpty()) {
			cpStr="1";	// 1페이지를 기본값으로 설정
		}
		int cpage = Integer.parseInt(cpStr.trim());
		
		if (cpage < 1) {
			cpage = 1;	// 1페이지를 기본 값으로 설정
		}
		
		BoardDAO dao = new BoardDAO();
		
		// 1. 총 게시글 수 가져오기
		int totalCount = dao.getTotalCount();
		
		// 2. 한 페이지 당 보여줄 목록 개수 정하기
		int pageSize = 5;
		
		// 3. 페이지 수 구하기
		/**
		 * totalCount	pageSize	pageCount
			1~4,5		  	5			1
			6~9,10		  	5			2
			11~14,15		5			3
			16~19,20		5			4
		
		/////////////////////////////////////////
		if(totalCount%pageSize==0)
			pageCount=totalCount/pageSize;
		else
			pageCount=totalCount/pageSize +1
		/////////////////////////////////////////////
		==> 1줄로 줄여보자
		pageCount=(totalCount-1)/pageSize+1;
		 */
		int pageCount = (totalCount-1)/pageSize+1;
		
		if (pageCount <= 0) {
			pageCount = 1;
		}
		if (cpage > pageCount) {
			cpage = pageCount;	// 마지막 페이지로 설정
		}
		
		int end = cpage*pageSize;
		int start = end-(pageSize-1);
		
		// 글 목록 가져오기
		// List<BoardVO> boardArr = dao.listBoard();	// <= 페이징 처리 하지 않을 때
		List<BoardVO> boardArr = dao.listBoard(start, end);	// <= 페이징 처리 시
		
		req.setAttribute("boardArr", boardArr);
		req.setAttribute("totalCount", totalCount);
		req.setAttribute("pageSize", pageSize);
		req.setAttribute("pageCount", pageCount);
		req.setAttribute("cpage", cpage);
		
		this.setViewPage("/board/boardList.jsp");
		this.setRedirect(false);
	}

}
