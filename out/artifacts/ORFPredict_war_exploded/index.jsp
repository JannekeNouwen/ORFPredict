<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>ORFPredict</title>

</head>
<body>
  <%@include file="/WEB-INF/includes/header.jsp"%>
  <h1>Login</h1>
  <form action="login" method="post">
    Name:<input type="text" name="username"><br>
    Password:<input type="password" name="password"><br>
    <input type="submit" value="login"><input type="submit" value="register">
  </form>

  ${message}
</body>

</html>
