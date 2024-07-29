package shaunak.proj.web.jdbc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource(name="jdbc/web_student_tracker")
    private DataSource dataSource;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        try {
            // Get a connection to the database
            Connection mycon = dataSource.getConnection();

            // Create a statement
            Statement myStmt = mycon.createStatement();

            // SQL statement
            String sql = "SELECT * FROM student";

            // Execute SQL query
            ResultSet myRs = myStmt.executeQuery(sql);

            // Process the result set
            while (myRs.next()) {
                String email = myRs.getString("email");
                out.println(email);  // Send output to the browser
            }

            // Close the resources
        } catch (SQLException e) {
            e.printStackTrace(out);  // Print stack trace to browser for debugging
        }
    }

}
