package board.controller;

import javax.servlet.http.*;
import common.controller.AbstractAction;
import user.model.UserVO;

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;
import java.io.*;
import board.model.*;

public class BoardEditAction extends AbstractAction {

	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 0. post방식일 때 한글 처리 ==> EncodingFilter 통해 처리
		// 1. 파일 업로드 처리
		String upDir = req.getServletContext().getRealPath("/upload");
		System.out.println(upDir);
		File dir = new File(upDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}
		DefaultFileRenamePolicy df = new DefaultFileRenamePolicy();
		MultipartRequest mr = new MultipartRequest(req, upDir, 100*1024*1024, "utf-8", df);
		System.out.println("업로드 성공!");
		
		// 2. 수정한 값 얻어오기
		String numStr = mr.getParameter("num");
		String subject = mr.getParameter("subject");
		
		// 3. 유효성 체크
		if (numStr == null || subject == null || numStr.trim().isEmpty() ||
			subject.trim().isEmpty()) {
				this.setViewPage("../boardList.do");
				this.setRedirect(true);
				return;
			}
		
		UserVO user = this.getLoginUser(req);
		
		String content = mr.getParameter("content");
		String userid = user.getUserid();
		String filename = mr.getFilesystemName("filename");
		long filesize = 0;
		File file = mr.getFile("filename");
		if (file != null ) {
			// 새로 첨부한 파일이 있다면
			filesize = file.length();	// 파일 크기
			// 예전에 첨부한 파일명 얻기
			String old_file = mr.getParameter("old_file");
			if (old_file != null && !old_file.trim().isEmpty()) {
				// 서버에서 예전 파일 지우기
				File delFile = new File(upDir, old_file);
				if (delFile != null) {
					boolean b = delFile.delete();
					System.out.println("파일 삭제여부: "+b);
				}
			}
			
		}	// if -------------------
		// 2번에서 받은 값 VO에 담기
		int num = Integer.parseInt(numStr.trim());
		
		BoardVO vo = new BoardVO(num, userid, subject, content, null, 0, 
								filename, filesize);
	
		BoardDAO dao = new BoardDAO();
		int n = dao.updateBoard(vo);
		
		String str = (n>0)? "수정 성공":"수정 실패";
		String loc = (n>0)? "../boardList.do":"javascript:history.back()";
		
		req.setAttribute("msg", str);
		req.setAttribute("loc", loc);
		
		this.setViewPage("/message.jsp");
		this.setRedirect(false);
		
	}

}
