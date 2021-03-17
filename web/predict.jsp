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
        <input type="submit" name="action"><input type="reset"><br />
        <input type="radio" id="atgonly" name="startcodon" value="atgonly">
        <label for="atgonly">"ATG" only</label><br />
        <input type="radio" id="alternative" name="startcodon" value="alternative">
        <label for="alternative">"ATG" and alternative initiation codons</label><br />
        <p>Select minimum size of ORF
            <select name="minSize">
                <option value="30">30</option>
                <option value="75" selected>75</option>
                <option value="90">90</option>
                <option value="150">150</option>
                <option value="300">300</option>
                <option value="600">600</option>
            </select></p>
<%--        TODO: stijl voor elementen toevoegen.--%>
    </form>
    ${message}
</t:template_main>
