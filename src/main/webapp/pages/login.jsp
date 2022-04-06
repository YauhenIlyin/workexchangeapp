<%--
  Created by IntelliJ IDEA.
  User: Comp236782
  Date: 10.09.2021
  Time: 18:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Login</title>

</head>
<body>
<%System.out.println("work jsp login");%> <!--/delete test-->
<form name="loginForm" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="sign_in"/>
    <br/>
    <p>login
    <p/><input type="text" name="login" value=""/>
    <br/>
    <p>password
    <p/><input type="password" name="password" value=""/>
    <br/>
    <input type="submit" name="Log In" value="Sign in"/>
</form>
<br/>
<br/>
<form action="pages/registration.jsp">
    <input type="submit" name="" value="Register new account">
</form>
<%= application.getServerInfo() %><br>
 <%= application.getMajorVersion() %>.<%= application.getMinorVersion() %>
</body>
</html>
