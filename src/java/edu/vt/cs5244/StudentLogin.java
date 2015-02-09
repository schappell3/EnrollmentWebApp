/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The purpose of this servlet is to process the login feature of the
 * enrollment application.
 * 
 * @author Scott Chappell
 */
public class StudentLogin extends HttpServlet {

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
        // Creates an Application object of a servlet context
        ServletContext application = getServletContext();
        
        // Creates a Session object of a request associated with a session
        HttpSession session = request.getSession();
        
        // Fetch the Map of the student IDs and UserData objects from the application object
        Map<Integer, UserData> studentRegistrationMap = (HashMap)application.getAttribute("userDataMap");
        
        // The student ID
        String username = request.getParameter("username");
        try
        {
            // Convert the student ID string to an Integer
            Integer usernameInteger = Integer.parseInt(username);
            
            // Checks if the student ID exists in the system
            if(studentRegistrationMap.containsKey(usernameInteger))
            {
                // The student's password
                String password = request.getParameter("password");
                
                // Checks if the student's password matches for their student ID
                if(studentRegistrationMap.get(usernameInteger).getStdPassword().equals(password))
                {
                    // Creates the authentication token
                    session.setAttribute("loggedInUser", usernameInteger);
                    
                    // Creates a valid message object for messages related to the JSP
                    session.setAttribute("messageLogin", "Valid");
                    response.sendRedirect("../enrollment.jsp");
                    return;
                }
                else
                {
                    // Sends an error message
                    session.setAttribute("messageLogin", "Invalid student id or password");
                    response.sendRedirect("../login.jsp");
                    return;
                }
            }
            else
            {
                // Sends an error message
                session.setAttribute("messageLogin", "Invalid student id or password");
                response.sendRedirect("../login.jsp");
                return;
            }
        }
        // If the student ID string cannot be converted to an Integer, sends error message
        catch(NumberFormatException nfe)
        {
            // Sends an error message
            session.setAttribute("messageLogin", "Invalid student id or password");
            response.sendRedirect("../login.jsp");
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
