package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.*;

@WebServlet(name = "Registration", value = "/Registration")
public class Registration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("pass");
        String userContact = request.getParameter("contact");
        int contact = Integer.parseInt(userContact);
        String userType = "user";

        RequestDispatcher dispatcher = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/airlines?useSSL=false", "root", "root");
            String query = "INSERT INTO user (name, email, password, userType, contact) values (?, ?, ?, ? , ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, userType);
            preparedStatement.setInt(5, contact);

            int rowCount = preparedStatement.executeUpdate();

            dispatcher = request.getRequestDispatcher("registration.jsp");
            if (rowCount > 0) {
                request.setAttribute("status", "success");
            }
            else {
                request.setAttribute("status", "failed");
            }
            dispatcher.forward(request, response);
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}