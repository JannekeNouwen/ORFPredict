<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template_main>
    <h1>Result history</h1>
    <table id="resulthistorytable">
        <tr>
            <th>Name</th>
            <th>Sequence</th>
            <th>Accession Code</th>
            <th>Header</th>
        </tr>
        <c:forEach var="result" items="${resultSummary}" varStatus="loopCounter">
            <tr class="resulthistorytablerow">
                <td class="resulthistorytabledata"><a
                        href="result?result_id=${result.get(0)}">
                        ${result.get(1)}</a></td>
                <td class="resulthistorytabledata">${result.get(2)}</td>
                <td class="resulthistorytabledata">${result.get(3)}</td>
                <td class="resulthistorytabledata">${result.get(4)}</td>
            </tr>
        </c:forEach>
    </table>
</t:template_main>

