import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class errorServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String msg = (String) req.getAttribute("javax.servlet.error.message");
		PrintWriter pw = null;
		try {
			resp.setContentType("text/html");
			pw = resp.getWriter();
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<meta charset=\'UTF-8\'>");
			pw.println("<title>Prueba Servlet</title>");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<p>Se ha producido un error.</p>");
			pw.println("<p>Causa:"+msg+" </p>");
			pw.println("</body>");
			pw.println("</html>");
			
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
	}
}
