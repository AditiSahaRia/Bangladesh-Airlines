package com.example.servlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;


@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/airlines?useSSL=false", "root", "root");
            PreparedStatement preparedStatement = connection.prepareStatement("select * from user where email = ? and password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                session.setAttribute("name", resultSet.getString("name"));
                dispatcher = request.getRequestDispatcher("index.jsp");
            }
            else {
                request.setAttribute("status", "failed");
                dispatcher = request.getRequestDispatcher("login.jsp");
            }
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
