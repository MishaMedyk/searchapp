package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

@WebServlet(name = "searchservlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //response.setContentType("text/html");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        String searchkey = request.getParameter("searchkey");
        writer.println("You are looking for: " +  searchkey + " And here are the results. Please choose the one that matches your needs best. ");

        PrintWriter out = response.getWriter();
        System.out.println("servler started with input parameter " + searchkey);
        Connection conn = null;
        String url = "jdbc:mysql://localhost:3306/prodapp";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String password = "6666";

        Statement st;

        try {
            System.out.println("try block started");

            Class.forName(driver);
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected!");
            System.out.println("before creating an array");

            ArrayList al = null;
            ArrayList prodsList = new ArrayList();
            System.out.println("array created");

            String query = "select * from products where item='" + searchkey + "' ";
            System.out.println("search querry created");


            System.out.println("query " + query);
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            System.out.println("tbefore while block");
            while (rs.next()) {
                System.out.println("inside the while block");
                al = new ArrayList();


                al.add(rs.getString(1));
                al.add(rs.getString(2));
                al.add(rs.getString(3));
                al.add(rs.getString(4));
                al.add(rs.getString(5));
                al.add(rs.getString(6));
                al.add(rs.getString(7));



                System.out.println("al :: " + al);
                prodsList.add(al);
            }

            request.setAttribute("prodsList", prodsList);
            System.out.println("setatrribute executed");


            System.out.println("forvarded");

            RequestDispatcher view = request.getRequestDispatcher("/search.jsp");
            view.forward(request, response);
            conn.close();
            System.out.println("Disconnected!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/add.jsp");
//        requestDispatcher.forward(request, response);





    }
}
