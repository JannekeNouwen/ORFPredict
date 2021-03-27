<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_login_register>
    <div class="textBlockSpacer">
        Please fill out the form below to register your account.
    </div>
    <div class="textBlock">
        Your password must at least be 8 characters long, containing one uppercase
        and lowercase letter, one digit and one special character.
    </div>
    <div class="errorMessage">
        ${message}
    </div>
    <form class="flexbox" action="register" method="post">
<%--        TODO: met JavaScript ingegeven wachtwoord valideren of twee keer hetzelfde
                  is ingegeven.--%>
        <label for="username">Username</label>
        <input id="username" name="username" type="text" required>
        <label for="password">Password</label>
        <input id="password" name="password" type="password" required>
        <label for="password2">Confirm password</label>
        <input id="password2" name="password2" type="password" required>
        <input class="button" type="submit" name="action" value="Register">
    </form>
    <a class="button" href="./">Log in instead</a>
</t:template_login_register>
