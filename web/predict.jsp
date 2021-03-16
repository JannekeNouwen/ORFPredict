<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
    <form action="predict" method="post" enctype="multipart/form-data">
        <textarea name="textInput" rows="10" cols="50"
                  placeholder="Enter ONE accession number or FASTA sequence"></textarea>
        <br>
        <label>Or, upload a file</label>
        <input name="fileInput" type="file" accept=".fasta,.fa">
        <input type="submit" name="action">
<%--        TODO: stijl voor elementen toevoegen.--%>
<%--        TODO: invoervelden voor parameters toevoegen.--%>
    </form>
    ${message}
</t:template_main>
