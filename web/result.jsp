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
            <th></th>
            <th></th>
        </tr>
        <c:forEach var="orf" items="${ORFArray}" varStatus="loopCounter">
            <tr>
                <td>${orf.start}</td>
                <td>${orf.stop}</td>
                <td>${orf.seq}</td>
                <td><a href="blastresulthistory?orf_id=${orf.id}">View
                    BLAST searches</a></td>
                <td><a href="blast?orf_id=${orf.id}">New BLAST
                search</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
