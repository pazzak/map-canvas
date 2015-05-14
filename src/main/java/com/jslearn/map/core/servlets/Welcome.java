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
    private String message;

    public void init() {
        message = "Hello message";
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("message", getDBResult());
        req.getRequestDispatcher("welcome.jsp").forward(req, resp);
    }

    protected String getDBResult() {
        Connection connection = null;
        Statement statement = null;
        String result = "";
        String createTableSQL = "CREATE TABLE DBUSER("
                                + "USER_ID NUMBER(5) NOT NULL, "
                                + "USERNAME VARCHAR(20) NOT NULL, "
                                + "CREATED_BY VARCHAR(20) NOT NULL, "
                                + "CREATED_DATE DATE NOT NULL, " + "PRIMARY KEY (USER_ID) "
                                + ")";
        try {
            connection = getDBConnection();
            statement = connection.createStatement();
            statement.execute(createTableSQL);
            result =  "Successfully connected";
        } catch (ClassNotFoundException e) {
            result = "Driver not found: " + e.getMessage();
        } catch (SQLException e) {
            result = "Connection error: " + e.getMessage();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    result = "Error while closing: " + e.getMessage();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    result = "Error while closing: " + e.getMessage();
                }
            }
        }

        return result;
    }

    protected Connection getDBConnection() throws ClassNotFoundException, SQLException {
        Connection connection = null;
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/u623295485mapca", "u623295485admin", "pe@4VXjdTqZ8gguRUE");
        return connection;
    }
}
