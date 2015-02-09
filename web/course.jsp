<%--
    Document   : course
    Created on : Jul 25, 2013, 4:17:34 PM
    Author     : Scott Chappell
--%>

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
        <h2>Course Detail Page</h2>
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
        <%
        // Displays an error message if the course is already in the cart
        if(request.getParameter("message") != null)   
        {
            if(request.getParameter("message").equals("inCart"))
            {%>
                <div class="errorMessage">That course is already in your cart</div>
            <%
            }
        }
        
        // Fetch the shared BEM
        BasicEnrollmentManager sharedBEM = (BasicEnrollmentManager)application.getAttribute("theBEM");
        
        // Displays all the course details
        String courseName = request.getParameter("course");
        if(courseName != null)
        {
            String courseDescription = sharedBEM.getCourseDescription(courseName);
            int courseCapacity = sharedBEM.getCourseCapacity(courseName);
            int courseAvailableSeats = sharedBEM.getCourseAvailableSeats(courseName);

            %>
            <%=courseName%><br>Course Capacity: <%=courseCapacity%>
            <br>Available Seats: <%=courseAvailableSeats%>
            <br>Course Description: <%=courseDescription%>
            <%
            // Checks if the course is full
            if(courseAvailableSeats > 0)
            {
            %>
                <form action="servlet/edu.vt.cs5244.CourseCart">
                    <tfoot>
                        <input type="submit" value="Add to Cart">
                    </tfoot>
                    <tbody>
                        <input type="hidden" name="crs" value="<%=courseName%>">
                    </tbody>
                </form>
            <%
            } 
        }
        %>
        <hr></hr>
        <br><a href="enrollment.jsp">Main Enrollment Page</a>
        <br><a href="cart.jsp">Cart Contents Page</a>
        <br><a href="student.jsp">Student Detail Page</a>
    </body>
</html>
