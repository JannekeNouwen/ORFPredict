<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_login_register>
    <div class="textBlockSpacer">
        Please fill out the form below to register your account.
    </div>
    <div class="textBlock">
        Your password must al least be
        8 characters long, containing one uppercase and lowercase letter, one digit and one
        special character.
    </div>
    <form class="flexbox" action="register" method="post">
        <label for="username">Username</label>
        <input id="username" name="username" type="text" required>
        <label for="password">Password</label>
        <input id="password" name="password" type="password"
               pattern="^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$"
               required>
        <label for="password2">Confirm password</label>
        <input id="password2" name="password2" type="password" required>
        <input class="button" type="submit" name="action" value="Register">
    </form>
    <a class="button" href="./">Log in instead</a>
    ${message}
</t:template_login_register>
