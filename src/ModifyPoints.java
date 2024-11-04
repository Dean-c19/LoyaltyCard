import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ModifyPoints extends HttpServlet {

    Connection connection;

    public ModifyPoints() throws SQLException {

        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/loyaltycard?serverTimezone=UTC", "root", "rootroot1");
    }

    public static void main(String[] args) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();



        // log the user in, query by username to get ther username and password
        String username = request.getParameter("username");
        String passwordEntered = request.getParameter("password");
        try {
            String loginQuery = "SELECT * FROM members WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(loginQuery);
            preparedStatement.setString(1, username);

            ResultSet output = preparedStatement.executeQuery();

            if (output.next()) {

                String passwordsStored = output.getString("password");
                int newPoints = Integer.parseInt(request.getParameter("points"));
                int points = output.getInt("points");
                int modifiedPoints = points + newPoints;
                // if the user exists and passwords match, update the points in the db and display the new number
                if (passwordsStored.equals(passwordEntered) && modifiedPoints >= 0) {

                    String updatePointsSql = "UPDATE members SET points = ? WHERE username = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updatePointsSql);
                    updateStatement.setInt(1, modifiedPoints);
                    updateStatement.setString(2, username);
                    updateStatement.executeUpdate();



                    out.println("<h3>Points Updated!</h3>");
                    out.println("<h3>New points balance for " + username + ": " + modifiedPoints + "</h3>");

                }
                // if theres not enough points to apply a negative number, display an error
                else if (modifiedPoints < 0) {
                    out.println("<h3>You dont have enough points to spend. Please try again.</h3>");
                }
                else {
                    // if password not correct display error
                    out.println("<h3>Incorrect password. Please try again.</h3>");
                }
            } else {
                // username not found display error
                out.println("<h3>Username doesnt exist. Please try again.</h3>");
            }





        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}