package paquete;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Servlet2", urlPatterns = {"/Servlet2"})
public class Servlet2 extends HttpServlet {

    Connection connection = null;
    Statement statement = null;
    ResultSet resultset = null;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<body>");
            Random rand = new Random();
            int pares = parseInt(request.getParameter("txtPares"));
            if (pares >= 20 && pares <= 50) {
                int i = 0;
                while (i < pares) {
                    int ran1 = rand.nextInt(15);
                    int ran2 = rand.nextInt(15);
                    ran1++;
                    ran2++;
                    statement.executeUpdate("INSERT INTO numerosrandom (numero1,numero2) VALUES("+ran1+","+ran2+")");
                    i++;
                }
                responderOK(out);
            } else {
                responderFaltanDatos(out);
            }
        } catch (NumberFormatException nfe) {
            responderDatosIncorrectos(out);
        } catch (SQLException ex) {
            Logger.getLogger(Servlet2.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            out.println("</body>");
            out.println("</html>");
        }
    }

    private void responderOK(PrintWriter out) {
        out.println("<h1>Alta Ok.</h1>");
        out.println("<a href='index.html'>Volver al index</a>");
    }

    private void responderError(PrintWriter out, String msg) {
        out.println("<h1>Hubo un problema con el Alta. Intente en un momento o informe al administrador.");
        out.println("<p>" + msg + "</p>");
    }

    private void responderDatosIncorrectos(PrintWriter out) {
        out.println("<h1>El precio es incorrecto. Vuelva a ingresarlo.</h1>");
    }

    private void responderFaltanDatos(PrintWriter out) {
        out.println("<h1>Faltan datos en el formulario</h1>");
        out.println("<a href='index.html'>Volver al index</a>");
    }
}
