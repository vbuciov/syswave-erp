<%-- 
    Document   : application
    Created on : 3/03/2019, 12:00:16 AM
    Author     : victor
--%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello From Master Page Template!</h1>
        <tiles:insertAttribute name="content" />
    </body>
</html>
