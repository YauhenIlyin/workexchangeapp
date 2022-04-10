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

<%System.out.println("work jsp registration");%> <!--/delete test-->
<form name="registrationForm" method="POST" action="${pageContext.request.contextPath}/controller">
    <input type="hidden" name="command" value="sign_up"/>
    <br/>
    <br/>
    <p>login (6-16 symbols, 1+ 'a-z',1+ 'A-Z',0+ '0-9', 0+ '_')</p>
    <input type="text" name="login" value=""/>
    <p>${pageContext.request.session.sign_up_login_message}</p>
    <br/>
    <p>password (8-32 symbols, 1+ '!"#$%&'()*',1+ 'a-z',1+ 'A-Z',0+ '0-9', 0+ '_')</p>
    <input type="text" name="password_first" value=""/>
    <p>${pageContext.request.session.sign_up_password_message}</p>
    <br/>
    <input type="text" name="password_second" value=""/>
    <br/>
    <p>first name (1-14 symbols)</p>
    <input type="text" name="first_name" value=""/>
    <p>${pageContext.request.session.sign_up_first_last_name_message}</p>
    <br/>
    <p>last name (1-14 symbols)</p>
    <input type="text" name="last_name" value=""/>
    <br/>
    <p>e-mail (60max symbols, hello.world@mail.ru)</p>
    <input type="text" name="e_mail" value=""/>
    <p>${pageContext.request.session.sign_up_email_message}</p>
    <br/>
    <p>mobile number (30max symbols, +375***, 8029***)</p>
    <input type="text" name="mobile_number" value=""/>
    <p>${pageContext.request.session.sign_up_mobile_message}</p>
    <br/>
    <input type="submit" name="register"/>
</form>

</body>
</html>
