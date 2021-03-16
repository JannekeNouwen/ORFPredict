<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
  <h1>Login</h1>
  <form action="login" method="post">
    <label for="username">Name:</label>
    <input id="username" name="username" type="text" required><br>
    <label for="password">Password:</label>
    <input id="password" name="password" type="password" required><br>
    <input type="submit" name="action" value="Login">
<%--    <input type="submit" name="action" value="register">--%>
    <a href="register">Register</a>
  </form>
  ${message}
</t:template_main>
