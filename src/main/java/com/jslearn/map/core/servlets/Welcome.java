package com.jslearn.map.core.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/u623295485mapca", "u623295485admin", "pe@4VXjdTqZ8gguRUE");
            connection.close();
        } catch (ClassNotFoundException e) {
            return "Driver not found: " + e.getMessage();
        } catch (SQLException e) {
            return "Connection error: " + e.getMessage();
        }

        return "successfully connected";
    }
}
