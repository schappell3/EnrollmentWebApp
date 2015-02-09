<%-- 
    Document   : enrollment
    Created on : Jul 25, 2013, 4:00:34 PM
    Author     : Scott Chappell
--%>

<%@page import="java.util.HashSet"%>
<%@page import="java.util.Set"%>
<%@page import="edu.vt.cs5244.BasicEnrollmentManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <h2>Main Enrollment Page</h2>
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
            <h4><%= sharedBEM.getStudentName(id)%>
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
        <h4>Course list</h4>
        <hr></hr>
        <%
        // Retrieves all the course names from the shared BEM
        Set<String> allCourses = sharedBEM.getAllCourses();
        
        // Retrieves all the course the student is enrolled in
        Set<String> courseForStudent = sharedBEM.getCoursesForStudent(id);
        
        // Fetches the cart from the session object
        Set<String> courseInCart = (Set)session.getAttribute("cart");
        
        // Makes a new cart HashSet if it does not exist
        if(courseInCart == null)
        {
            courseInCart = new HashSet<String>();
        }
        // Iterates through all the course in the system
        for (String crs : allCourses)  
        { 
        %>
            <a href="course.jsp?course=<%=crs%>"><%=crs%></a>
        <%
            // Displays (Enrolled) (In Cart) messages
            if(courseForStudent.contains(crs) && courseInCart.contains(crs))
            {
            %> (Enrolled) (In Cart)<br>
            <%
            }
            // Displays (Enrolled) messages
            else if(courseForStudent.contains(crs))
            {
                %> (Enrolled)<br>
                <%
            }
            // Displays (In Cart) messages
            else if(courseInCart.contains(crs))
            {
                %> (In Cart)<br>
                <%
            }
            else
            {
                %><br>
                <%
            }       
        }      
        %>
        <hr></hr>
        <br><a href="cart.jsp">Cart Contents Page</a>
        <br><a href="student.jsp">Student Detail Page</a>
    </body>
</html>
