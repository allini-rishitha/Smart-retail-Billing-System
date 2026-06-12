package com.smartretail;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addProduct")
public class ProductServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)

            throws ServletException, IOException {

        response.setContentType("text/plain");

        PrintWriter out = response.getWriter();

        try {

            String name =
                    request.getParameter("name");

            double price =
                    Double.parseDouble(
                            request.getParameter("price"));

            int quantity =
                    Integer.parseInt(
                            request.getParameter("quantity"));

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "INSERT INTO products(name,price,quantity) VALUES(?,?,?)";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(1, name);

            ps.setDouble(2, price);

            ps.setInt(3, quantity);

            int rows = ps.executeUpdate();

            if(rows > 0) {

                out.print("Product Added Successfully");

            } else {

                out.print("Insert Failed");

            }

            ps.close();

            con.close();

        } catch(Exception e) {

            e.printStackTrace();

            out.print("Error : " + e.getMessage());
        }
    }
}