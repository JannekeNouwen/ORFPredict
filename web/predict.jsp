<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
    <h1>[title_page]</h1>
    <div>
        [description on how to use the tool]
    </div>
    <form class="flexbox" action="predict" method="post" enctype="multipart/form-data">
        <div id="name" class="flexbox">
            <label for="query_name">Query name</label>
            <input type="text" id="query_name" name="query_name" placeholder="e.g. 'My super awesome ORF-search'" required>
        </div>

        <div id="sequence_input" class="flexbox">
            <label for="text_input">Enter an accession number or a FASTA-sequence for a nucleotide sequence</label>
            <textarea id="text_input" name="textInput" rows="10" cols="50"
                      placeholder="e.g. 'XP_020076380.1'"></textarea>
        </div>

        <div class="flexbox">
            <label for="file_input">Or, upload a file</label>
            <input name="fileInput" id="file_input" type="file" accept=".fasta,.fa">
        </div>

        <div class="flexbox" id="start_codon_use">
            <label>ORF start codon use</label>
            <div>
                <input type="radio" id="atgonly" name="startcodon" value="atgonly" checked>
                <label class="normal" for="atgonly">'ATG' only</label>
            </div>
            <div>
                <input type="radio" id="alternative" name="startcodon" value="alternative">
                <label class="normal" for="alternative">'ATG' and alternative initiation codons</label>
            </div>
        </div>

        <div class="flexbox" id="stop_codon_use">
            <label>ORF stop codon use</label>
            <div>
                <input type="radio" id="normalstop" name="stopcodon" value="normalstop" checked>
                <label class="normal" for="normalstop">'TAG', 'TGA' and 'TAA' only</label>
            </div>
            <div>
                <input type="radio" id="mitostop" name="stopcodon" value="mitostop">
                <label class="normal" for="mitostop">'TAG', 'TGA', 'TAA' and mitochondrial termination codons</label>
            </div>
        </div>

        <div class="flexbox" id="orf_length">
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
