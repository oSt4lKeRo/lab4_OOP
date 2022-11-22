package Task1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "shopMenu", value = "/Sho0op")
public class Task1Menu extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html;charset=UTF-8");
		req.setCharacterEncoding("UTF-8");
		PrintWriter printWriter = resp.getWriter();

		String choose = req.getParameter("choose");
		switch (choose){
			case "View" :
				Task1Methods.viewTable(req, resp);
				break;
			case "Add" :
				Task1Methods.addElem(req, resp);
				break;
			case "Delete" :
				Task1Methods.deleteElem(req, resp);
				break;
			case "Update" :
				Task1Methods.updateElem(req, resp);
				break;
		}
	}
}
