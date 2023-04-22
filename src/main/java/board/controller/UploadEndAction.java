package board.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.controller.AbstractAction;

// 파일 업로드 라이브러리 => cos.jar

import com.oreilly.servlet.*;
import com.oreilly.servlet.multipart.*;

public class UploadEndAction extends AbstractAction {

	String upDir = "C:/myjava/Upload";
	
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		// 동일한 파일명이 있을 경우 "파일명+인덱스번호" 식으로 업로드 시킴.
		DefaultFileRenamePolicy df = new DefaultFileRenamePolicy();
		
		MultipartRequest mr = new MultipartRequest(req, upDir, 
								100*1024*1024, "utf-8", df);
		// 업로드 파일 최대 용량을 100mb로 설정 => 초과하면 예외 발생
		System.out.println("파일 업로드 성공!"+upDir+"에서 확인하세요.");
		
		req.setAttribute("msg", "파일 업로드 성공!"+upDir+"에서 확인하세요.");
		
		// String writer = req.getParameter("writer");	// X 안나옴
		String writer = mr.getParameter("writer");
		System.out.println("writer: "+writer);
		
		// 첨부파일명
		String fname = mr.getFilesystemName("fname");
		System.out.println("fname: "+fname);
		
		// 첨부파일 크기
		File file = mr.getFile("fname");
		long fsize = 0;
		if (file != null) {
			fsize = file.length();
			System.out.println("fsize: "+fsize);
		}
		
		req.setAttribute("fname", fname);
		req.setAttribute("fsize", fsize);
		req.setAttribute("writer", writer);
		
		this.setViewPage("/board/uploadResult.jsp");
		this.setRedirect(false);
	}

}
