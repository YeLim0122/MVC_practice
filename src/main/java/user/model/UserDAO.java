package user.model;
import java.sql.*;
import java.util.*;

import common.util.DBUtil;

public class UserDAO {
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	public UserDAO() {
		System.out.println("UserDAO()생성자..");
	}
	
	
	/** 로그인 처리를 하는 메소드 */
	public UserVO loginCheck(String userid, String pwd) throws SQLException, NotUserException {
		UserVO user = this.selectUserByUserid(userid);
		if (user == null) {
			// 아이디가 없는 경우 => 예외 발생
			throw new NotUserException(userid+"란 아이디는 존재하지 않습니다.");
		}
		// 비밀번호 일치 여부 체크
		String dbPwd = user.getPwd();
		if (!dbPwd.equals(pwd)) {
			throw new NotUserException("비밀번호가 일치하지 않아요!");	// 진짜 할 때는 애매하게 메시지 줘야 함.
		}
		return user;
		
	}	// loginCheck() ------------------------------
	
	/** 아이디로 회원정보 가져오는 메소드 
	 * 
	 * -- system으로 접속해서 multishop에게 view 생성 권한 부여
		GRANT CREATE VIEW, CREATE SYNONYM TO multishop;
		-----------------------------------------------
		-- multishop으로 접속해서 뷰 생성
		CREATE OR REPLACE VIEW memberView
		AS
		SELECT member.*, 
		decode(mstate, 0, '활동회원', -1, '정지회원', -2, '탈퇴회원', 9, '관리자') mstateStr
		FROM member
		WHERE mstate > -2;
		
		SELECT * FROM memberView;
	 * */
	public UserVO selectUserByUserid(String userid) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			/*
			 * StringBuilder buf = new StringBuilder("SELECT member.*,")
			 * .append(" decode(mstate, 0, '활동회원', -1, '정지회원', -2, '탈퇴회원', 9, '관리자') mstateStr"
			 * ) .append(" FROM member WHERE userid=?");
			 */
			String sql = "SELECT * FROM memberView WHERE userid=?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, userid);
			rs = ps.executeQuery();
			
			List<UserVO> arr = makeList(rs);
			if (arr!=null && arr.size()==1) {
				return arr.get(0);
			}
			return null;
		}
		finally {
			close();
		}
	}	// selectUserByUserid() ---------------------------
	
	
	public boolean idCheck(String userid) throws SQLException{
		try {
			con = DBUtil.getCon();
			String sql = "SELECT userid FROM member WHERE userid=?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, userid);
			rs = ps.executeQuery();
			// 결과는 있으면 1개 레코드를 반환, 없으면 x
			boolean b = rs.next();
			/*
			if(b) 
				return false;	// 이미 사용 중
			else
				return true;	// 사용 가능
			*/
			return !b;
		}
		finally {
			
			close();
		}
		
	}	//--------------------------
	
	public int insertUser(UserVO user) throws SQLException {
		try {
			con = DBUtil.getCon();
			StringBuilder buf = new StringBuilder("INSERT INTO member(")
					.append(" idx, name, userid, pwd, hp1, hp2, hp3, post, addr1, addr2)")
					.append(" VALUES(member_seq.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			String sql = buf.toString();
			
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getUserid());
			ps.setString(3, user.getPwd());
			ps.setString(4, user.getHp1());
			ps.setString(5, user.getHp2());
			ps.setString(6, user.getHp3());
			ps.setString(7, user.getPost());
			ps.setString(8, user.getAddr1());
			ps.setString(9, user.getAddr2());
			
			return ps.executeUpdate();
		
		}
		finally {
			close();
		}
	}	// ---------------------------------------
	
	
	public List<UserVO> listUser() throws SQLException {
		try {
			con = DBUtil.getCon();
			
			StringBuilder buf = new StringBuilder("select member.*, ")
					.append(" decode(mstate, 0, '활동회원', -1, '정지회원', -2, '탈퇴회원', 9, '관리자') mstateStr")
					.append(" from member order by idx desc");
			
			// String sql = "SELECT * FROM member ORDER BY idx desc";
			
			String sql = buf.toString();
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			return makeList(rs);
		}
		finally {
			close();
		}
		
	}	// listUser()---------------------------------------
	
	// PK-idx(회원번호)로 회원정보를 가져오는 메소드
	public UserVO selectUserByIdx(int idx) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			StringBuilder buf = new StringBuilder("SELECT member.*,")
					.append(" decode(mstate, 0, '활동회원', -1, '정지회원', -2, '탈퇴회원', 9, '관리자') mstateStr")
					.append(" FROM member WHERE idx=?");
			
			String sql = buf.toString();
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, idx);
			
			rs = ps.executeQuery();
			
			List <UserVO> arr = makeList(rs);
			if (arr !=null && arr.size() == 1) {
				UserVO user = arr.get(0);
				return user;	// 회원정보 반환
			}
			return null;	// 해당 회원이 없을 경우
			
		}
		finally {
			close();
		}
	}
	
	public List<UserVO> makeList(ResultSet rs) throws SQLException {
		List<UserVO> arr = new ArrayList<>();
		
		while(rs.next()) {
			int idx = rs.getInt("idx");
			String name = rs.getString("name");
			String userid = rs.getString("userid");
			String pwd = rs.getString("pwd");
			String hp1 = rs.getString("hp1");
			String hp2 = rs.getString("hp2");
			String hp3 = rs.getString("hp3");
			String post = rs.getString("post");
			String addr1 = rs.getString("addr1");
			String addr2 = rs.getString("addr2");
			java.sql.Date indate = rs.getDate("indate");
			int mileage = rs.getInt("mileage");
			int mstate = rs.getInt("mstate");
			String mstateStr = rs.getString("mstateStr");
			
			UserVO user = new UserVO(idx, name, userid, pwd, hp1, hp2, hp3,
									post, addr1, addr2, indate, mileage, mstate, mstateStr);
			arr.add(user);
			
		}	// while
		return arr;
		
	}	// makeList() ---------------------------------------
	
	
	public int updateUser(UserVO user, int mstate) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			StringBuilder buf = null;
			if (mstate != 9) {	// 일반 회원일 경우
				buf = new StringBuilder("UPDATE member")
						.append(" SET name=?, userid=?, hp1=?, hp2=?, hp3=?, ")
						.append(" post=?, addr1=?, addr2=?, mstate=?, pwd=?")
						.append(" WHERE idx=?");
			}
			else {
				// 관리자라면 마일리지 수정
				buf = new StringBuilder("UPDATE member")
						.append(" SET name=?, userid=?, hp1=?, hp2=?, hp3=?, ")
						.append(" post=?, addr1=?, addr2=?, mstate=?, mileage=?")
						.append(" WHERE idx=?"); 
			}
			
			String sql = buf.toString();
			// System.out.println(sql);	// 디버그용
			
			ps = con.prepareStatement(sql);
			ps.setString(1, user.getName());
			ps.setString(2, user.getUserid());
			ps.setString(3, user.getHp1());
			ps.setString(4, user.getHp2());
			ps.setString(5, user.getHp3());
			ps.setString(6, user.getPost());
			ps.setString(7, user.getAddr1());
			ps.setString(8, user.getAddr2());
			ps.setInt(9, user.getMstate());
			
			if(mstate != 9) {	// 일반회원일 경우
				ps.setString(10, user.getPwd());
			}
			else {	// 관리자일 경우
				ps.setInt(10, user.getMileage());
			}
			ps.setInt(11, user.getIdx());
			return ps.executeUpdate();
			
		}
		finally {
			close();
		}
	}	// updateUser() --------------------------------------
	
	
	public int deleteUser(int idx) throws SQLException {
		try {
			con = DBUtil.getCon();
			
			String sql = "UPDATE member SET mstate=-2 WHERE idx=?";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, idx);
			return ps.executeUpdate();
		}
		finally {
			close();
		}
	}	// deleteUser() ---------------------------------------
	
	
	public void close() {
		try {
			if(rs!=null) rs.close();
			if(ps!=null) ps.close();
			if(con!=null) con.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
