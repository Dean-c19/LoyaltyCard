import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogIn extends HttpServlet {

    Connection connection;

    public LogIn() throws SQLException {

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/loyaltycard?serverTimezone=UTC", "root", "rootroot1");
    }

    public static void main(String[] args) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<h3>Hi, Youve logged in</h3>");


    }
}
