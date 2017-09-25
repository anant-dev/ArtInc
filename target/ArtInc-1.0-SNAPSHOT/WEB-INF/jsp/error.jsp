<%-- 
    Document   : Error
    Created on : Sep 17, 2017, 6:42:18 PM
    Author     : anants
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Art Inc</title>
    </head>
    <%
                                 
                                String message = (String) request.getAttribute("message");
                                
                            %>
    <body>
        <h1><%= message %></h1>
    </body>
</html>
