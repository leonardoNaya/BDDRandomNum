package paquete;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet(name = "Servlet3", urlPatterns = {"/Servlet3"})
public class Servlet3 extends HttpServlet {
    Connection connection = null;
    Statement statement = null;
    ResultSet resultset = null;
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/BDDNumeros");
            statement = (Statement) connection.createStatement();

        } catch (SQLException ex) {
            System.out.println("Hubo un error en el SQL: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("No se pudo cargar el controlador: " + ex.getMessage());
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String borrador="Delete from numerosrandom";
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet borrador de tabla</title>");
            out.println("</head>");
            out.println("<body>");
            int n=statement.executeUpdate(borrador);
            if (n>=0){
                out.println("<h1>Eliminado con éxito</h1>");
            }
        } catch (SQLException ex){
            System.out.println("Hubo un error en el SQL: "+ex.getMessage());
        } finally {
            out.println("</body>");
            out.println("</html>");
        }
    }
}
