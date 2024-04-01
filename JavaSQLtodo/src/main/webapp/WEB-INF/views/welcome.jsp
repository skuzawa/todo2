<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<!DOCTYPE html>
<html>
<head>
<title>Welcome Page</title>
</head>
<body>
	<h1>Welcome</h1>
	<!-- セッションから値を取得 -->
	<p>
		Welcome,
		<%=session.getAttribute("username")%>!
	</p>
	<table>
		<table border="1">
			<tr>
				<th>タスク名</th>
				<th>タスク内容</th>
				<th>重要度</th>
			</tr>
			<%
			ArrayList<HashMap<String, String>> rows = (ArrayList<HashMap<String, String>>) request.getAttribute("rows");
			%>
			<%
			for (HashMap<String, String> columns : rows) {
			%>
			<tr>
				<form action="todo" method="POST">
					<td><%=columns.get("task_name")%></td>
					<td><%=columns.get("content")%></td>
					<td><%=columns.get("prior")%></td>
					<td><button type="submit" value="delete" class="btn"
							　name="action">削除</button></td>
					<td><button type="submit" value="update" class="btn"
							　name="action">更新</button></td>
				</form>
			</tr>
			<%}%>
		</table>
	</table>
	<form method="POST" action="logout">
		<input type="submit" value="Logout">
	</form>
</body>
</html>