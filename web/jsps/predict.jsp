<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
    <form>
        <textarea id="input" rows="10", cols="50"
                  placeholder="Enter accession number(s) or FASTA sequence(s)"></textarea>
        <br>
        <label>Or, upload a file</label>
        <input id="file" type="file">
    </form>
</t:template_main>
