<%@tag description="Template for a generic page" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ORFPredict
<%--        TODO: titel vanuit servlet doorgeven en hier weergeven.--%>
    </title>
    <link rel="stylesheet" href="stylesheets/style_base.css" type="text/css">
    <link rel="stylesheet" href="stylesheets/style_navbar.css" type="text/css">
    <link rel="stylesheet" href="stylesheets/style_form_elements.css" type="text/css">
    <link rel="stylesheet" href="stylesheets/style_result.css"
          type="text/css">
    <script
            src="https://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script> <%--Nodig voor SVG visualisatie--%>
    <script rel="script" src="scripts/functions_hamburger.js" type="text/javascript"></script>
</head>
<body>
    <div class="navbar" id="navigation_bar">
        <a class="hamburgerButton" id="hamburger_button" href="javascript:void(0);" onclick="hamburger()">
            <div></div>
            <div></div>
            <div></div>
        </a>
        <a class="titleWebsite" id="title_website" href="predict">ORF
            Predict</a>
<%--        TODO: element 'account' verwijderen en een overlay van maken.--%>
        <div class="navbarBreaker" id="navbar_breaker"></div>
        <ul class="navigationDisplay" id="navigation_display">
            <li><a id="link_predict" href="predict">Predict new ORF</a></li>
            <li><a id="link_history" href="resulthistory">My result history</a></li>
            <li><a id="link_account" href="account">My account</a></li>
        </ul>
    </div>
    <div class="containerContent" id="content">
        <jsp:doBody/>
    </div>
</body>
</html>
