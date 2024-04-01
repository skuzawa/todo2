package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class createDAO {
	String url = "jdbc:mysql://localhost/User";
	String user = "root";
	String password = "password";
	Connection conection = null;

	public void conect() throws Exception {
		// おまじない
		Class.forName("com.mysql.cj.jdbc.Driver");
		// ①DBに接続
		conection = DriverManager.getConnection(url, user, password);
	}

	public void register(String username, String hashedPassword, String profile) throws Exception {
		PreparedStatement statement = null;
		String sql = "INSERT INTO users (username, password, profile) VALUES (?,?,?)";
		
		// DB接続メソッド呼び出し
		conect();
		// ステートメント生成
		statement = conection.prepareStatement(sql);
		
		statement.setString(1, username);
		statement.setString(2, hashedPassword);
		statement.setString(3, profile);
		statement.executeUpdate();
	}
}