/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owner
 */
@WebServlet(name = "Books", urlPatterns = {"/Books"})
public class Books extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request Books request
     * @param response Books response
     * @throws ServletException if a Books-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private final String url = "jdbc:postgresql://localhost:5432/BooksForAll";
    private final String user = "postgres";//"USER_PG";
    private final String password = "123456";
    // private final Boolean ssl = true;

    public Connection connect() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the PostgreSQL server successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            conn = null;
        }

        return conn;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Books</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Server Books at " + request.getContextPath() + "</h1>");
            out.println("<p>DOGETBEGIN</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        StringBuffer sb = new StringBuffer();
        System.out.println("DO_GET_BEGIN");
        Statement stmt = null;
        Connection conn = null;
    
        context.getRequestDispatcher("/error.jsp").forward(request, response);
      
        boolean added = false;
        if (action.equals("list_books")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT b.\"ID\", \"Name\",\"Last name\", \"Title\", image\n"
                        + "	FROM public.\"Book\" b,public.\"Authors\" a\n"
                        + "	WHERE a.\"ID\"=b.author\n"
                        + "	LIMIT 3");
                while (rs.next()) {
                    sb.append("<book>");
                    sb.append("<ID>" + rs.getString("ID") + "</ID>");
                    sb.append("<Author>" + rs.getString("Name") + " " + rs.getString("Last name") + "</Author>");
                    sb.append("<Title>" + rs.getString("Title") + "</Title>");
                    sb.append("<Image>" + rs.getString("image") + "</Image>");
                    added = true;
                }

            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (added) {
                response.setContentType("test/xml");
                response.setHeader("Cache-control", "no-cache");
                response.getWriter().write("<books_list>" + sb.toString() + "</books_list>");
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
        if (action.equals("login")) {
            try {
                conn = connect();
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT \"ID\", \"Name\", \"Last name\", \"Birth Date\", \"e-mail\", password, address, \"registration date\"\n"
                        + "                                                 FROM public.\"User\"\n"
                        + "                                                 where e-mail ='"
                        + "' and password = '" + "'");
                if (rs != null) {
                    sb.append("<User>");
                    sb.append("<ID>" + rs.getString("ID") + "</ID>");
                    sb.append("<Full Name>" + rs.getString("Name") + " " + rs.getString("Last name") + "</Full name>");
                    sb.append("<Bdate>" + rs.getString("Birth Date") + "</Bdate>");
                    sb.append("<E-mail>" + rs.getString("e-mail") + "</E-mail>");
                    sb.append("<password>" + rs.getString("password") + "</password>");
                    sb.append("<address>" + rs.getString("address") + "</address>");
                    sb.append("<address>" + rs.getString("address") + "</address>");
                    sb.append("<registration date>" + rs.getString("registration date") + "/<registration date");
                } else {
                    sb.append("<User>");
                    sb.append("<ID> User not found </ID>");
                }

            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (added) {
                response.setContentType("test/xml");
                response.setHeader("Cache-control", "no-cache");
                response.getWriter().write("<books_list>" + sb.toString() + "</books_list>");
            } else {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        }
        if (action.equals("renewpass")) {

        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    private ServletContext context;

}