<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
    <form>
        <textarea id="input" rows="10" cols="50"
                  placeholder="Enter accession number(s) or FASTA sequence(s)"></textarea>
        <br>
        <label>Or, upload a file</label>
        <input id="file" type="file">
<%--        TODO: stijl voor elementen toevoegen.--%>
<%--        TODO: invoervelden voor parameters toevoegen.--%>
    </form>
</t:template_main>
