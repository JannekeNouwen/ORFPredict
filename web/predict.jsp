<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<t:template_main>
    <h1 class="pageTitle">Predict new ORF</h1>
    <div class="widthLimiter">
        On this page you can enter your query to search for ORF's.
    </div>
    <c:if test="${not empty message}">
        <div class="errorMessage">
                ${message}
        </div>
    </c:if>
    <form class="flexbox widthLimiter" action="predict" method="post" enctype="multipart/form-data">
        <div id="name" class="flexbox">
            <label class="coloured" for="query_name">Query name</label>
            <input class="coloured" type="text" id="query_name" name="query_name"
                   placeholder="e.g. 'My super awesome ORF-search'"
                   maxlength="30" required>
        </div>

        <div id="sequence_input" class="flexbox">
            <label class="coloured" for="text_input">Enter an accession number or a FASTA-sequence for a nucleotide sequence</label>
            <textarea class="coloured" id="text_input" name="textInput" placeholder="e.g. 'XP_020076380.1'"></textarea>
        </div>

        <div class="flexbox">
            <label class="coloured" for="file_input">Or, upload a file</label>
            <input  class="coloured" name="fileInput" id="file_input" type="file" accept=".fasta,.fa">
        </div>

        <div class="flexbox" id="start_codon_use">
            <label class="coloured">ORF start codon use</label>
            <div class="radioElement">
                <input class="coloured" type="radio" id="atgonly" name="startcodon" value="atgonly" checked>
                <label class="radioLabel" for="atgonly">'ATG' only</label>
            </div>
            <div class="radioElement">
                <input class="coloured" type="radio" id="alternative" name="startcodon" value="alternative">
                <label class="radioLabel" for="alternative">'ATG' and alternative initiation codons</label>
            </div>
        </div>

        <div class="flexbox" id="stop_codon_use">
            <label class="coloured">ORF stop codon use</label>
            <div class="radioElement">
                <input class="coloured" type="radio" id="normalstop" name="stopcodon" value="normalstop" checked>
                <label class="radioLabel" for="normalstop">'TAG', 'TGA' and 'TAA' only</label>
            </div>
            <div class="radioElement">
                <input class="coloured" type="radio" id="mitostop" name="stopcodon" value="mitostop">
                <label class="radioLabel" for="mitostop">'TAG', 'TGA', 'TAA' and mitochondrial termination codons</label>
            </div>
        </div>

        <div class="flexbox" id="orf_length">
            <label class="coloured" for="length">Minimal ORF length</label>
            <select class="coloured" id="length" name="minSize">
                <option value="30">30</option>
                <option value="75" selected>75</option>
                <option value="90">90</option>
                <option value="150">150</option>
                <option value="300">300</option>
                <option value="600">600</option>
            </select>
        </div>

        <div>
            <input class="button coloured" type="submit" name="action">
            <input class="button coloured" type="reset">
        </div>
    </form>

</t:template_main>
