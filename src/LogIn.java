import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
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


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String passwordEntered = request.getParameter("password");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        // querry db for username, and password and points if the user exists
        try {
            // e.g select * from members where username = 'username'
            String loginQuery = "SELECT * FROM members WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(loginQuery);
            preparedStatement.setString(1, username);

            ResultSet output = preparedStatement.executeQuery();

            if (output.next()) {

                String passwordsStored = output.getString("password");
                int points = output.getInt("points");

                // compare password to the user's entered password
                if (passwordsStored.equals(passwordEntered)) {
                    // if they match display points
                    out.println("<h3>Thanks for logging in " + username + "</h3>");
                    out.println("<h3>You currently have " + points + " points.</h3>");
                } else {
                    // if not display error
                    out.println("<h3>Incorrect password. Please try again.</h3>");
                }
            } else {
                // username not found display error
                out.println("<h3>Username doesnt exist. Please try again.</h3>");
            }



        }



        catch (Exception e) {
            throw new RuntimeException(e);
        }






    }
}
