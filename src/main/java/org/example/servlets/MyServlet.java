package org.example.servlets;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/test")
public class MyServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //получаем параметры запроса
        String squares = req.getParameter("square");
        //получаем объект для записи данных на страницу
        PrintWriter out = resp.getWriter();
        try {
            //создаем подключение к СУБД H2
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:~/test");
            //получаем данные из БД
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT id, number, square FROM APARTMENTS WHERE SQUARE=squares");
            //записываем данные на страницу
            while (rs.next()) {
                out.println(rs.getString("id") + " " + rs.getString("number")+ " " +rs.getString("square") );
            }
        } catch (SQLException | ClassNotFoundException sqlex) {
            out.print(sqlex);
        }
        out.close();
    }
}