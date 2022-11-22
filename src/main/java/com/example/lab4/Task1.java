package com.example.lab4;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.*;

@WebServlet(name = "Task1", value = "/Task1")
public class Task1  extends HttpServlet {


	private Connection getConnection(HttpServletResponse resp) throws IOException {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/lab4DB", "postgres", "1986");
		} catch (SQLException sqle){
			PrintWriter out = resp.getWriter();
			out.println(sqle.getMessage() + "Pizdec");
			System.out.println(sqle.getMessage());
		}
		return connection;
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		try {
			Connection con = getConnection(resp);
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery("SELECT * FROM \"donationsList\"");
			while (res.next()){
				out.println(res.getInt("donation_id"));
			}
		} catch (Exception e) {
			out.write(e.getMessage() + "hui");
		}
	}
}