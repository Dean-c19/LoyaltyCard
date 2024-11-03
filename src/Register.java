import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Register extends HttpServlet {

    Connection connection;

    public Register() throws SQLException {

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/loyaltycard?serverTimezone=UTC", "root", "rootroot1");
    }

    public static void main(String[] args) {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        if (password == null || confirmPassword == null || !password.equals(confirmPassword)) {
            out.println("<h3>Passwords do not match! Please try again.</h3>");
            return;
        }
        else {

            try{
                // select the table to use
                String query = "INSERT INTO members (username, password, points) VALUES (?, ?, ?)";
                // add the user to the db with 100 points
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setInt(3, 100);

                int addMember = preparedStatement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            out.println("<h3>Thanks for registering!! 100 points have been added</h3>");




        }

    }
}
