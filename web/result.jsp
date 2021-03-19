<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Results</title>
    <link rel="stylesheet" href="stylesheets/style_result_visuals.css"
          type="text/css">

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
    <h2>Overview</h2>
    <p id="demo"></p>

    <canvas id="overview" width="200" height="100"></canvas>
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
        </c:forEach>
    </script>

    <h2>ORF browser</h2>
    <div class="browser" id="div_browser">
        <canvas class="orf_browser" id="orf_browser" ></canvas>
    </div>
    <script>
        var c = document.getElementById("orf_browser");
        document.getElementById("orf_browser").setAttribute("width",
            (seqLength * 20 + 10).toString());
        // document.getElementById("browser_box").setAttribute("max-width",
        //     (seqLength * 20 + 10).toString());

        var ctx = c.getContext("2d");

        ctx.font = "15px Courier New";
        ctx.textAlign = "center";

        for (var i = 0; i < seqLength; i++) {
            var nuc = seq.charAt(i).toUpperCase();
            if (nuc === "A") {
                ctx.fillStyle = "#00FF00";
            } else if (nuc === "T") {
                ctx.fillStyle = "#FF0000";
            } else if (nuc === "C") {
                ctx.fillStyle = "#FFFF00";
            } else if (nuc === "G") {
                ctx.fillStyle = "#00FFFF";
            } else {
                ctx.fillStyle = "#FFB6C1";
            }
            ctx.fillRect(5 + i * 20, 5, 20, 20);
            ctx.fillStyle = "#000000";
            ctx.fillText(nuc, 15 + i * 20, 20);
        }

        ctx.fillStyle = "#FF0000";

        <c:forEach var="row" items="${formattedORFs}" varStatus="loopCounter">
            <c:forEach var="orf" items="${row}">
                seq = "${orf.seq}";
                var orfLength = seq.length;
                var y = 25*${loopCounter.count} + 20;


        // TODO van deze sequenties aminozuur sequenties maken
                for (i = 0; i < orfLength; i++) {
                    nuc = seq.charAt(i).toUpperCase();
                    if (nuc === "A") {
                        ctx.fillStyle = "#00FF00";
                    } else if (nuc === "T") {
                        ctx.fillStyle = "#FF0000";
                    } else if (nuc === "C") {
                        ctx.fillStyle = "#FFFF00";
                    } else if (nuc === "G") {
                        ctx.fillStyle = "#00FFFF";
                    } else {
                        ctx.fillStyle = "#FFB6C1";
                    }

                    var x = ${orf.start} * 20 + i * 20 + 5;

                    ctx.fillRect(x, y, 20, 20);
                    ctx.fillStyle = "#000000";
                    ctx.fillText(nuc, x + 10, y + 15);
                }

            </c:forEach>
        </c:forEach>
    </script>

</body>
</html>

