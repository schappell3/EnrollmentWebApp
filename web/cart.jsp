<%-- 
    Document   : cart
    Created on : Aug 4, 2013, 2:40:14 PM
    Author     : Scott Chappell
--%>

<%@page import="edu.vt.cs5244.BasicEnrollmentManager"%>
<%@page import="java.util.Set"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <h2>Cart Contents Page</h2>
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
        <form action="servlet/edu.vt.cs5244.CartCheckout">
            <table>
                <thead>
                    <tr>
                        <td>Remove</td>
                        <td>Courses</td>
                    </tr>
                </thead>
                <tfoot>
                    <tr>
                        <td><input type="submit" name="action" value="Clear Cart"></td>
                        <td><input type="submit" name="action" value="Remove"></td>
                        <td><input type="submit" name="action" value="Checkout"></td>
                    </tr>
                </tfoot>
                <tbody>
                    
                    <%   
                    // Fetches the course in the cart from the session object
                    Set<String> courseInCart = (Set)session.getAttribute("cart");
                    if(courseInCart != null)
                    {
                        // Displays all the courses in the cart
                        for(String crs : courseInCart)  
                        { %>
                            <tr>
                                <td><input type="checkbox" name="remove_<%=crs%>"></td>
                                <td><a href="course.jsp?course=<%=crs%>"><%=crs%></a></td>
                            </tr>
                        <%
                        }     
                    }
                    // Displays various error messages depending on what the error code is
                    if(request.getParameter("message") != null)
                    {
                       // Cart is empty message
                       if(request.getParameter("message").equals("empty"))
                       {
                       %>
                            <div class="errorMessage">The cart is empty.</div>
                       <%
                       }
                       // Course is full message
                       else if(request.getParameter("message").equals("full"))
                       {
                            Set<String> fullCourses = (Set)session.getAttribute("fullCourses");
                            if(fullCourses != null)
                            {
                                if(!fullCourses.isEmpty())
                                {
                                %>
                                    <div class="errorMessage">Checkout could not be completed.</div>
                                    <%
                                    for(String crs : fullCourses)
                                    {
                                    %>
                                        <div class="errorMessage"><%=crs%> has no available seats.</div>
                                    <%           
                                    }
                                } 
                            }
                       }
                       // Student is already enrolled in the course message
                       else if(request.getParameter("message").equals("enrolled"))
                       {
                            Set<String> fullCourses = (Set)session.getAttribute("alreadyEnrolled");
                            if(fullCourses != null)
                            {
                                if(!fullCourses.isEmpty())
                                {
                                %>
                                    <div class="errorMessage">Checkout could not be completed.</div>
                                    <%
                                    for(String crs : fullCourses)
                                    {
                                    %>
                                        <div class="errorMessage">You are already enrolled in <%=crs%>.</div>
                                    <%           
                                    }
                                } 
                            }
                       }
                    }
                    %>
                </tbody>        
            </table>                
        </form>
        <hr></hr>
        <br><a href="enrollment.jsp">Main Enrollment Page</a>  
        <br><a href="student.jsp">Student Detail Page</a>
    </body>
</html>
