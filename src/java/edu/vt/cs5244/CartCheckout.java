/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The purpose of this servlet is to provide a checkout process from a cart.
 * 
 * @author Scott Chappell
 */
public class CartCheckout extends HttpServlet {

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
        
        // Checks if the user is logged in
        if( session.getAttribute("loggedInUser") == null )  
        {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Fetch the shared basic enrollment manager from the application object
        BasicEnrollmentManager sharedBEM = (BasicEnrollmentManager)application.getAttribute("theBEM");
        
        // Fetch the Map of the student IDs and UserData objects from the application object
        Map<Integer, UserData> studentRegistrationMap = (HashMap)application.getAttribute("userDataMap");
        
        // Fetch a Set of courses (in the student's cart) from the session object
        Set courseInCart = (Set)session.getAttribute("cart");
        
        // Checks if the courseInCart Set has been initialized yet.
        // Creates a new one if it has not.
        if (courseInCart == null) 
        {
          courseInCart = new HashSet();
          session.setAttribute("cart", courseInCart);
        }
        
        // Checks if the user clicked on the Clear Cart button
        if(request.getParameter("action").equals("Clear Cart"))
        {
            // Removes all the courses in the cart
            courseInCart.clear();
            response.sendRedirect("../cart.jsp");
            return;  
        }
        // Checks if the user clicked on the Remove button
        else if(request.getParameter("action").equals("Remove"))
        {
            // A new Set of courses that will be removed from the cart
            Set<String> coursesToRemove = new HashSet<String>();
            if(!courseInCart.isEmpty())
            {
                // If the checkbox has been selected, put the course in the coursesToRemove Set
                for(String crs : (Set<String>)courseInCart)
                {
                    if(request.getParameter("remove_" + crs) != null)
                    {
                        coursesToRemove.add(crs);
                    }
                }
                // Remove the courses freom the cart that exists in the coursesToRemove Set
                for(String crs: coursesToRemove)
                {
                    courseInCart.remove(crs);
                }
                response.sendRedirect("../cart.jsp");
                return;    
            }
            else
            {
                response.sendRedirect("../cart.jsp");
                return;
            }
            
        }
        // Checks if the user clicked on the Checkout button
        else if(request.getParameter("action").equals("Checkout"))
        {
            // A new Set of courses that are full
            Set<String> fullCourses = new HashSet<String>();
            
            // A new Set of courses that the student is already enrolled in
            Set<String> alreadyEnrolled = new HashSet<String>();
            
            // The student's ID number
            Integer id = (Integer)session.getAttribute("loggedInUser");
            synchronized(sharedBEM)
            {
                try
                {
                    // Retrieves the courses a student is enrolled in
                    Set<String> coursesForStudent = sharedBEM.getCoursesForStudent(id);

                    // Interate through courses in the cart
                    for(String crs : (Set<String>)courseInCart)
                    {
                            // Checks if the course has room
                            if(sharedBEM.getCourseAvailableSeats(crs) > 0)
                            {
                                // Checks if the student has a course in their cart
                                // that they are already enrolled in
                                if(coursesForStudent.contains(crs))
                                {
                                    // Adds the course to the alreadyEnrolled Set
                                    alreadyEnrolled.add(crs);
                                }
                            }
                            else
                            {
                                // Adds the course to the fullCourses Set
                                fullCourses.add(crs);
                            }
                    }
                    // Checks if the fullCourses Set is empty
                    if(!fullCourses.isEmpty())
                    {
                        // Sends an error message
                        session.setAttribute("fullCourses", fullCourses);
                        response.sendRedirect("../cart.jsp?message=full");
                        return;
                    }
                    // Checks if the alreadyEnrolled Set is empty 
                    else if (!alreadyEnrolled.isEmpty())
                    {
                        // Sends an error message
                        session.setAttribute("alreadyEnrolled", alreadyEnrolled);
                        response.sendRedirect("../cart.jsp?message=enrolled");
                        return;
                    }
                    // Checks if the cart is empty
                    else if(courseInCart.isEmpty())
                    {
                        // Sends a error message
                        response.sendRedirect("../cart.jsp?message=empty");
                        return;
                    }
                    else
                    {
                        // Iterates through the course in the cart
                        for(String crs : (Set<String>)courseInCart)
                        {
                            // Enrolls the student in the course
                            sharedBEM.enrollStudentInCourse(id, crs);
                            
                            // Adds the course to the course history in the UserData object
                            studentRegistrationMap.get(id).setCourseHistory(crs);
                        }
                        // Clears the contents of the cart
                        courseInCart.clear();
                        
                        // Goes to the Student Detail Page
                        response.sendRedirect("../student.jsp");
                        return;
                    }
                }
                catch(StudentEnrollmentException see)
                {
                    throw new RuntimeException("Unexpected exception thrown by BEM", see);      
                }
                catch(CourseEnrollmentException cee)
                {
                    throw new RuntimeException("Unexpected exception thrown by BEM", cee);
                }
            }
            
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
