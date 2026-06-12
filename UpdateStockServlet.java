package com.smartretail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/updateStock")
public class UpdateStockServlet extends HttpServlet {

    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");

        try {

            String name =
                    request.getParameter("name");

            int qty =
                    Integer.parseInt(
                            request.getParameter("qty"));

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "UPDATE products SET quantity = quantity - ? WHERE name = ?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setInt(1, qty);

            ps.setString(2, name);

            int rows = ps.executeUpdate();

            if (rows > 0) {

                response.getWriter()
                        .print("Stock Updated");

            } else {

                response.getWriter()
                        .print("Product Not Found");
            }

            ps.close();

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter()
                    .print("Error : " + e.getMessage());
        }
    }
}