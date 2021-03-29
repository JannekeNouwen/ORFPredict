<%@tag description="Template for login/register content" pageEncoding="UTF-8" %>

<html lang="en" xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>
        <%--        TODO: titel vanuit servlet doorgeven en hier weergeven.--%>
    </title>
    <link rel="stylesheet" href="stylesheets/style_base.css" type="text/css">
    <link rel="stylesheet" href="stylesheets/style_login_register.css" type="text/css">
    <link rel="stylesheet" href="stylesheets/style_form_elements.css" type="text/css">
</head>
<body>
    <div class="flexPage wallpaper">
        <h1>ORF Predict</h1>
        <div class="containerBackground">
            <jsp:doBody/>
        </div>
    </div>
</body>
</html>
