/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.ajax;

import com.postgresql.PostgreSQL;
import static com.sun.xml.bind.util.CalendarConv.formatter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
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
import java.sql.Types;
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Owner
 */
@WebServlet(name = "Servlet", urlPatterns = {"/Servlet"})
public class Servlet extends HttpServlet {

    private final String url = "jdbc:postgresql://localhost:5432/BooksForAll";
    private final String user = "USER_PG";//"postgres";
    private final String password = "123456";

    @Override
    // This function handles all the options of user-side requests( JS) and sends XML-formatted
    //answers back to user. Each if-case covers a different action from user
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        // connection to Postgresql database
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        boolean added = false;
        Statement stmt = null;
        Connection conn = null;;
        String action = req.getParameter("action");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String ID = req.getParameter("ID");
        String order_id = req.getParameter("order_id");
        String book_id = req.getParameter("book_id");
        String amount = req.getParameter("amount");
        String name = req.getParameter("name");
        String lname = req.getParameter("lname");
        String day = req.getParameter("day");
        String month = req.getParameter("month");
        String year = req.getParameter("year");
        String str_find = req.getParameter("search");
        String country = req.getParameter("country");
        String city = req.getParameter("city");
        String street = req.getParameter("street");
        String hn = req.getParameter("home");
        String postcode = req.getParameter("postcode");
        StringBuffer sb = new StringBuffer();
        String str = "";
        // This action shows books on a main page
        if (action.equals("list_books")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT b.\"ID\", \"Name\",\"Last name\", \"Title\", image\n , price"
                        + "	FROM public.\"Book\" b,public.\"Authors\" a\n"
                        + "	WHERE a.\"ID\"=b.author\n"
                        + "	LIMIT 12");
                while (rs.next()) {
                    sb.append("<book>");
                    sb.append("<ID>" + rs.getString("ID") + "</ID>");
                    sb.append("<Author>" + rs.getString("Name") + " " + rs.getString("Last name") + "</Author>");
                    sb.append("<Title>" + rs.getString("Title") + "</Title>");
                    sb.append("<Image>" + rs.getString("image") + "</Image>");
                    sb.append("<Price>" + rs.getString("price") + "</Price>");
                    sb.append("</book>");
                    added = true;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (added) {
                resp.setContentType("text/xml");
                resp.setHeader("Cache-control", "no-cache");
                resp.getWriter().write("<list_books>" + sb.toString() + "</list_books>");
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
          // This action handles an addition of a bokk to cart
        } else if (action.equals("cart")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select c.book_id,c.amount,a.\"Name\",a.\"Last name\",b.\"Title\",b.image,b.price\n"
                        + "	from public.\"Cart\" c,public.\"Book\" b,public.\"Authors\" a\n"
                        + "	where c.book_id=b.\"ID\" and b.author=a.\"ID\"\n"
                        + "	and c.\"User\"=" + ID + ";");

                while (rs.next()) {
                    sb.append("<book>");
                    sb.append("<ID>" + rs.getString("book_id") + "</ID>");
                    sb.append("<Author>" + rs.getString("Name") + " " + rs.getString("Last name") + "</Author>");
                    sb.append("<Title>" + rs.getString("Title") + "</Title>");
                    sb.append("<Image>" + rs.getString("image") + "</Image>");
                    sb.append("<Price>" + rs.getDouble("price") + "</Price>");
                    sb.append("<Amount>" + rs.getInt("amount") + "</Amount>");
                    sb.append("</book>");
                    added = true;
                }

            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (added) {
                resp.setContentType("text/xml");
                resp.setHeader("Cache-control", "no-cache");
                resp.getWriter().write("<cart>" + sb.toString() + "</cart>");
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
            // This action handles remove a book from the cart
        } else if (action.equals("remove")) {
            conn = connect();
            try {

                stmt = conn.createStatement();
                str = "delete from  public.\"Cart\" c\n"
                        + "where c.\"User\"=" + ID + " and c.book_id=" + book_id + " and \"Id\"=(select max(\"Id\")\n"
                        + "		from  public.\"Cart\" c\n"
                        + "where c.\"User\"=" + ID + " and c.book_id=" + book_id + ");";

                stmt.executeUpdate(str);

                ResultSet rs = stmt.executeQuery("select c.book_id,c.amount,a.\"Name\",a.\"Last name\",b.\"Title\",b.image,b.price\n"
                        + "	from public.\"Cart\" c,public.\"Book\" b,public.\"Authors\" a\n"
                        + "	where c.book_id=b.\"ID\" and b.author=a.\"ID\"\n"
                        + "	and c.\"User\"=" + ID + ";");

                while (rs.next()) {
                    sb.append("<book>");
                    sb.append("<ID>" + rs.getString("book_id") + "</ID>");
                    sb.append("<Author>" + rs.getString("Name") + " " + rs.getString("Last name") + "</Author>");
                    sb.append("<Title>" + rs.getString("Title") + "</Title>");
                    sb.append("<Image>" + rs.getString("image") + "</Image>");
                    sb.append("<Price>" + rs.getDouble("price") + "</Price>");
                    sb.append("<Amount>" + rs.getInt("amount") + "</Amount>");
                    sb.append("</book>");
                    added = true;
                }

            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (added) {
                resp.setContentType("text/xml");
                resp.setHeader("Cache-control", "no-cache");
                resp.getWriter().write("<remove>" + sb.toString() + "</remove>");
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
            // This action handles a book page with it's details
        } else if (action.equals("book")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT b.\"ID\", a.\"Name\",a.\"Last name\", about, price, \"year published\",\n"
                        + "format, amount, p.\"Name\"  \"Publisher\", \"ISBN\", g.\"Genre\", \"Title\", image\n"
                        + "	FROM public.\"Book\" b, public.\"Authors\" a,public.\"Publishers\" p, public.\"Genres\" g\n"
                        + "where b.author = a.\"ID\" and b.publisher=p.\"ID\" \n"
                        + "and b.genre=g.\"ID\" and b.\"ID\" =" + ID + ";");

                while (rs.next()) {
                    sb.append("<book>");
                    sb.append("<ID>" + rs.getString("ID") + "</ID>");
                    sb.append("<Author>" + rs.getString("Name") + " " + rs.getString("Last name") + "</Author>");
                    sb.append("<About>" + rs.getString("about") + "</About>");
                    sb.append("<Year_published>" + rs.getInt("year published") + "</Year_published>");
                    sb.append("<Format>" + rs.getString("format") + "</Format>");
                    sb.append("<Amount>" + rs.getInt("amount") + "</Amount>");
                    sb.append("<Publisher>" + rs.getString("Publisher") + "</Publisher>");
                    sb.append("<ISBN>" + rs.getString("ISBN") + "</ISBN>");
                    sb.append("<Genre>" + rs.getString("Genre") + "</Genre>");
                    sb.append("<Title>" + rs.getString("Title") + "</Title>");
                    sb.append("<Image>" + rs.getString("image") + "</Image>");
                    sb.append("<Price>" + rs.getDouble("price") + "</Price>");
                    sb.append("</book>");
                    added = true;

                    Book book = new Book(
                            rs.getString("Title"),
                            rs.getString("Name") + " " + rs.getString("Last name"),
                            rs.getString("about"),
                            rs.getString("Genre"),
                            rs.getInt("year published"),
                            rs.getString("Publisher"),
                            rs.getString("format"),
                            rs.getString("ISBN"),
                            rs.getString("image"),
                            rs.getDouble("price"),
                            rs.getInt("amount"));
                }
            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (added) {
                resp.setContentType("text/xml");
                resp.setHeader("Cache-control", "no-cache");
                resp.getWriter().write("<book_detail>" + sb.toString() + "</book_detail>");
            } else {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
            // This function handles search for a book by Author/Title/ISBN
        } else if (action.equals("search")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT b.\"ID\", \"Name\",\"Last name\", \"Title\", image\n , price"
                        + "	FROM public.\"Book\" b,public.\"Authors\" a\n"
                        + "	WHERE a.\"ID\"=b.author\n"
                        + "	and (a.\"Name\" ILIKE '%" + str_find + "%' \n"
                        + "	OR a.\"Last name\" ILIKE '%" + str_find + "%' \n"
                        + "OR b.\"Title\"  ILIKE '%" + str_find + "%' \n"
                        + "OR \"ISBN\"  ILIKE '%" + str_find + "%' )");
                while (rs.next()) {
                    sb.append("<book>");
                    sb.append("<ID>" + rs.getString("ID") + "</ID>");
                    sb.append("<Author>" + rs.getString("Name") + " " + rs.getString("Last name") + "</Author>");
                    sb.append("<Title>" + rs.getString("Title") + "</Title>");
                    sb.append("<Image>" + rs.getString("image") + "</Image>");
                    sb.append("<Price>" + rs.getString("price") + "</Price>");
                    sb.append("</book>");
                    added = true;
                }

                //  resp.getWriter().println(sb.toString());
            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (!added) {
                sb.append("<Result>0</Result>");
            }
            resp.setContentType("text/xml");
            resp.setHeader("Cache-control", "no-cache");
            resp.getWriter().write("<search>" + sb.toString() + "</search>");

            // This action handles a login into existing account
        } else if (action.equals("login")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                str = "SELECT \"ID\", \"Name\", \"Last name\", \"Birth Date\", \"e-mail\", password, address,\"ad permission\", \"registration date\"\n"
                        + "                                                 FROM public.\"User\"\n"
                        + "                                                 where \"e-mail\" ='" + email
                        + "' and password = '" + password + "' ;";
                ResultSet rs = stmt.executeQuery(str);

                if (rs.next()) {
                    User user = new User(rs.getString("Name"),
                            rs.getString("Last name"),
                            rs.getDate("Birth Date"),
                            rs.getString("e-mail"),
                            rs.getString("password"),
                            rs.getDate("registration date"),
                            rs.getBoolean("ad permission"),
                            rs.getString("address"));

                    sb.append("<ID>" + rs.getString("ID") + "</ID>");
                    sb.append("<Name>" + rs.getString("Name") + "</Name>");
                    added = true;
                } else {

                    sb.append("<ID>0</ID>");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            resp.setContentType("text/xml");
            resp.setHeader("Cache-control", "no-cache");
            resp.getWriter().write("<Login>" + sb.toString() + "</Login>");
//                      } else {
//                          resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//                     }
        // This action handles a registration 
        } else if (action.equals("reg")) {
            conn = connect();

            try {
                stmt = conn.createStatement();
                str = "SELECT \"ID\"\n"
                        + "	FROM public.\"User\"\n"
                        + "	where  \"e-mail\"='" + email + "';";
                ResultSet rs = stmt.executeQuery(str);
                if (rs.next()) {
                    sb.append("<Result>0</Result>");
                } else {
                    str = "INSERT INTO public.\"User\"(\n"
                            + "	\"Name\", \"Last name\", \"Birth Date\", \"e-mail\", password, \"registration date\", \"ad permission\")\n"
                            + "	VALUES ('" + name + "','" + lname + "','" + year + "-" + month + "-" + day + "','" + email + "','" + password + "', CURRENT_DATE, 'false');";
                    stmt.executeUpdate(str);
                    LocalDate today = LocalDate.now();
                    String stoday = today.toString();
                    Date date_today = null;
                    try {
                        date_today = new SimpleDateFormat("yyyy-mm-dd").parse(stoday);
                    } catch (ParseException ex) {
                        Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (conn != null) {
                        try {
                            conn.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    String sdate = day + "/" + month + "/" + year;
                    Date date1 = null;
                    try {
                        date1 = new SimpleDateFormat("dd/MM/yyyy").parse(sdate);
                    } catch (ParseException ex) {
                        Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    User user = new User(name,
                            lname,
                            date1,
                            email,
                            password,
                            date_today,
                            false,
                            null);
                    sb.append("<Result>1</Result>");
                }
            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }

            resp.setContentType("text/xml");
            resp.setHeader("Cache-control", "no-cache");
            resp.getWriter().write("<Reg>" + sb.toString() + "</Reg>");
            //This action saves details of order
        } else if (action.equals("new_order")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                str = "Insert into public.\"Orders\"(\"User ID\")Values(" + ID + ");";
                stmt.executeUpdate(str);

                str = "select max(\"ID\") \"Order\"\n"
                        + "from public.\"Orders\";";
                ResultSet rs = stmt.executeQuery(str);
                if (rs.next()) {
                    sb.append("<Order>" + rs.getInt("Order") + "</Order>");
                    added = true;
                } else {

                    sb.append("<Order>0</Order>");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            resp.setContentType("text/xml");
            resp.setHeader("Cache-control", "no-cache");
            resp.getWriter().write("<new_order>" + sb.toString() + "</new_order>");
//                      } else {
//                          resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//                     }
        // This action saves a request for a book that is not avialible to buy
        } else if (action.equals("request")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                str = "INSERT INTO public.\"Requests\"(\"User ID\", \"Book ID\", \"Request date\", \"Status\")\n"
                        + "	VALUES (" + ID + "," + book_id + ",CURRENT_DATE, 'false');";
                stmt.executeUpdate(str);
                sb.append("<Result>1</Result>");

            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            resp.setContentType("text/xml");
            resp.setHeader("Cache-control", "no-cache");
            resp.getWriter().write("<request>" + sb.toString() + "</request>");
//                      } else {
//                          resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//                     }
        // This action saves the details of the order and updates it
        } else if (action.equals("pay")) {
            conn = connect();
            int p_id = 0;
            try {
                stmt = conn.createStatement();

                str = "update public.\"User\"\n"
                        + "	SET address = '" + street + " " + hn + " " + city + " " + country + " " + postcode + "'\n"
                        + "	where \"ID\"=" + ID + ";";
                stmt.executeUpdate(str);

                str = "insert into public.\"Orders\"(\"User ID\", amount, \"total pay\", \"payment status\")\n"
                        + "select c.\"User\",  sum(c.amount),sum(c.amount*price) ,'false'\n"
                        + "from public.\"Cart\" c,public.\"Book\" b\n"
                        + "where c.book_id=b.\"ID\"\n"
                        + "and c.\"User\"=" + ID + "\n"
                        + "group by c.\"User\";";

                stmt.executeUpdate(str);

                str = "select max(\"ID\") pid\n"
                        + "from public.\"Orders\"\n"
                        + "where \"User ID\"=" + ID + ";";
                ResultSet rs = stmt.executeQuery(str);
                if (rs.next()) {
                    p_id = rs.getInt("pid");
                    added = true;
                } else {

                    sb.append("<Order>0</Order>");

                }

                if (added) {
                    str = " INSERT INTO public.\"Order details\"(\n"
                            + "	 \"Order date\", \"Book id\", \"Order status\", amount, \"Order_ID\")\n"
                            + "select CURRENT_DATE,book_id,'false',amount," + p_id + "\n"
                            + "from public.\"Cart\" c\n"
                            + "where c.\"User\"=" + ID + ";";

                    stmt.executeUpdate(str);

                    str = "select c.book_id,c.amount\n"
                            + "from public.\"Cart\" c\n"
                            + "where c.\"User\"=" + ID + ";";

                    rs = stmt.executeQuery(str);
                    while (rs.next()) {

                        str = "  update public.\"Book\" b\n"
                                + "   set amount=amount-" + rs.getInt("amount") + "\n"
                                + "   where b.\"ID\"=" + rs.getInt("book_id") + ";";
                        stmt.executeUpdate(str);
                    }
                    str = "DELETE FROM public.\"Cart\"\n"
                            + "	WHERE \"User\"=" + ID + ";";

                    stmt.executeUpdate(str);
                    sb.append("<Order>" + p_id + "</Order>");

                }
            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            resp.setContentType("text/xml");
            resp.setHeader("Cache-control", "no-cache");
            resp.getWriter().write("<pay>" + sb.toString() + "</pay>");
            // This action handles a adding a book into cart
        } else if (action.equals("cart_book")) {
            conn = connect();
            try {
                stmt = conn.createStatement();
                str = "INSERT INTO public.\"Cart\"(\n"
                        + "	 book_id, amount, \"User\")\n"
                        + "	VALUES (" + book_id + "," + amount + "," + ID + ");";
                stmt.executeUpdate(str);
                sb.append("<Result>1</Result>");

            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            resp.setContentType("text/xml");
            resp.setHeader("Cache-control", "no-cache");
            resp.getWriter().write("<cart_book>" + sb.toString() + "</cart_book>");
//                      } else {
//                          resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
//                     }
        // This action handles a renewing a passord of an existing user
        } else if (action.equals("forgot")) {
            conn = connect();

            try {
                stmt = conn.createStatement();
                str = "SELECT \"ID\"\n"
                        + "	FROM public.\"User\"\n"
                        + "	where  \"e-mail\"='" + email + "';";
                ResultSet rs = stmt.executeQuery(str);
                if (!rs.next()) {
                    sb.append("<Result>0</Result>");
                } else {
                    str = "UPDATE public.\"User\"\n"
                            + "  SET password='" + password
                            + "'   where  \"e-mail\"='" + email + "';";
                    stmt.executeUpdate(str);
                    sb.append("<Result>1</Result");
                }

            } catch (SQLException ex) {
                Logger.getLogger(Books.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            resp.setContentType("text/xml");
            resp.setHeader("Cache-control", "no-cache");
            resp.getWriter().write("<forgot>" + sb.toString() + "</forgot>");
        } else {
            resp.setContentType("text/plain");
            resp.getWriter().println("Servlet wrote action  another! ");
        }
        //  catch (ClassNotFoundException ex) {
        //       Logger.getLogger(Servlet.class.getName()).log(Level.SEVERE, null, ex);
        //       }
    }
    // Function that creates a connection with database
    public Connection connect() {
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            conn = null;
        }

        return conn;
    }
}
