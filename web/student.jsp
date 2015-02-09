<%-- 
    Document   : student
    Created on : Aug 6, 2013, 2:47:00 AM
    Author     : Scott Chappell
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="edu.vt.cs5244.BasicEnrollmentManager"%>
<%@page import="java.util.Set"%>
<%@page import="edu.vt.cs5244.UserData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <h2>Student Detail Page</h2>
        <hr></hr>
        <%
        // Checks for the authentication token
        Integer id = (Integer)session.getAttribute("loggedInUser");    
        if( session.getAttribute("loggedInUser") == null )  
        {
            response.sendRedirect("login.jsp?message=needLogin");
            return;
        }
        // Displays the student's name
        else
        { 
            BasicEnrollmentManager sharedBEM = (BasicEnrollmentManager)application.getAttribute("theBEM");
        %>
            <h4><%= sharedBEM.getStudentName(id) %>
            <form action="servlet/edu.vt.cs5244.StudentLogout">
                <tfoot>
                    <input type="submit" value="Logout" class="logout"></h4>
                </tfoot>
            </form>
        <%
        }
        %>
        <hr></hr>
        <%  // Fetch the shared BEM
        BasicEnrollmentManager sharedBEM = (BasicEnrollmentManager)application.getAttribute("theBEM");%>
        <h4>Courses currently enrolled in</h4>
        <hr></hr>
        <% 
        // Retrieves all the courses a student is enrolled in
        Set<String> coursesEnrolled = sharedBEM.getCoursesForStudent(id);
                     
        // Displays all the course the student is enrolled in
        for (String crs : coursesEnrolled)  
        { 
            %>
            <a href="course.jsp?course=<%=crs%>"><%=crs%><br></a>
            <%
        }         
        %>
        <hr></hr>        
        <h4>Past Course History</h4>
        <hr></hr>
        <%
        // Fetches the map with the student ID number and the UserData object from the application object
        Map<Integer, UserData> studentRegistrationMap = (HashMap)application.getAttribute("userDataMap");
        
        // A list of all the courses a student has enrolled in
        List<String> courseHistory = studentRegistrationMap.get(id).getCourseHistory();
            
        // Logic to display all the course names that the student is currently
        // not enrolled in, but have been enrolled in (in the past)
        for(String course : courseHistory)
        {
            int courseCounter = 0;
            for(String crs : coursesEnrolled)
            {
                if(crs.equals(course))
                {
                    courseCounter++;
                }
            }
            if(courseCounter == 0)
            {
            %>
                <a href="course.jsp?course=<%=course%>"><%=course%><br></a>
            <%  
            }    
        }
            
        // Logic to display all the course names of the courses the student is 
        // currently enrolled in minus 1.  This ensures that the history list
        // does not display courses they are already in (but repeat names
        // can still be in the list if students takes the course multiple time).
        for(String crs : coursesEnrolled)
        {
            int courseCounter = 0;
            for(String course : courseHistory)
            {
                if(crs.equals(course))
                {
                    courseCounter++;
                    if(courseCounter > 1)
                    {
                    %>
                        <a href="course.jsp?course=<%=crs%>"><%=crs%><br></a>
                    <%    
                    } 
                }     
            }   
        }
        %>
        <hr></hr>                
        <br><a href="enrollment.jsp">Main Enrollment Page</a>
        <br><a href="cart.jsp">Cart Contents Page</a>
    </body>
</html>
