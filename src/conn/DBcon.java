package conn;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBcon {
	static Connection con;
	private DBcon() throws Exception {
		String url="jdbc:oracle:thin:@localhost:1521:orcl";
		String user="scott";
		String pass="123456";
		Class.forName("oracle.jdbc.driver.OracleDriver");
		
		con=DriverManager.getConnection(url, user, pass);
		System.out.println("초기 접속 성공");
	}
	
	public static Connection getConnection() throws Exception {
		if (con==null) {
			new DBcon();
		}
		return con;
	}

}
