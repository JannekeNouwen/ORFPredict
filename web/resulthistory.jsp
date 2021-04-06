<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template_main>
    <h1 class="pageTitle">Result history</h1>
    <div class="widthLimiter">
        On this page you can view your old queries.
    </div>
    <table class="resulthistorytable">
        <tr>
            <th class="resulthistorytableheader">Name</th>
            <th class="resulthistorytableheader">Sequence</th>
            <th class="resulthistorytableheader">Header</th>
        </tr>
        <%-- Loop over old results --%>
        <c:forEach var="result" items="${resultSummary}" varStatus="loopCounter">
            <tr class="resulthistorytablerow">
                <td class="resulthistorytabledata"><a
                        href="result?result_id=${result.get(0)}">
                        ${result.get(1)}</a></td>
                <td class="resulthistorytabledata">${result.get(2)}</td>
                <td class="resulthistorytabledata">${result.get(3)}</td>
            </tr>
        </c:forEach>
    </table>
</t:template_main>

