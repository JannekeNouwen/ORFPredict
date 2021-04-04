<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:template_main>
    <h1>Results</h1>
    <div class="containerBackground">
        <span class="errorMessage">
            Please wait while we calculate your BLAST results.
            Reloading in ${timeout} seconds...
        </span>
    </div>
    <script>
        function sleep(ms) {
            return new Promise(resolve => setTimeout(resolve, ms));
        }

        async function wait() {
            await sleep(${timeout})
            window.location.replace("/blast?timeout=${timeout}");
        }

        wait()
    </script>

</t:template_main>

