<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result history</title>
</head>
<body>
    <%@include file="/WEB-INF/includes/header.jsp"%>
    <h1>Result history</h1>
<%--    <%--%>
<%--        ArrayList<ArrayList<String>> resultSummary--%>
<%--                = (ArrayList<ArrayList<String>>)--%>
<%--                request.getAttribute("resultSummary");--%>
<%--        for (ArrayList<String> result : resultSummary) {--%>
<%--            out.println(result.get(0));--%>
<%--        }--%>
<%--    %>--%>

    <table>
        <tr>
            <th>name</th>
            <th>seq</th>
            <th>acc_code</th>
            <th>header</th>
        </tr>
        <c:forEach var="result" items="${resultSummary}" varStatus="loopCounter">
            <tr>
                <td><a href="result?result_id=${result.get(0)}">${result.get(1)}</a></td>
                <td>${result.get(2)}</td>
                <td>${result.get(3)}</td>
                <td>${result.get(4)}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
