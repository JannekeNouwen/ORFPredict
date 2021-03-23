<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
    <h1>Blast your ORF</h1>
    Database:<br>
    <input type="radio" id="nt" name="database" value="nt" required>
    <label for="nt">Nucleotide collection</label><br>
    <input type="radio" id="refseq_rna" name="database" value="refseq_rna">
    <label for="refseq_rna">NCBI Transcript Reference Sequences</label><br>
    <input type="radio" id="pdbnt" name="database" value="pdbnt">
    <label for="pdbnt">PDB nucleotide database</label><br>
    <br>

    Program:<br>
    <input type="radio" id="blastn" name="program" value="blastn" required>
    <label for="blastn">BLASTn</label><br>
    <input type="radio" id="blastx" name="program" value="blastx">
    <label for="blastx">BLASTx</label><br>
    <input type="radio" id="tblastx" name="program" value="tblastx">
    <label for="tblastx">tBLASTx</label><br>
    <br>

    <label for="evalue">Expect threshold:</label><br>
    <input type="text" id="evalue" name="evalue"
           pattern="[0-9]*\.?[0-9]+[eE]?-?[0-9]*" value="0.05" required><br>

    <label for="word_size">Word size:</label><br>
    <input type="number" id="word_size" name="word_size"
           pattern="[0-9]*" value="6" required><br>

    <label for="alignment_number">Number of alignments to return:
        (nax. 100)</label><br>
    <input type="number" id="alignment_number" name="alignment_number"
           pattern="[0-9]*" max="100" required value="10"><br>

    <input class="button" type="submit" name="action">

</t:template_main>