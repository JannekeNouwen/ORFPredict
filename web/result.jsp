<%@ page import="orf_processing.ORF" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results</title>
</head>
<body>
    <%@include file="/WEB-INF/includes/header.jsp"%>
    <h1>Results</h1>

    <table>
        <tr>
            <th>Start</th>
            <th>Stop</th>
            <th>Seq</th>
        </tr>
        <c:forEach var="orf" items="${ORFArray}" varStatus="loopCounter">
            <tr>
                <td>${orf.start}</td>
                <td>${orf.stop}</td>
                <td>${orf.seq}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
