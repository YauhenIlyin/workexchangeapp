<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<%System.out.println("work jsp index.jsp");%> <!--/delete test-->
    <jsp:forward page="pages/login.jsp"/>

    <%--
    <h1><%= "Hello World!" %>
    </h1>
    hellooooo hello hello hello
    <br/>
    <a href="hello-servlet">Hello Servlet</a>
    --%>
</body>
</html>