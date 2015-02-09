<%-- 
    Document   : admin
    Created on : Jul 7, 2013, 1:09:44 PM
    Author     : Scott Chappell
--%>

<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Set"%>
<%@page import="edu.vt.cs5244.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <h2>Administration Page</h2>
        <hr></hr>
        <form action="servlet/edu.vt.cs5244.EnrollmentAdmin">
            <table>
                <thead>
                    <tr>
                        <th>Command</th>
                        <th colspan="3">Parameters</th>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <th><input type="submit" value="Perform Action"></th>
                        <th colspan="3"></th>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <td><input type="radio" name="command" value="init">Initialize System</td>
                        <td>"YES" to confirm:<br><input type="text" name="init_confirm"></td>
                        <td colspan="2"></td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="command" value="newstd">Add Student</td>
                        <td>Name:<br><input type="text" name="newstd_std"></td>
                        <td colspan="2"></td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="command" value="newcrs">Add Course</td>
                        <td>Name:<br><input type="text" name="newcrs_crs"></td>
                        <td>Seats:<br><input type="text" name="newcrs_cap"></td>
                        <td>Description:<br><input type="text" name="newcrs_des"></td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="command" value="adjust">Adjust Course Capacity</td>
                        <td>Course:<br><input type="text" name="adjust_crs"></td>
                        <td>Seats:<br><input type="text" name="adjust_amt"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="command" value="enroll">Enroll Student in Course</td>
                        <td>Student:<br><input type="text" name="enroll_std"></td>
                        <td>Course:<br><input type="text" name="enroll_crs"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="command" value="endcrs">Complete Course</td>
                        <td>Course:<br><input type="text" name="endcrs_crs"></td>
                        <td>Remove:<br><input type="checkbox" name="endcrs_del"></td>
                        <td></td>
                    </tr>
                    <tr>
                        <td><input type="radio" name="command" value="remstd">Remove Student</td>
                        <td>Student:<br><input type="text" name="remstd_std"></td>
                        <td colspan="2"></td>
                    </tr>
                </tbody>
            </table>
        </form>
        <hr></hr>
        <h3>Results: <% // Determines if the message is null
                        // Should only be null the first time the web app is launched
                        if(session.getAttribute("message") == null) 
                        {   %>
                            Please initialize the system
                     <% }
                        else
                        {   %>
                            <%= session.getAttribute("message") %>
                     <% }   %>  </h3>                   
        <hr></hr>
        <%  // Fetch the shared BEM
        BasicEnrollmentManager sharedBEM = (BasicEnrollmentManager)application.getAttribute("theBEM");%>
        <h4>Student list: </h4>
        <%
        // Checks if the shared BEM is not null 
        if(sharedBEM != null)
        {
            // Synchronize the shared BEM
            synchronized(sharedBEM)
            {
               // Retrieves all the student ids from the shared BEM
               Set<Integer> allStudents = sharedBEM.getAllStudents(); 
               
               // Creates a new tree set object
               TreeSet<Integer> orderedStudents = new TreeSet<Integer>();
               
               // Creates a new StringBuilder object
               StringBuilder studentsString = new StringBuilder();
               
               // Adds all the student ids to a tree set
               for (Integer id : allStudents)  
               {
                   orderedStudents.add(id);
               }
               // Retrieves all the student names to put the ids and names into the StringBuilder
               for (Integer id : orderedStudents) 
               {
                    String name = sharedBEM.getStudentName(id);
                    studentsString.append(id + ": " + name + "<br/>");
               } 
               %>
               <%=studentsString%>
               <%
            }     
        }
        %>
        <h4>Course list: </h4>
        <%
        // Checks if the shared BEM is not null 
        if(sharedBEM != null)
        {
            // Synchronize the shared BEM
            synchronized(sharedBEM)
            {
                // Retrieves all the course names from the shared BEM
                Set<String> allCourses = sharedBEM.getAllCourses();
                
                // Creates a new StringBuilder object
                StringBuilder coursesString = new StringBuilder();
                
                // Retrieves all the studens in a course and the course capacity
                // to add the course name, capacity, and student names into the StringBuilder
                for (String crs : allCourses)  
                {
                    Set<Integer> enrolledStudents = sharedBEM.getStudentsForCourse(crs);
                    int cap = sharedBEM.getCourseCapacity(crs);
                    coursesString.append(crs + " (capacity: " + cap + ") Students: "
                            + enrolledStudents + "<br/>");
                }
                %>
                <%=coursesString%>
                <%
            }   
        }
        %>
        <hr></hr>
    </body>
</html>
