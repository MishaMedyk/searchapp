package controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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


        String searchkey = request.getParameter("searchkey");
        System.out.println("servler started with input parameter " + searchkey);


        Statement st;

        try {
            Connection conn = ConnectionFactory.getConnection();

            ArrayList<String> al = null;
            ArrayList<ArrayList<String>> prodsList = new ArrayList<ArrayList<String>>();
            String query = "select * from products where item like '%" + searchkey + "%'";



            System.out.println("query " + query);
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                al = new ArrayList<>();

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

            RequestDispatcher view = request.getRequestDispatcher("/search.jsp");
            view.forward(request, response);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }






    }
}
