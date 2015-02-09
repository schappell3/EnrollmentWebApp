/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * A servlet used for the implementation of a basic enrollment manager web application.
 * 
 * @author Scott Chappell
 */
public class EnrollmentAdmin extends HttpServlet {
    private boolean initialization;

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
        
        // Creates a string that is a command received from the jsp
        String command = request.getParameter("command");
        
        // Fetch the shared basic enrollment manager from the application object
        BasicEnrollmentManager sharedBEM = (BasicEnrollmentManager)application.getAttribute("theBEM");
        
        try
        {
            // Checks the command parameter from the jsp
            if(command.equals("init")) 
            {
                if(request.getParameter("init_confirm").equals("YES")) 
                {
                    // Creates a new BEM object for the application object
                    application.setAttribute("theBEM", new SafeHW1_BEM()); 
                    
                    // Creates a new Map object to store the student ID and the UserData object
                    application.setAttribute("userDataMap", new HashMap<Integer, UserData>());
                    
                    // Clears the authentication token
                    session.setAttribute("loggedInUser", null);
                    
                    // These three lines repeat throughout the servlet
                    // Sends a message to the user indicating success or failure
                    // and allows the user to refresh the page
                    session.setAttribute("message", "Initialization complete");
                    response.sendRedirect("../admin.jsp");
                    return;
                }
                else
                {
                    session.setAttribute("message", "Initialization requires 'YES' to confirm");
                    response.sendRedirect("../admin.jsp"); 
                    return;
                }
            }
            // Checks the command parameter from the jsp
            else if(command.equals("newstd")) 
            {
                try
                {
                    Integer studentID;  // Return value from the addStudent request
                    
                    // Synchronize the shared BEM
                    synchronized(sharedBEM)
                    {
                        // Send parameters to the addStudent method of the BEM
                        studentID = sharedBEM.addStudent(request.getParameter("newstd_std"));
                    }
                    session.setAttribute("message", "Student added successfully; ID: " + studentID);
                    response.sendRedirect("../admin.jsp");
                    return;
                }
                // Catches StudentEnrollmentException
                catch(StudentEnrollmentException see)
                {
                    session.setAttribute("message", "Student not added; name cannot be null");
                    response.sendRedirect("../admin.jsp");
                    return;
                }
            }
            // Checks the command parameter from the jsp
            else if(command.equals("newcrs")) 
            {
                try
                {
                    boolean courseBoolean;  // Return value from the addCourse request
                    
                    // Synchronize the shared BEM
                    synchronized(sharedBEM)
                    {
                        // Send parameters to the addCourse method of the BEM
                        courseBoolean = sharedBEM.addCourse(request.getParameter("newcrs_crs"),
                            Integer.parseInt(request.getParameter("newcrs_cap")), 
                            request.getParameter("newcrs_des"));
                    }
                    if(courseBoolean == true)
                    {
                        session.setAttribute("message", "Course added successfully");
                        response.sendRedirect("../admin.jsp");
                        return;
                    }
                    else if(courseBoolean == false)
                    {
                        session.setAttribute("message", "Course not added; capacity must be non-negative");
                        response.sendRedirect("../admin.jsp");
                        return;
                    }
                }
                // Catches NumberFormatException
                catch(NumberFormatException nfe)
                {
                    session.setAttribute("message", "Course not added; capacity must be numeric");
                    response.sendRedirect("../admin.jsp");
                    return;
                }
                // Catches CourseEnrollmentException
                catch(CourseEnrollmentException cee)
                {
                    session.setAttribute("message", "Course not added; unique non-null name required");
                    response.sendRedirect("../admin.jsp");
                    return;
                }
            }
            // Checks the command parameter from the jsp
            else if(command.equals("adjust")) 
            {
                try
                {
                    boolean adjustBoolean;  // Return value from the adjustCourseCapacity request
                    
                    // Synchronize the shared BEM
                    synchronized(sharedBEM)
                    {
                        // Send parameters to the adjustCourseCapacity method of the BEM
                        adjustBoolean = sharedBEM.adjustCourseCapacity(request.getParameter("adjust_crs"),
                            Integer.parseInt(request.getParameter("adjust_amt"))); 
                    }
                    if(adjustBoolean == true)
                    {
                        session.setAttribute("message", "Capacity adjusted successfully");
                        response.sendRedirect("../admin.jsp");
                        return;    
                    }
                    else if(adjustBoolean == false)
                    {
                        session.setAttribute("message", "Capacity not adjusted; not enough seats available");
                        response.sendRedirect("../admin.jsp");
                        return; 
                    }
                }
                catch(NumberFormatException nfe)
                {
                    session.setAttribute("message", "Capacity not adjusted; amount must be numeric");
                    response.sendRedirect("../admin.jsp");
                    return;
                }
                catch(CourseEnrollmentException cee)
                {
                    session.setAttribute("message", "Capacity not adjusted; course name unknown");
                    response.sendRedirect("../admin.jsp");
                    return;
                }    
            }
            // Checks the command parameter from the jsp
            else if(command.equals("enroll")) 
            {
                try
                {
                    boolean enrollBoolean;  // Return value from the enrollStudentInCourse request
                    
                    // Synchronize the shared BEM
                    synchronized(sharedBEM)
                    {
                        // Send parameters to the enrollStudentInCourse method of the BEM
                        enrollBoolean = sharedBEM.enrollStudentInCourse(Integer.parseInt(request.getParameter("enroll_std")),
                            request.getParameter("enroll_crs")); 
                    }
                    if(enrollBoolean == true)
                    {
                        session.setAttribute("message", "Student enrolled successfully");
                        response.sendRedirect("../admin.jsp");
                        return;
                    }
                    else if(enrollBoolean == false)
                    {
                        int capacityValue;  // Return value from the getCourseAvailableSeats request
                        
                        // Synchronize the shared BEM
                        synchronized(sharedBEM)
                        {
                            // Send parameters to the getCourseAvailableSeats method of the BEM
                            capacityValue = sharedBEM.getCourseAvailableSeats(request.getParameter("enroll_crs"));
                        }
                        // Checks if the course has enough capacity to add the student
                        if(capacityValue > 0)
                        {
                            session.setAttribute("message", "Student not enrolled; student was already enrolled");
                            response.sendRedirect("../admin.jsp");
                            return;
                        }
                        else
                        {
                            session.setAttribute("message", "Student not enrolled; no available seats");
                            response.sendRedirect("../admin.jsp");
                            return;
                        }
                    }  
                }
                catch(NumberFormatException nfe)
                {
                    session.setAttribute("message", "Student not enrolled; ID must be numeric");
                    response.sendRedirect("../admin.jsp");
                    return;
                }
                catch(StudentEnrollmentException see)
                {
                    session.setAttribute("message", "Student not enrolled; student ID unknown");
                    response.sendRedirect("../admin.jsp");
                    return;       
                }
                catch(CourseEnrollmentException cee)
                {
                    session.setAttribute("message", "Student not enrolled; course name unknown");
                    response.sendRedirect("../admin.jsp");
                    return;       
                }
            }
            // Checks the command parameter from the jsp
            else if(command.equals("endcrs")) 
            {
                try
                {
                    // Checks if the remove course button was checked
                    if(request.getParameter("endcrs_del") == null)
                    {
                        // Synchronize the shared BEM
                        synchronized(sharedBEM)
                        {
                            // Send parameters to the completeCourse method of the BEM
                            sharedBEM.completeCourse(request.getParameter("endcrs_crs"), false);
                        }
                        session.setAttribute("message", "Course completed");
                        response.sendRedirect("../admin.jsp");
                        return;
                    }
                    else
                    {
                        // Synchronize the shared BEM
                        synchronized(sharedBEM)
                        {
                            // Send parameters to the completeCourse method of the BEM
                            sharedBEM.completeCourse(request.getParameter("endcrs_crs"), true);
                        }
                        session.setAttribute("message", "Course completed (and removed)");
                        response.sendRedirect("../admin.jsp");
                        return;
                    }
                }
                catch(CourseEnrollmentException cee)
                {
                    session.setAttribute("message", "Course not completed; course name unknown");
                    response.sendRedirect("../admin.jsp");
                    return;
                }

            }
            // Checks the command parameter from the jsp
            else if(command.equals("remstd")) 
            {
                try
                {
                    boolean removeBoolean;  // Return value from the removeStudent request
                    
                    // Synchronize the shared BEM
                    synchronized(sharedBEM)
                    {
                        // Send parameters to the removeStudent method of the BEM
                        removeBoolean = sharedBEM.removeStudent(Integer.parseInt(request.getParameter("remstd_std")));
                    }
                    if(removeBoolean == true)
                    {
                        session.setAttribute("message", "Student removed successfully");
                        response.sendRedirect("../admin.jsp");
                        return; 
                    }
                    else if(removeBoolean == false)
                    {
                        session.setAttribute("message", "Student not removed; enrolled in a course");
                        response.sendRedirect("../admin.jsp");
                        return;
                    }
                }
                catch(NumberFormatException nfe)
                {
                    session.setAttribute("message", "Student not removed; ID must be numeric");
                    response.sendRedirect("../admin.jsp");
                    return;
                }
                catch(StudentEnrollmentException see)
                {
                    session.setAttribute("message", "Student not removed; student ID unknown");
                    response.sendRedirect("../admin.jsp");
                    return;
                }
            }
            else
            {
                session.setAttribute("message", "Please select a valid command");
                response.sendRedirect("../admin.jsp");
                return; 
            }
        }
        // Catches NullPointerException 
        // Should only occur when the shared BEM is null
        // and when the command parameter is null
        catch(NullPointerException npe)
        {
            if(sharedBEM == null)
            {
                session.setAttribute("message", "Please initialize the system");
                response.sendRedirect("../admin.jsp");
                return; 
            }
            else
            {
                session.setAttribute("message", "Please select a valid command");
                response.sendRedirect("../admin.jsp");
                return; 
            } 
        }
    }                // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
