<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template_main>
    <h1>Results</h1>

<%--    <table>--%>
<%--        <tr>--%>
<%--            <th>Start</th>--%>
<%--            <th>Stop</th>--%>
<%--            <th>Seq</th>--%>
<%--            <th></th>--%>
<%--            <th></th>--%>
<%--        </tr>--%>
<%--        <c:forEach var="orf" items="${ORFArray}" varStatus="loopCounter">--%>
<%--            <tr>--%>
<%--                <td>${orf.start}</td>--%>
<%--                <td>${orf.stop}</td>--%>
<%--                <td>${orf.seq}</td>--%>
<%--                <td><a href="blastresulthistory?orf_id=${orf.id}">View--%>
<%--                    BLAST searches</a></td>--%>
<%--                <td><a href="blast?orf_id=${orf.id}">New BLAST--%>
<%--                search</a></td>--%>
<%--            </tr>--%>
<%--        </c:forEach>--%>
<%--    </table>--%>
    <h2>Overview</h2>

    <canvas id="overview" width="200" height="100">
        Sorry, your browser does not support the Canvas element. You're missing out.
    </canvas>
    <script>
        var c = document.getElementById("overview");
        var ctx = c.getContext("2d");
        ctx.fillStyle = "#00FF00";
        ctx.fillRect(5, 5, 190, 20);


        let seq = "${result.seq}";
        let seqLength = seq.length;
        ctx.fillStyle = "#FF0000";

        <c:forEach var="row" items="${formattedORFs}" varStatus="loopCounter">
            <c:forEach var="orf" items="${row}">

                var x = ${orf.start}*190/seqLength + 5;
                var y = 15*${loopCounter.count} + 25;
                var len = ${orf.stop}*190/seqLength - x;

                ctx.fillRect(x, y, len, 10);

                </c:forEach>
            var rows = ${loopCounter.count}
        </c:forEach>

        c.style.height = rows*20 + 30;
    </script>

    <h2>ORF browser</h2>
    <div class="browser" id="div_browser">
<%--        <span class="box">--%>
            <span id="raphael_browser"></span>
<%--        <canvas class="orf_browser" id="orf_browser" >--%>
<%--            Sorry, your browser does not support the Canvas element. You're missing out.--%>
<%--        </canvas>--%>
<%--        </span>--%>
    <script>
        var paper = Raphael("raphael_browser",
            (seqLength * 20 + 10).toString(), rows * 20 + 60);

        for (var i = 0; i < seqLength; i++) {
            var nuc = seq.charAt(i).toUpperCase();
            if (nuc === "A") {
                paper.rect(5 + i * 20, 5, 20, 20)
                    .attr({fill: "#00FF00", 'stroke-width': 0})
            } else if (nuc === "T") {
                paper.rect(5 + i * 20, 5, 20, 20)
                    .attr({fill: "#FF0000", 'stroke-width': 0})
            } else if (nuc === "C") {
                paper.rect(5 + i * 20, 5, 20, 20)
                    .attr({fill: "#FFFF00", 'stroke-width': 0})
            } else if (nuc === "G") {
                paper.rect(5 + i * 20, 5, 20, 20)
                    .attr({fill: "#00FFFF", 'stroke-width': 0})
            } else {
                paper.rect(5 + i * 20, 5, 20, 20)
                    .attr({fill: "#FFB6C1", 'stroke-width': 0})
            }
            paper.text(15 + i * 20, 15, nuc).attr({'font-weight': "bold",
                cursor: 'default'})
        }

        function showORFInfo(orfId, start, stop, seq) {
            console.log("hellooo")
            document.getElementById("orf_info").style.visibility = "visible";
            document.getElementById("nuc_seq").innerHTML = seq;
            document.getElementById("seq_len").innerHTML =
                seq.length.toString();
            document.getElementById("prot_seq").innerHTML = seq;
            // TODO nog doen
            document.getElementById("new_blast").setAttribute('href',
                '/blast.jsp?orf_id=' + orfId)
            document.getElementById("old_blasts").setAttribute('href',
                '/blastresulthistory.jsp?orf_id=' + orfId)

        }

        <c:forEach var="row" items="${formattedORFs}" varStatus="loopCounter">
        <c:forEach var="orf" items="${row}">
        seq = "${orf.seq}";
        var orfLength = seq.length;
        var y = 25*${loopCounter.count} + 20;

        // TODO van deze sequenties aminozuur sequenties maken
        for (i = 0; i < orfLength; i++) {
            nuc = seq.charAt(i).toUpperCase();
            var x = ${orf.start} * 20 + i * 20 + 5;

            if (nuc === "A") {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#00FF00", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}")
                    });
            } else if (nuc === "T") {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#FF0000", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}")
                    });
            } else if (nuc === "C") {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#FFFF00", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}")
                    });
            } else if (nuc === "G") {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#00FFFF", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}")
                    });
            } else {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#FFB6C1", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}")
                    });
            }
            paper.text(10 + x, 10 + y, nuc).attr({'font-weight':
                    "bold", cursor: 'pointer'})

        }

        </c:forEach>
        </c:forEach>

    </script>
    </div>

    <div class="containerBackground" id="orf_info" style="visibility: hidden">
        <div>Nucleotide sequence: </div>
        <div id="nuc_seq"></div>
        <div>Length: </div>
        <div id="seq_len"></div>
        <div>Protein sequence: </div>
        <div id="prot_seq"></div><br>
            <a id="new_blast" class="button">BLAST with this ORF</a>
            <a id="old_blasts" class="button">View old BLAST queries</a>
    </div>
</t:template_main>

