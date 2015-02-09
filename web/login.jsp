<%-- 
    Document   : login
    Created on : Jul 29, 2013, 10:05:37 PM
    Author     : Scott Chappell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <h2>Student Login Page</h2>
        <hr></hr>
        <%
        // Displays a need to log in message
        if(request.getParameter("message") != null)   
        {
            if(request.getParameter("message").equals("needLogin"))
            {%>
                <h3>You need to log in</h3>
        <%
            }
        }
        %>
        <form action="servlet/edu.vt.cs5244.StudentLogin">
            <table>
                <tfoot>
                    <tr>
                        <td></td>
                        <th><input type="submit" value="Login"></th>
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <td>Student ID</td>
                        <td><input type="text" name="username"></td> 
                    </tr>
                    <tr>
                        <td>Password</td> 
                        <td><input type="text" name="password"></td>   
                    </tr>
                </tbody>
            </table>
                <%  
                // Displays an error message
                if(session.getAttribute("messageLogin") != null)
                {
                    if(!session.getAttribute("messageLogin").equals("Valid"))
                    { %>
                        <div class="errorMessage"><%= session.getAttribute("messageLogin") %></div>
                    <%
                    }
                }
                %>
        </form>
        <br><a href="registration.jsp">Register</a>
    </body>
</html>
