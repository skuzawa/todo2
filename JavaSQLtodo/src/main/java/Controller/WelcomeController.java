package Controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.ListDAO;

@WebServlet("/welcome")
public class WelcomeController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String username = (String) session.getAttribute("username");
        String session_id = Integer.toString((int) session.getAttribute("id"));
        

        // セッションから取得したusernameでログイン状態のチェックを行う
        if (username != null) {
            ListDAO dao = new ListDAO();
            try {
            	req.setAttribute("rows", dao.select_user(session_id));
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
            req.setAttribute("username", username);
            String view = "/WEB-INF/views/welcome.jsp";
            req.getRequestDispatcher(view).forward(req, res);
        } else {
            // 未ログインの場合、ログイン画面に遷移
            res.sendRedirect("login");
        }
    }
}