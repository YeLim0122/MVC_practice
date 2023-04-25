package board.model;

import java.sql.*;
import java.util.*;

import common.util.DBUtil;

public class BoardDAO {
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public int insertBoard(BoardVO vo) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			StringBuilder buf = new StringBuilder("INSERT into board(num,")
					.append(" userid, subject, content, wdate, readnum, ")
					.append(" filename, filesize) VALUES(board_seq.nextval,")
					.append(" ?, ?, ?, systimestamp, 0, ?, ?)");
			String sql = buf.toString();
			
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getUserid());
			ps.setString(2, vo.getSubject());
			ps.setString(3, vo.getContent());
			ps.setString(4, vo.getFilename());
			ps.setLong(5, vo.getFilesize());
			
			return ps.executeUpdate();
			
		} finally {
			close();
		}
	}	// insertBoard() ---------------------------
	
	public List<BoardVO> listBoard() throws SQLException {
		try {
			con = DBUtil.getCon();
			
			String sql = "SELECT * FROM board ORDER BY num DESC";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			return makeList(rs);
		}
		finally {
			close();
		}
	}	// listBoard() -------------------------
	
	
	public List<BoardVO> makeList(ResultSet rs) throws SQLException {
		List<BoardVO> arr = new ArrayList<>();
		while(rs.next()) {
			int num = rs.getInt("num");
			String userid = rs.getString("userid");
			String subject = rs.getString("subject");
			String content = rs.getString("content");
			Timestamp wdate = rs.getTimestamp("wdate");
			int readnum = rs.getInt("readnum");
			String filename = rs.getString("filename");
			long filesize = rs.getLong("filesize");
			
			BoardVO vo = new BoardVO(num, userid, subject, content, wdate,
									readnum, filename, filesize);
			arr.add(vo);
		}
		
		return arr;
	}	// makeList() --------------------------

	public int getTotalCount() throws SQLException {
		try {
			con = DBUtil.getCon();
			
			String sql = "SELECT count(num) FROM board";
			
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			int cnt = rs.getInt(1);
			return cnt;
			
		} finally {
			close();
		}
	}	// getTotalCount() -------------------------

	/**
	 * 글번호(num)으로 글 내용 보기
	 */
	public BoardVO viewBoard(int num) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			String sql = "SELECT * FROM board WHERE num=?";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			rs = ps.executeQuery();
			
			List<BoardVO> arr = makeList(rs);
			if (arr==null || arr.size()==0) {
				return null;
			}
			return arr.get(0);
			
//			if (arr != null && arr.size() == 1) {
//				BoardVO board = arr.get(0);
//				return board;
//			}
//			return null;
			
			
		} finally {
			close();
		}
	}	// viewBoard() ---------------------------
	
	/** 글 삭제 */
	public int deleteBoard(int num) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			String sql = "DELETE FROM board WHERE num=?";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			
			return ps.executeUpdate();
			
		} finally {
			close();
		}
	}	// deleteBoard() ------------------------------
	
	public int updateBoard(BoardVO vo) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			StringBuilder buf = new StringBuilder("UPDATE board SET subject=?,")
					.append(" content=?, wdate=systimestamp");
					// 첨부파일이 있다면
					if(vo.getFilename() != null && !vo.getFilename().trim().isEmpty()) {
						buf.append(", filename=?, filesize=?");
					}
					buf.append(" WHERE num=?");
			String sql = buf.toString();
			System.out.println(sql);
			
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.getSubject());
			ps.setString(2, vo.getContent());
			if(vo.getFilename() != null && !vo.getFilename().trim().isEmpty()) {
				ps.setString(3, vo.getFilename());
				ps.setLong(4, vo.getFilesize());
				ps.setInt(5, vo.getNum());
			}
			else {
				ps.setInt(3, vo.getNum());
			}
			return ps.executeUpdate();
			
		} finally {
			close();
		}
	}	// updateBoard() -----------------------------
	
	public boolean updateReadnum(int num) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			String sql = "UPDATE board SET readnum=readnum+1 WHERE num=?";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			int n = ps.executeUpdate();
			return (n>0)? true:false;
			
		} finally {
			close();
		}
	}	// updateReadnum() ----------------------
	
	
	public void close() {
		try {
			if (rs!=null) rs.close();
			if (ps!=null) ps.close();
			if (con!=null) con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}// close() --------------------------


}
