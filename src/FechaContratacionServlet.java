import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet(urlPatterns = "/fechaContratacion")
public class FechaContratacionServlet extends HttpServlet {
	
	private DataSource pool;
	
	@Override
	public void init() throws ServletException {
		InitialContext context;
		try {
			context = new InitialContext();
			pool = (DataSource) context.lookup("java:comp/env/jdbc/employees");
		} catch (NamingException e) {
			throw new ServletException("Recurso de base de datos no encontrado.");
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		resp.setContentType("text/html");
		PrintWriter pw;
		Connection conexion = null;
		ResultSet rs = null;
		
		try {
			String fecha = req.getParameter("fecha");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        java.util.Date parsed = format.parse(fecha);
	        java.sql.Date fecha_date = new java.sql.Date(parsed.getTime());
			pw = resp.getWriter();
			conexion = pool.getConnection();
			PreparedStatement s = conexion.prepareStatement("SELECT * FROM employees WHERE hire_date > ?");
			s.setDate(1, fecha_date);
			rs = s.executeQuery();
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
			
		} catch (SQLException | ParseException e) {
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
