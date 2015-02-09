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
 * The purpose of this servlet is to process the registration feature of the
 * enrollment application.
 * @author Scott Chappell
 */
public class StudentRegistration extends HttpServlet {

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
        
        // Fetch the shared basic enrollment manager from the application object
        BasicEnrollmentManager sharedBEM = (BasicEnrollmentManager)application.getAttribute("theBEM");
        
        // Fetch the Map of the student IDs and UserData objects from the application object
        Map<Integer, UserData> studentRegistrationMap = (HashMap)application.getAttribute("userDataMap");
        
        // Fetch the UserData object from the session object
        UserData studentRegistration = (UserData)session.getAttribute("userData");
        
        // A new HashMap of message responses
        Map<String, String> messageResponses = new HashMap<String, String>();
        
        // Creates a new UserData object if one does not exist
        if (studentRegistration == null) 
        {
          studentRegistration = new UserData();
          session.setAttribute("userData", studentRegistration);
        }
        
        // Validates the student's name
        // Student Name: max 50 characters, non-blank (meaning not empty, and not all whitespace)
        String name = request.getParameter("std_name");         
        if(name.length() > 50 || name.isEmpty() || name.contains("  "))
        {
            // Puts error message in the message map
            messageResponses.put("messageName", "Invalid name; max 50 characters, "
                    + "forbids two sequential white spaces, and cannot be empty");
            // Sends the name to the UserData object
            studentRegistration.setStdName(name);
        }
        else
        {
            // Sends the name to the UserData object
            studentRegistration.setStdName(name);       
        }
            
        // Validates the student's password
        // Password: exactly 5 letters/digits only (must include at least 2 letters and at least 2 digits)
        String password = request.getParameter("std_password");
        
        // The password can only be letters or numbers and 5 characters long
        if(!password.matches("[a-zA-Z0-9]+") || password.length() != 5)
        {
            // Puts error message in the message map
            messageResponses.put("messagePassword", "Invalid password; can only "
                    + "be 5 letters/digits and requires at least two of each");
            // Sends the password to the UserData object
            studentRegistration.setStdPassword(password);
        }
        else
        {
            // Checks the individual characters of the string to determine if they 
            // are letters or numbers
            Integer letters = 0;
            for(int x = 0; x < password.length(); x++)
            {
                try
                {
                    Integer.parseInt(Character.toString(password.charAt(x)));        
                }
                // If it doesn't pass the parseInt, must be a letter
                catch(NumberFormatException nfe)
                {
                    letters++;
                }
            }
            // Makes sure the password has 2 or 3 letters
            if(letters == 2 || letters == 3)
            {
                // Sends the password to the UserData object
                studentRegistration.setStdPassword(password);
            }
            else
            {
                // Puts error message in the message map 
                messageResponses.put("messagePassword", "Invalid password; can only "
                    + "be 5 letters/digits and requires at least two of each");
                // Sends the password to the UserData object
                studentRegistration.setStdPassword(password);
            }
            
        }   

        // Validates the student's re-typed password
        // Password confirmation: must exactly match password (above)
        String rePassword = request.getParameter("std_repassword");
        if(rePassword.equals(password))
        {
            // Sends the re-typed password to the UserData object
            studentRegistration.setStdRePassword(rePassword);
        }
        else
        {
            // Puts error message in the message map 
            messageResponses.put("messageRePassword", "Invalid; does not match password");
            // Sends the re-typed password to the UserData object
            studentRegistration.setStdRePassword(rePassword);
        }
            
        // Validates the student's city
        // City: max 20 characters, non-blank (meaning not empty, and not all whitespace)
        String city = request.getParameter("std_city");
        if(city.length() > 20 || city.isEmpty() || city.contains("  "))
        {
            // Puts error message in the message map
            messageResponses.put("messageCity", "Invalid city name; max 20 characters,"
                    + " forbids two sequential white spaces, and cannot be empty");
            // Sends the city to the UserData object
            studentRegistration.setStdCity(city);
        }
        else
        {  
            // Sends the city to the UserData object
            studentRegistration.setStdCity(city);    
        }

        // Validates the student's state
        // State: exactly 2 upper-case letters
        String state = request.getParameter("std_state");
        if(state.length() == 2 && state.matches("[A-Z]+"))
        {
            // Sends the state to the UserData object
            studentRegistration.setStdState(state);  
        }
        else
        {
            // Puts error message in the message map
            messageResponses.put("messageState", 
                    "Invalid state code; must be exactly two uppercase letters");
            // Sends the state to the UserData object
            studentRegistration.setStdState(state);
        }

        // Validates the first 5 digits of the students zip code 
        // ZIP code: exactly 5 digits
        String zip1 = request.getParameter("std_zip1");
        if(zip1.length() == 5 && zip1.matches("[0-9]+"))
        {
            // Sends the first 5 digits of the zip code to the UserData object
            studentRegistration.setStdZip1(zip1);
        }
        else
        {
            // Puts error message in the message map
            messageResponses.put("messageZip", 
                    "Invalid zip code; must be exactly 5 or 9 digits");
            // Sends the first 5 digits of the zip code to the UserData object
            studentRegistration.setStdZip1(zip1);

        }

        // Validates the last 4 digits of the students zip code
        // Exactly 9 digits; presented as 2 separate fields, in groupings of 5 digits and 4 digits 
        String zip2 = request.getParameter("std_zip2");
        if(zip2.length() == 4 && zip2.matches("[0-9]+") || zip2.isEmpty())
        {
            // Sends the last 4 digits of the zip code to the UserData object
            studentRegistration.setStdZip2(zip2);
        }
        else
        {
            // Puts error message in the message map
            messageResponses.put("messageZip", 
                    "Invalid zip code; must be exactly 5 or 9 digits");
            // Sends the last 4 digits of the zip code to the UserData object
            studentRegistration.setStdZip2(zip2);

        }

        // Validates the first 3 digits of the students phone number
        // Phone: exactly 10 digits; presented as 3 separate fields, in groupings 
        // of 3 digits, 3 digits, and 4 digits
        String phone1 = request.getParameter("std_phone1");
        if(phone1.length() == 3 && phone1.matches("[0-9]+"))
        {
            // Sends the first 3 digits of the phone number to the UserData object
            studentRegistration.setStdPhone1(phone1);
        }
        else
        {
            // Puts error message in the message map
            messageResponses.put("messagePhone", 
                    "Invalid phone number; must be exactly 10 digits");
            // Sends the first 3 digits of the phone number to the UserData object
            studentRegistration.setStdPhone1(phone1);
        }

        // Validates the middle 3 digits of the students phone number
        // Phone: exactly 10 digits; presented as 3 separate fields, in groupings 
        // of 3 digits, 3 digits, and 4 digits
        String phone2 = request.getParameter("std_phone2");
        if(phone2.length() == 3 && phone2.matches("[0-9]+"))
        {
            // Sends the middle 3 digits of the phone number to the UserData object
            studentRegistration.setStdPhone2(phone2);
        }
        else
        {
            // Puts error message in the message map
            messageResponses.put("messagePhone", 
                    "Invalid phone number; must be exactly 10 digits");
            // Sends the middle 3 digits of the phone number to the UserData object
            studentRegistration.setStdPhone2(phone2);
        }

        // Validates the last 4 digits of the students phone number
        // Phone: exactly 10 digits; presented as 3 separate fields, in groupings 
        // of 3 digits, 3 digits, and 4 digits
        String phone3 = request.getParameter("std_phone3");
        if(phone3.length() == 4 && phone3.matches("[0-9]+"))
        {
            // Sends the last 4 digits of the phone number to the UserData object
            studentRegistration.setStdPhone3(phone3);
        }
        else
        {
            // Puts error message in the message map
            messageResponses.put("messagePhone", 
                    "Invalid phone number; must be exactly 10 digits");
            // Sends the last 4 digits of the phone number to the UserData object
            studentRegistration.setStdPhone3(phone3);
        }    
        
        // Validates that the user selected an option for the first survey question
        String educationSurvey = request.getParameter("education");
        if(educationSurvey != null)
        {
            // Sends selection from the first survey question to the UserData object
            studentRegistration.setStdSurvey1(educationSurvey);
        }
        else
        {
            // Puts error message in the message map
            messageResponses.put("messageSurvey", "Please select a choice");
        }
        
        // Individually checks if a checkbox from the second survey question has been checked
        // If yes, adds each select to a survey question 2 Set in the UserData object
        String adSurvey1 = request.getParameter("Checkbox1");
        if(adSurvey1 != null)
        {
            studentRegistration.setStdSurvey2(adSurvey1);
        }
        String adSurvey2 = request.getParameter("Checkbox2");
        if(adSurvey2 != null)
        {
            studentRegistration.setStdSurvey2(adSurvey2);
        }
        String adSurvey3 = request.getParameter("Checkbox3");
        if(adSurvey3 != null)
        {
            studentRegistration.setStdSurvey2(adSurvey3);
        }
        String adSurvey4 = request.getParameter("Checkbox4");
        if(adSurvey4 != null)
        {
            studentRegistration.setStdSurvey2(adSurvey4);
        }
        
        // Checks if the message response map is empty
        if(messageResponses.isEmpty())
        {
            try
            {
                // Add the student and generate a student ID
                Integer id = sharedBEM.addStudent(request.getParameter("std_name"));
                
                // Put the student ID and the corresponding UserData object in the Map
                studentRegistrationMap.put(id, studentRegistration);
                
                // Makes sure the messageLogin object for the login page doesn't give an error
                session.setAttribute("messageLogin", null);
                
                // Goes to the Registration Success Page
                response.sendRedirect("../success.jsp?id=" + id);
                return;
            }
            catch(StudentEnrollmentException see)
            {
                throw new RuntimeException("Unexpected exception thrown by BEM", see);     
            }  
        }
        else
        {
            // Sends the error messages as a map to the session object
            session.setAttribute("messageMap", messageResponses);
            response.sendRedirect("../registration.jsp");
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
