package user.model;

import java.sql.Date;

public class UserVO {
	// property - 멤버변수 (html의 input name과 db table의 컬럼명과 일치시켜주면 편리함)
	private int idx;
	private String name;
	private String userid;
	private String pwd;
	
	private String hp1;
	private String hp2;
	private String hp3;
	
	private String post;
	private String addr1;
	private String addr2;

	private java.sql.Date indate;
	private int mileage;
	private int mstate;	// 회원 상태정보 (0: 활동회원, -1: 정지회원, -2: 탈퇴회원)
	private String mstateStr;	// 상태정보를 문자열로
	
	
	public UserVO() {
		System.out.println("UserVO()생성자..");
	}

	public UserVO(int idx, String name, String userid, String pwd, String hp1, String hp2, String hp3, String post,
			String addr1, String addr2, Date indate, int mileage, int mstate, String mstateStr) {
		super();
		this.idx = idx;
		this.name = name;
		this.userid = userid;
		this.pwd = pwd;
		this.hp1 = hp1;
		this.hp2 = hp2;
		this.hp3 = hp3;
		this.post = post;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.indate = indate;
		this.mileage = mileage;
		this.mstate = mstate;
		this.mstateStr = mstateStr;
	}


	public int getIdx() {
		return idx;
	}


	public void setIdx(int idx) {
		this.idx = idx;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
		System.out.println("setName(): "+name);
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
		System.out.println("setUserid(): "+userid);
	}


	public String getPwd() {
		return pwd;
	}


	public void setPwd(String pwd) {
		this.pwd = pwd;
	}


	public String getHp1() {
		return hp1;
	}


	public void setHp1(String hp1) {
		this.hp1 = hp1;
	}


	public String getHp2() {
		return hp2;
	}


	public void setHp2(String hp2) {
		this.hp2 = hp2;
	}


	public String getHp3() {
		return hp3;
	}


	public void setHp3(String hp3) {
		this.hp3 = hp3;
	}


	public String getPost() {
		return post;
	}


	public void setPost(String post) {
		this.post = post;
	}


	public String getAddr1() {
		return addr1;
	}


	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}


	public String getAddr2() {
		return addr2;
	}


	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}


	public java.sql.Date getIndate() {
		return indate;
	}


	public void setIndate(java.sql.Date indate) {
		this.indate = indate;
	}


	public int getMileage() {
		return mileage;
	}


	public void setMileage(int mileage) {
		this.mileage = mileage;
	}


	public int getMstate() {
		return mstate;
	}


	public void setMstate(int mstate) {
		this.mstate = mstate;
	}


	public String getMstateStr() {
		return mstateStr;
	}


	public void setMstateStr(String mstateStr) {
		this.mstateStr = mstateStr;
	}
	
	public String getAllHp() {
		return hp1+"-"+hp2+"-"+hp3;
	}
	
	public String getAllAddr() {
		return "["+post+"] "+addr1+" "+addr2;
	}
	
	
	
	
}	/////////////////////////////////
