<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_login_register>
    <h1>Register with ORF Predict</h1>
    <form action="register" method="post">
        <label for="username">Name:</label>
        <input id="username" name="username" type="text" required><br>
        <label for="password">Password:</label>
        <input id="password" name="password" type="password" required><br>
        <label for="password2">Confirm password:</label>
        <input id="password2" name="password2" type="password" required><br>
        <input type="submit" name="action" value="Register">
    </form>
    ${message}
</t:template_login_register>


