<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template_login_register>
    <jsp:useBean id="message" scope="request" class="java.lang.String"/>
    <div class="textBlockSpacer">
        Please fill out the form below to register your account.
    </div>
    <div class="textBlock">
        Your password must at least be 8 characters long, containing one uppercase
        and lowercase letter, one digit and one special character.
    </div>
    <c:if test="${not empty message}">
        <div class="errorMessage">
            ${message}
        </div>
    </c:if>
    <form class="flexbox" action="register" method="post">
        <label class="coloured" for="username">Username</label>
        <input class="coloured" id="username" name="username" type="text" required>
        <label class="coloured" for="password">Password</label>
        <input class="coloured" id="password" name="password" type="password" required>
        <label class="coloured" for="password2">Confirm password</label>
        <input class="coloured" id="password2" name="password2" type="password" required>
        <input class="button coloured" type="submit" name="action" value="Register">
    </form>
    <a class="button coloured" href="./">Log in instead</a>
</t:template_login_register>
