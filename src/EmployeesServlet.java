import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/empleados")
public class EmployeesServlet extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter pw;
		Connection conexion = null;
		ResultSet rs = null;
		try {
			pw = resp.getWriter();
			conexion = DriverManager.getConnection ("jdbc:mysql://localhost/employees", "root", "practicas");
			Statement s = conexion.createStatement();
			rs = s.executeQuery ("SELECT * FROM employees");
			pw.println("<html>");
			pw.println("<head>");
			pw.println("<meta charset=\'UTF-8\'>");
			pw.println("<title>Prueba Servlet</title>");
			pw.println("</head>");
			pw.println("<body>");
			pw.println("<table>");
			pw.println("<tr>");
			pw.println("<th>Nº Empleado</th>");
			pw.println("<th>Fecha de nacimiento</th>");
			pw.println("<th>Nombre</th>");
			pw.println("<th>Género</th>");
			pw.println("<th>Fecha de contratación</th>");
			pw.println("</tr>");
			
			while (rs.next()) {
				pw.println("<tr>");
				pw.println("<td>"+rs.getInt(1)+"</td>");
				pw.println("<td>"+rs.getDate(2)+"</td>");
				pw.println("<td>"+rs.getString(3)+" "+rs.getString(4)+"</td>");
				pw.println("<td>"+rs.getString(5)+"</td>");
				pw.println("<td>"+rs.getDate(6)+"</td>");
				pw.println("</tr>");
			}
			
			pw.println("</table>");
			pw.println("</body>");
			pw.println("</html>");
		} catch (SQLException e) {
			resp.resetBuffer();
			throw new ServletException(e);
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(conexion != null) {
					conexion.close();
				}
			} catch (SQLException e) {
				throw new ServletException(e);
			}
			
		}
	}
}
