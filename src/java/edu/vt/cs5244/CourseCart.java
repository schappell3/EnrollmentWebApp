/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The purpose of this servlet is to process the add to cart feature of the
 * enrollment application.
 * 
 * @author Scott Chappell
 */
public class CourseCart extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        // Creates a Session object of a request associated with a session
        HttpSession session = request.getSession();
        
        // Checks if the user is logged in
        if( session.getAttribute("loggedInUser") == null )  
        {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Fetch a Set of courses (in the student's cart) from the session object
        Set courseInCart = (Set)session.getAttribute("cart");
        
        // Checks if the courseInCart Set has been initialized yet.
        // Creates a new one if it has not.
        if (courseInCart == null) 
        {
          courseInCart = new HashSet();
          session.setAttribute("cart", courseInCart);
        }
        
        // Checks if the course is already in the cart
        String crs = request.getParameter("crs");
        if(courseInCart.contains(request.getParameter("crs")))
        {
            // Sends error message
            response.sendRedirect("../course.jsp?course=" + crs + "&message=inCart");
            return;  
        }
        else
        {
            // Adds the course to the cart
            courseInCart.add(request.getParameter("crs"));
            
            // Goes to the Cart Content Page
            response.sendRedirect("../cart.jsp");
            return;  
        } 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
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
}
