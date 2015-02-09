<%-- 
    Document   : registration
    Created on : Jul 29, 2013, 10:56:02 PM
    Author     : Scott Chappell
--%>
<%@page import="edu.vt.cs5244.UserData"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" type="text/css" href="style.css">
    </head>
    <body>
        <h2>Student Registration Page</h2>
        <hr></hr>
        <%
        // Creates a new UserData object if one does not exist
        // Ensures that the fields can be pre-populated with blank fields
        UserData data = (UserData)session.getAttribute("userData");
        if (data == null) 
        {
            data = new UserData();
        }
        %>
        <form action="servlet/edu.vt.cs5244.StudentRegistration">
            <table>
                <tfoot>
                    <tr>
                        <th><input type="submit" value="Submit"></th>  
                    </tr>
                </tfoot>
                <tbody>
                    <tr>
                        <td>Student Name<br><input type="text" name="std_name" value="<%=data.getStdName()%>">
                            <%
                            Map<String, String> message = (HashMap)session.getAttribute("messageMap");    
                            if(session.getAttribute("messageMap") != null)
                            {
                                // Displays error message
                                if(message.containsKey("messageName"))
                                { %>
                                    <br><div class="errorMessage"><%= message.get("messageName") %></div></td> 
                                <%
                                }
                            }
                            %>    
                    </tr>
                    <tr>
                        <td>Password<br><input type="text" name="std_password" value="<%=data.getStdPassword()%>">
                            <% 
                            if(session.getAttribute("messageMap") != null)
                            {
                                // Displays error message
                                if(message.containsKey("messagePassword"))
                                { %>
                                <br><div class="errorMessage"><%= message.get("messagePassword") %></div></td> 
                                <%
                                }
                            }
                            %>  
                    </tr>
                    <tr>
                        <td>Re-type Password<br><input type="text" name="std_repassword" value="<%=data.getStdRePassword()%>">
                            <% 
                            if(session.getAttribute("messageMap") != null)
                            {
                                // Displays error message
                                if(message.containsKey("messageRePassword"))
                                { %>
                                    <br><div class="errorMessage"><%= message.get("messageRePassword") %></div></td> 
                                <%
                                }
                            }
                            %>  
                    </tr>
                    <tr>
                        <td>City<br><input type="text" name="std_city" value="<%=data.getStdCity()%>">
                            <% 
                            if(session.getAttribute("messageMap") != null)
                            {
                                // Displays error message
                                if(message.containsKey("messageCity"))
                                { %>
                                    <br><div class="errorMessage"><%= message.get("messageCity") %></div></td> 
                                <%
                                }
                            }
                            %>  
                    </tr>
                    <tr>
                        <td>State<br><input type="text" name="std_state" value="<%=data.getStdState()%>">
                            <% 
                            if(session.getAttribute("messageMap") != null)
                            {
                                // Displays error message
                                if(message.containsKey("messageState"))
                                { %>
                                    <br><div class="errorMessage"><%= message.get("messageState") %></div></td> 
                                <%
                                }
                            }
                            %>  
                    </tr>
                    <tr>
                        <td>Zip Code<br><input type="text" name="std_zip1" value="<%=data.getStdZip1()%>" style="width: 71px;">
                        <input type="text" name="std_zip2" value="<%=data.getStdZip2()%>" style="width: 55px;">
                            <% 
                            if(session.getAttribute("messageMap") != null)
                            {
                                // Displays error message
                                if(message.containsKey("messageZip"))
                                { %>
                                    <br><div class="errorMessage"><%= message.get("messageZip") %></div></td> 
                                <%
                                }
                            }
                            %>  
                    </tr>
                    <tr>
                        <td>Phone<br><input type="text" name="std_phone1" value="<%=data.getStdPhone1()%>" style="width: 35px;">
                        <input type="text" name="std_phone2" value="<%=data.getStdPhone2()%>" style="width: 35px;">
                        <input type="text" name="std_phone3" value="<%=data.getStdPhone3()%>" style="width: 46px;">
                            <% 
                            if(session.getAttribute("messageMap") != null)
                            {
                                // Displays error message
                                if(message.containsKey("messagePhone"))
                                { %>
                                    <br><div class="errorMessage"><%= message.get("messagePhone") %></div></td> 
                                <%
                                }
                            }
                            %>  
                    </tr>
                    <tr>
                        <td>
                            Highest level of education completed by a parent.
                            <br><input type="radio" name="education" value="High School"> High School
                            <br><input type="radio" name="education" value="Some College"> Some College
                            <br><input type="radio" name="education" value="College"> College
                            <br><input type="radio" name="education" value="Post College"> Post College
                            <% 
                            if(session.getAttribute("messageMap") != null)
                            {
                                // Displays error message
                                if(message.containsKey("messageSurvey"))
                                { 
                                %>
                                    <br><div class="errorMessage"><%= message.get("messageSurvey") %></div></td> 
                                <%
                                }
                            }
                            %> 
                        </td>
                    </tr>
                    <tr>
                        <td>
                            How did you hear about this college?
                            <br><input type="Checkbox" name="Checkbox1" value="Internet"> Internet
                            <br><input type="Checkbox" name="Checkbox2" value="Television"> Television
                            <br><input type="Checkbox" name="Checkbox3" value="Newspaper"> Newspaper
                            <br><input type="Checkbox" name="Checkbox4" value="Friends/Family"> Friends/Family
                        </td>
                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>
