package Controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.HashGenerator;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final String DB_URL = "jdbc:mysql://localhost/User";
	private static final String DB_USER = "root";
	private static final String DB_PASSWORD = "password";

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String view = "/WEB-INF/views/index.jsp";
		req.getRequestDispatcher(view).forward(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		HttpSession session = req.getSession();
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        PreparedStatement statement = null;
        Connection conection = null;
        try {
        	 Class.forName("com.mysql.cj.jdbc.Driver");
         	 conection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD);
        }catch(SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// リクエストスコープへのデータ格納（リストデータの格納）
			req.setAttribute("message", "例外が発生しました");
			String view = "WEB-INF/view/error.jsp";
			// 転送オブジェクトを取得
			RequestDispatcher dispatcher = req.getRequestDispatcher(view);
			// 転送
			dispatcher.forward(req, res);
		}
        
    	
        
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try {
        		String hashedPassword = HashGenerator.generateHash(password);
            	statement = conection.prepareStatement(sql);
            	statement.setString(1, username);
            	statement.setString(2, hashedPassword);
                ResultSet rs = statement.executeQuery();
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String profile = rs.getString("profile");
                    // 2.サーバーからセッションを取得する
                    session = req.getSession();
                    // 3.キーと値のペアでセッションに登録する
                    session.setAttribute("id", id);
                    session.setAttribute("username", username);
                    session.setAttribute("profile", profile);
                    res.sendRedirect("welcome");
                } else {
                    String view = "/WEB-INF/views/index.jsp";
                    req.getRequestDispatcher(view).forward(req, res);
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Database Connection Failed", e);
        } catch (NoSuchAlgorithmException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}
}