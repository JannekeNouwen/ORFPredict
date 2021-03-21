<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
    <form class="flexbox" action="predict" method="post" enctype="multipart/form-data">
        <div id="name">
            <label for="query_name">Query name:</label>
            <input type="text" id="query_name" name="query_name" required>
        </div><br>
        <div id="sequence_input">
            <textarea name="textInput" rows="10" cols="50"
                      placeholder="Enter ONE accession number or FASTA sequence"></textarea>
            <div>
                <label>Or, upload a file</label>
                <input name="fileInput" type="file" accept=".fasta,.fa">
            </div>
        </div>
        <div class="flexbox" id="codon_usage">
            <div>
                <input type="radio" id="atgonly" name="startcodon" value="atgonly" checked>
                <label for="atgonly">"ATG" only</label>
            </div>
            <div>
                <input type="radio" id="alternative" name="startcodon" value="alternative">
                <label for="alternative">"ATG" and alternative initiation codons</label>
            </div>
        </div>
        <div id="orf_length">
            <label for="length">Minimal ORF length</label>
            <select id="length" name="minSize">
                <option value="30">30</option>
                <option value="75" selected>75</option>
                <option value="90">90</option>
                <option value="150">150</option>
                <option value="300">300</option>
                <option value="600">600</option>
            </select>
        </div>
<%--        TODO: stijl voor elementen toevoegen.--%>
        <div>
            <input class="button" type="submit" name="action">
            <input class="button" type="reset">
        </div>
    </form>
    ${message}
</t:template_main>
