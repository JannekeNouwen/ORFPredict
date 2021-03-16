<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
  <h1>Login</h1>
  <form action="login" method="post">
    Name:     <input type="text" name="username"><br>
    Password: <input type="password" name="password"><br>
    <input type="submit" name="action" value="login"><input type="submit" name="action" value="register">
  </form>
  ${message}
</t:template_main>
