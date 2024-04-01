package Model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

class ListDAOTest {

	@Test
	public void priorUpTest() throws Exception {
		
		ListDAO test = new ListDAO();
		
		ArrayList<HashMap<String, String>> row = test.select("","upSort");
		assertEquals("SELECT * FROM posts where del_flag = 0 ORDER BY prior ASC", test.sql);
	}
	
	@Test
	public void priorDownTest() throws Exception {
		
		ListDAO test = new ListDAO();
		
		ArrayList<HashMap<String, String>> row = test.select("","downSort");
		assertEquals("SELECT * FROM posts where del_flag = 0 ORDER BY prior DESC", test.sql);
	}
	
	@Test
	public void dateDownTest() throws Exception {
		
		ListDAO test = new ListDAO();
		
		ArrayList<HashMap<String, String>> row = test.select("downSort","");
		assertEquals("SELECT * FROM posts where del_flag = 0 ORDER BY create_time ASC", test.sql);
	}
	
	@Test
	public void dateUpTest() throws Exception {
		
		ListDAO test = new ListDAO();
		
		ArrayList<HashMap<String, String>> row = test.select("upSort","");
		assertEquals("SELECT * FROM posts where del_flag = 0 ORDER BY create_time DESC", test.sql);
	}
	
	@Test
	public void nullTest() throws Exception {
		
		ListDAO test = new ListDAO();
		
		ArrayList<HashMap<String, String>> row = test.select(null,null);
		assertEquals("SELECT * FROM posts where del_flag = 0", test.sql);
	}
	
	@Test
	public void selectNullUserTest() throws Exception {
		
		ListDAO test = new ListDAO();
		
		String user_num = "100";
		
		ArrayList<HashMap<String, String>> row = test.select_user(user_num);
		assertEquals(0, row.size());
	}
	
	@Test
	public void selectUserTest() throws Exception {
		
		ListDAO test = new ListDAO();
		
		String user_num = "1";
		
		ArrayList<HashMap<String, String>> row = test.select_user(user_num);
		assertEquals(3, row.size());
	}
}








