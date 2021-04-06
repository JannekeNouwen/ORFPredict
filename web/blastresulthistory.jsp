<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template_main>
    <h1>your BLAST search queries</h1>
    <table class="resulthistorytable">
        <tr>
            <th></th>
            <th class="resulthistorytableheader">Database</th>
            <th class="resulthistorytableheader">Maximum target sequences</th>
            <th class="resulthistorytableheader">Expect Threshold</th>
            <th class="resulthistorytableheader">Word Size</th>
        </tr>
        <%-- Table of old blast queries --%>
        <c:forEach var="search" items="${blastResultSummary}"
                   varStatus="loopCounter">
            <tr class="resulthistorytablerow">
                <td class="resulthistorytabledata"><a href="blastresult?blastsearch_id=${search.get(0)}">
                    View results</a></td>
                <td class="resulthistorytabledata">${search.get(1)}</td>
                <td class="resulthistorytabledata">${search.get(4)}</td>
                <td class="resulthistorytabledata">${search.get(5)}</td>
                <td class="resulthistorytabledata">${search.get(6)}</td>

            </tr>
        </c:forEach>
    </table>
</t:template_main>