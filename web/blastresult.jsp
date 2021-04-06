<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template_main>
    <h1>Results of your BLAST search</h1>
<%--    <table>--%>
<%--        <tr>--%>
<%--            <th>seq</th>--%>
<%--            <th>alignedSeq</th>--%>
<%--            <th>eValue</th>--%>
<%--            <th>accCode</th>--%>
<%--            <th>identityPercent</th>--%>
<%--            <th>title</th>--%>
<%--        </tr>--%>
<%--        &lt;%&ndash; Table of blast results &ndash;%&gt;--%>
<%--        <c:forEach var="result" items="${blastResults}" varStatus="loopCounter">--%>
<%--            <tr>--%>
<%--                <td>${result.seq}</td>--%>
<%--                <td>${result.alignedSeq}</td>--%>
<%--                <td>${result.eValue}</td>--%>
<%--                <td>${result.accCode}</td>--%>
<%--                <td>${result.identityPercent}</td>--%>
<%--                <td>${result.title}</td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--    </table>--%>

    <c:forEach var="result" items="${blastResults}" varStatus="loopCounter">
        <div class="blastresult">
            <div style="font-weight: bold">${result.title}</div><br>
            <div class="blastvisuals">
                <span>
                    <span>${result.qseq}</span><br>
                    <span>${result.alignedSeq}</span><br>
                    <span>${result.seq}</span><br>
                </span>
            </div><br>
            E-value: ${result.eValue}<br>
            Accession code: ${result.accCode}<br>
            Identity percent: ${result.identityPercent}%<br>
        </div>
        <br>
    </c:forEach>

</t:template_main>
