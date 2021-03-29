<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>BLAST search history</title>
</head>
<body>
    <h1>your BLAST search queries</h1>
    <table>
        <tr>
            <th></th>
            <th>database</th>
            <th>organism</th>
            <th>exclude</th>
            <th>max_target_sequences</th>
            <th>expect_threshold</th>
            <th>word_size</th>
            <th></th>
        </tr>
        <c:forEach var="search" items="${blastResultSummary}"
                   varStatus="loopCounter">
            <tr>
                <td>${loopCounter.count}</td>
                <td>${search.get(1)}</td>
                <td>${search.get(2)}</td>
                <td>${search.get(3)}</td>
                <td>${search.get(4)}</td>
                <td>${search.get(5)}</td>
                <td>${search.get(6)}</td>
                <td><a href="blastresult?blastsearch_id=${search.get(0)}">
                        View results</a></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
