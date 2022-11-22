package Task1;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

public class Task1Methods {

	private static String url = "jdbc:postgresql://127.0.0.1:5432/lab4DB";

	private static String url2 = "jdbc:postgresql://127.0.0.1:5432";

	private static String bdName = "lab42";
	private static String username = "postgres";
	private static String password = "1986";

	private static Connection getConnect(){
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(url, username, password);
		} catch (SQLException e){
			e.printStackTrace();
			connection = createDB();
		}
		return connection;
	}

//	public static int getID(){
//		Connection con = getConnect();
//		Statement st = null;
//		ResultSet res;
//
//		int id = 0;
//		try {
//			st = con.createStatement();
//			res = st.executeQuery("SELECT * FROM \"donationsList\"");
//			while (res.next()) {
//				id = res.getInt("donations_id");
//			}
//
//		} catch (SQLException e){
//			e.printStackTrace();
//		}
//		id++;
//		return id;
//	}

	public static Connection createDB(){
		Connection con = null;
		PreparedStatement st = null;
		ResultSet res;

		PrintWriter out = null;
		try {
			con = DriverManager.getConnection(url2 + bdName,username, password);
			Statement st_new = con.createStatement();
			st_new.executeUpdate(
					"CREATE TABLE item" +
							"(donations_id integer PRIMARY KEY NOT NULL," +
							"title character varying(255) NOT NULL ," +
							"description character varying(255) NOT NULL)");
			st_new.close();
		} catch (SQLException exep){
			out.println(exep.getMessage());
		}
		return con;
	}


	public static void deleteElem(HttpServletRequest req, HttpServletResponse resp){
		Connection con = getConnect();
		PreparedStatement st = null;
		ResultSet res;
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException exception){
			exception.printStackTrace();
		}
		int id;
		try {
			id = Integer.parseInt(req.getParameter("id"));
			st = con.prepareStatement("DELETE FROM \"donationsList\" WHERE donations_id = ?");
			st.setInt(1, id);

			out.println("Элемент " + id + " успешно удален!");
			st.executeQuery();
		} catch (SQLException e) {
			out.println(e.getMessage());
		}
	}


	public static void updateElem(HttpServletRequest req, HttpServletResponse resp){
		Connection con = getConnect();
		PreparedStatement st = null;
		ResultSet res;
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException exception){
			exception.printStackTrace();
		}
		int id = 0;
		try {
			id = Integer.parseInt(req.getParameter("id"));
		} catch (Exception e){
			out.println(e.getMessage());
		}
		String title = req.getParameter("title");
		String description = req.getParameter("description");

		try {
			st = con.prepareStatement("UPDATE \"donationsList\"" +
					"SET title=?, description= ? " +
					"WHERE donations_id = ?");
			st.setString(1, title);
			st.setString(2, description);
			st.setInt(3, id);

			out.println("Элемент " + id + " обновлен");
			st.executeQuery();
		} catch (SQLException e){
			out.println(e.getMessage());
		}
	}


	public static void addElem(HttpServletRequest req, HttpServletResponse resp){
		Connection con = getConnect();
		PreparedStatement st = null;
		ResultSet res;
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException exception){
			exception.printStackTrace();
		}

		String title = req.getParameter("title");
		String description = req.getParameter("description");

		try {
			st = con.prepareStatement("INSERT INTO \"donationsList\"(title, description) VALUES (?, ?)");
			st.setString(1, title);
			st.setString(2, description);

			st.executeQuery();
			out.println("Элемент успешно добавлен!");
			
		} catch (SQLException e){
			out.println(e.getMessage());
		}
	}


	public static void viewTable(HttpServletRequest req, HttpServletResponse resp){
		Connection con = getConnect();
		Statement st = null;
		ResultSet res;
		PrintWriter out = null;
		try {
			out = resp.getWriter();
		} catch (IOException exception){
			exception.printStackTrace();
		}
		
		int id;
		String title, description;
		try {
			st = con.createStatement();
			res = st.executeQuery("SELECT * FROM \"donationsList\"");
			out.println("id" + "\ttitle" + "             " + "description");
			out.println("<br>");
			while (res.next()){
			id = res.getInt("donations_id");
			title = res.getString("title");
			description = res.getString("description");

			out.println(id + " " + title + " " + description);
			out.write("<br>");
			}
		} catch (SQLException e){
			out.println(e.getMessage());
		}
	}
}
