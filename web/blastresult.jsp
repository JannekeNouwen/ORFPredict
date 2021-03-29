<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>Results of your BLAST search</h1>
    <table>
        <tr>
            <th>seq</th>
            <th>alignedSeq</th>
            <th>eValue</th>
            <th>accCode</th>
            <th>identityPercent</th>
            <th>title</th>
        </tr>
        <c:forEach var="result" items="${blastResults}" varStatus="loopCounter">
            <tr>
                <td>${result.seq}</td>
                <td>${result.alignedSeq}</td>
                <td>${result.eValue}</td>
                <td>${result.accCode}</td>
                <td>${result.identityPercent}</td>
                <td>${result.title}</td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
