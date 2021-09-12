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
    <title>Registration</title>
</head>
<body>
<%-- ${pageContext.request.getContextPath} --%>
<form name="registrationForm" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="register_account"/>
    <br/>
    <br/>
    <p>login</p>
    <input type="text" name="login" value=""/>
    <br/>
    <p>password</p>
    <input type="text" name="password1" value=""/>
    <br/>
    <input type="text" name="password2" value=""/>
    <br/>
    <p>first name</p>
    <input type="text" name="first_name" value=""/>
    <br/>
    <p>last name</p>
    <input type="text" name="last_name" value=""/>
    <br/>
    <p>e-mail</p>
    <input type="text" name="e_mail" value=""/>
    <br/>
    <p>mobile number</p>
    <input type="text" name="mobile_number" value=""/>
    <br/>
    <input type="submit" name="register"/>
</form>

</body>
</html>
