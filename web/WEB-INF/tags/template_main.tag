<%@tag description="Template for a generic page" pageEncoding="UTF-8" %>

<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>

    </title>
    <link rel="stylesheet" href="stylesheets/style.css" type="text/css">
</head>
<body>
    <div class="navbar">
        <a class="titel_applicatie" title="">ORF Predict</a>
        <ul class="navigatie">
            <li><a href="predict">Predict new ORF</a></li>
            <li><a href="resulthistory">My Result History</a></li>
            <li><a href="account">My Account</a></li>
        </ul>
    </div>
    <div class="content">
        <jsp:doBody/>
    </div>
</body>
</html>
