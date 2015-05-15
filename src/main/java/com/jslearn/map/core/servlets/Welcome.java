package com.jslearn.map.core.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Aleksandr_Ishimtcev
 */
public class Welcome extends HttpServlet {
    Connection connection = null;
    Statement statement = null;
    private String message;

    public void init() {
        message = "Hello message";
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("message", getDBResult());
        req.getRequestDispatcher("welcome.jsp").forward(req, resp);
    }

    protected String getDBResult() {
        if (openConnection()) {
            deleteTable();
        }
        closeConnection();

        return message;
    }

    protected boolean openConnection() {
        try {
            connection = getDBConnection();
            statement = connection.createStatement();
        } catch (ClassNotFoundException e) {
            message = "Driver not found: " + e.getMessage();
            return false;
        } catch (SQLException e) {
            message = "Connection error: " + e.getMessage();
            return false;
        }
        return true;
    }

    protected void closeConnection() {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
            }
        }
    }

    protected boolean createTable() {
        String createTableSQL = "CREATE TABLE DBUser("
                                + "userID INT(5) NOT NULL, "
                                + "userName VARCHAR(20) NOT NULL, "
                                + "createdBy VARCHAR(20) NOT NULL, "
                                + "createdDate DATE NOT NULL, "
                                + "PRIMARY KEY (userID) "
                                + ");";

        return sendQuery(createTableSQL);
    }

    protected boolean deleteTable() {
        String dropTableSQL = "DROP TABLE Administrator;";

        return sendQuery(dropTableSQL);
    }

    protected boolean sendQuery(String query) {
        try {
            statement.execute(query);
        } catch (SQLException e) {
            message = "Error in query sending: " + e.getMessage();
            return false;
        }

        message = "Done: " + query;
        return true;
    }

    protected Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/u623295485mapca", "u623295485admin", "pe@4VXjdTqZ8gguRUE");
        return connection;
    }
}
