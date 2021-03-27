<!DOCTYPE html>
<%@page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template_login_register>
    <jsp:useBean id="message" scope="request" class="java.lang.String"/>

    <div class="textBlock">
        Welcome to ORF Predict! Please use your credentials to log into your account.
    </div>
    <c:if test="${not empty message}">
        <div class="errorMessage">
            ${message}
        </div>
    </c:if>
    <form class="flexbox" action="login" method="post">
        <label for="username">Username</label>
        <input id="username" name="username" type="text" autocapitalize="off" required>
        <label for="password">Password</label>
        <input id="password" name="password" type="password" required>
        <input class="button" type="submit" name="action" value="Login">
    </form>
    <div class="flexbox register">
        <div>
            Or if you don't have an account, register one for free.
        </div>
        <a class="button" href="register">Register</a>
    </div>
</t:template_login_register>
