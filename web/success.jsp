<%-- 
    Document   : success
    Created on : Aug 2, 2013, 3:19:09 PM
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
        <h2>Registration Success Page</h2>
        <hr></hr>
        <h4>Registration Successful!</h4>
        <%
        // Displays the student's ID number
        if(request.getParameter("id") != null)
        {
        %>
           Your student ID is: <%= request.getParameter("id")%><br>
        <%   
        }
        %>
        <a href="login.jsp">Login</a>
    </body>
</html>
