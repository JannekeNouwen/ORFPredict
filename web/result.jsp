<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:template_main>
    <h1 class="pageTitle">Results</h1>
    <script>
        let seq = "${result.seq}";
        let seqLength = seq.length;

        <c:forEach var="row" items="${formattedORFs}" varStatus="loopCounter">
            var rows = ${loopCounter.count}
        </c:forEach>
    </script>

    <h2>ORF browser</h2>
    <p>Click on an ORF to get more information.</p>
    <div class="browser" id="div_browser">
        <span id="raphael_browser"></span>
    <script>
        // New blank browser
        var paper = Raphael("raphael_browser",
            (seqLength * 20 + 10).toString(), rows * 20 + 100);

        // Loop over the original sequence and draw it on the browser
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

        /**
         * Show info of clicked ORF
         * @param orfId - id of the orf
         * @param start - start position
         * @param stop - stop position
         * @param seq - ORF sequence
         * @param reading_frame - reading frame of the orf
         */
        function showORFInfo(orfId, start, stop, seq, reading_frame) {
            document.getElementById("orf_info").style.visibility = "visible";
            document.getElementById("nuc_seq").innerHTML = seq;
            document.getElementById("seq_len").innerHTML =
                seq.length.toString();
            document.getElementById("seq_start").innerHTML =
                start.toString();
            document.getElementById("seq_stop").innerHTML =
                stop.toString();
            document.getElementById("reading_frame").innerHTML =
                reading_frame.toString();
            document.getElementById("new_blast").setAttribute('href',
                '/blast?orf_id=' + orfId)
            document.getElementById("old_blasts").setAttribute('href',
                '/blastresulthistory?orf_id=' + orfId)

        }

        // Loop over the rows to draw the ORFs
        <c:forEach var="row" items="${formattedORFs}" varStatus="loopCounter">
        // Loop over the ORFs in a row
        <c:forEach var="orf" items="${row}">
        seq = "${orf.seq}";
        var orfLength = seq.length;
        var y = 25*${loopCounter.count} + 20;

        // Loop over the sequence of an ORF to draw it on the browser
        for (i = 0; i < orfLength; i++) {
            nuc = seq.charAt(i).toUpperCase();
            var x = ${orf.start} * 20 + i * 20 + 5;

            if (nuc === "A") {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#00FF00", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop},
                            "${orf.seq}", ${orf.readingFrame})
                    });
            } else if (nuc === "T") {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#FF0000", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}", ${orf.readingFrame})
                    });
            } else if (nuc === "C") {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#FFFF00", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}", ${orf.readingFrame})
                    });
            } else if (nuc === "G") {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#00FFFF", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}", ${orf.readingFrame})
                    });
            } else {
                paper.rect(x, y, 20, 20)
                    .attr({fill: "#FFB6C1", 'stroke-width': 0,
                        cursor: 'pointer'})
                    .click(function() {
                        showORFInfo(
                            ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}", ${orf.readingFrame})
                    });
            }
            paper.text(10 + x, 10 + y, nuc).attr({'font-weight':
                    "bold", cursor: 'pointer'}).click(function() {
                showORFInfo(
                    ${orf.id}, ${orf.start}, ${orf.stop}, "${orf.seq}", ${orf.readingFrame})
            });

        }

        </c:forEach>
        </c:forEach>

    </script>
    </div>

    <div class="containerBackground" id="orf_info" style="visibility: hidden">
        <div class="info_header">Nucleotide sequence: </div>
        <div id="nuc_seq" class="info_text"></div>
        <div class="info_header">From: </div>
        <div id="seq_start" class="info_text"></div>
        <div class="info_header">To: </div>
        <div id="seq_stop" class="info_text"></div>
        <div class="info_header">Length: </div>
        <div id="seq_len" class="info_text"></div>
        <div class="info_header">Reading frame: </div>
        <div id="reading_frame" class="info_text"></div>
        <a id="new_blast" class="button">BLAST with this ORF</a>
        <a id="old_blasts" class="button">View old BLAST queries</a>
    </div>
</t:template_main>

