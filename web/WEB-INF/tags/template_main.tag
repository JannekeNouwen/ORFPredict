<%@tag description="Template for a generic page" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>

    </title>
    <link rel="stylesheet" href="stylesheets/style_base.css" type="text/css">
    <link rel="stylesheet" href="stylesheets/style_navbar.css" type="text/css">
</head>
<body>
    <div class="navbar" id="navigation_bar">
        <a <%--href="javascript:void(0);" onclick="hamburger()" --%>class="hamburger" id="hamburger_button">
            <div></div>
            <div></div>
            <div></div>
        </a>
        <a class="titleWebsite" id="title_website" href="./">ORF Predict</a>
        <div class="navbarBreaker" id="navbar_breaker"></div>
        <ul class="navigatie">
            <li><a id="link_home" href="./">Home</a></li>
            <li><a id="link_predict" href="predict">Predict new ORF</a></li>
            <li><a id="link_history" href="resulthistory">My result history</a></li>
            <li><a id="link_account" href="account">My account</a></li>
        </ul>
    </div>
    <div class="content" id="content">
        <jsp:doBody/>
    </div>
</body>
</html>
