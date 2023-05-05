package paquete;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.sql.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet(name = "Servlet1", urlPatterns = {"/Servlet1"})
public class Servlet1 extends HttpServlet {
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
            int n1[];int n2[]; int contadorFinal=0;String temp1;String temp2;
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet consulta de Tabla</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Tabla</h1>");
            out.println("<table border=1>");
            out.println("<tr>");
            out.println("<td>Numero 1");
            out.println("<td>Numero 2");
            out.println("</tr>");
            resultset=statement.executeQuery("Select * from numerosrandom");
            while (resultset.next()){
                out.println("<tr>");
                out.println("<td>"+resultset.getString("Numero1"));
                out.println("<td>"+resultset.getString("Numero2"));
                out.println("</tr>");
                temp1=resultset.getString("Numero1");
                temp2=resultset.getString("Numero2");
                if(temp1.equals(temp2)){
                    contadorFinal++;
                }
            }
            out.println("</table>");
            out.println("Las veces que el par de numeros se repitio fueron: "+contadorFinal);
        } catch (SQLException ex){
            System.out.println("Hubo un error en el SQL: "+ex.getMessage());
        } finally {
            out.println("</body>");
            out.println("</html>");
        }
    }
}