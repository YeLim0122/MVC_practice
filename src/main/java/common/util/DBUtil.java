package common.util;
import java.sql.*;

public class DBUtil {
	static String url = "jdbc:oracle:thin:@localhost:1521:XE";
	static String user = "multishop", pwd = "tiger";
	
	static {
		// 드라이버 로딩
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver Loading Success!");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}	// main보다 먼저 실행
	
	public static Connection getCon() throws SQLException {
		Connection con = DriverManager.getConnection(url, user, pwd);
		return con;
	}

}
