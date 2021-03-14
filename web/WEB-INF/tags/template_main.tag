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
    <div class="navbar">
        <a class="titel_applicatie" href="./">ORF Predict</a>
        <%--Hamburgerknop--%>
        <a <%--href="javascript:void(0);" onclick="hamburger()" --%>class="hamburger" id="hamburger">
            <div></div>
            <div></div>
            <div></div>
        </a>
        <div class="navbarBreaker"></div>
        <ul class="navigatie">
            <li><a href="./">Home</a></li>
            <li><a href="predict">Predict new ORF</a></li>
            <li><a href="resulthistory">My result history</a></li>
            <li><a href="account">My account</a></li>
        </ul>
    </div>
    <div class="content">
        <jsp:doBody/>
    </div>
</body>
</html>
