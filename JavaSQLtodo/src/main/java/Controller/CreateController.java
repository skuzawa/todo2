package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Model.createDAO;
import utils.HashGenerator;

@WebServlet("/create")
public class CreateController extends HttpServlet {
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String view = "/WEB-INF/views/create.jsp";
		req.getRequestDispatcher(view).forward(req, res);
	}

	// 2.POST用のメソッド追加
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 3.フォームから各値を取得
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String profile = req.getParameter("profile");
        // 4.DBに接続
        try{
        	createDAO dao = new createDAO();
            // 5.パスワードをハッシュ化する
            String hashedPassword = HashGenerator.generateHash(password);
            dao.register(username, hashedPassword, profile);
            
        }catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// リクエストスコープへのデータ格納（リストデータの格納）
			req.setAttribute("message", "例外が発生しました");
			String view = "WEB-INF/view/index.jsp";
			// 転送オブジェクトを取得
			RequestDispatcher dispatcher = req.getRequestDispatcher(view);
			// 転送
			dispatcher.forward(req, res);
		}
        String view = "/WEB-INF/views/index.jsp";
        req.getRequestDispatcher(view).forward(req, res);
	}
}