<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:template_main>
    <h1>Blast your ORF</h1>
    <form action="blast" method="post">
        Program:<br>
        <input type="radio" id="blastn" name="program" value="blastn"
               required onclick="activateNucleotideDb()">
        <label for="blastn">BLASTn</label><br>
        <input type="radio" id="blastx" name="program" value="blastx"
               onclick="activateProteinDb()">
        <label for="blastx">BLASTx</label><br>
        <input type="radio" id="tblastx" name="program" value="tblastx"
               onclick="activateNucleotideDb()">
        <label for="tblastx">tBLASTx</label><br>
        <br>

        <script>
            function activateNucleotideDb() {
                document.getElementById("nt").disabled = false;
                document.getElementById("refseq_rna").disabled = false;
                document.getElementById("pdbnt").disabled = false;

                document.getElementById("nr").disabled = true;
                document.getElementById("nr").checked = false;
                document.getElementById("refseq_protein").disabled = true;
                document.getElementById("refseq_protein").checked = false;
                document.getElementById("swissprot").disabled = true;
                document.getElementById("swissprot").checked = false;
                document.getElementById("pdb").disabled = true;
                document.getElementById("pdb").checked = false;
            }

            function activateProteinDb() {
                document.getElementById("nt").checked = false;
                document.getElementById("nt").disabled = true;
                document.getElementById("refseq_rna").checked = false;
                document.getElementById("refseq_rna").disabled = true;
                document.getElementById("pdbnt").checked = false;
                document.getElementById("pdbnt").disabled = true;

                document.getElementById("nr").disabled = false;
                document.getElementById("refseq_protein").disabled = false;
                document.getElementById("swissprot").disabled = false;
                document.getElementById("pdb").disabled = false;
            }

        </script>

        Database:<br>
        <input type="radio" id="nt" name="database" value="nt" required>
        <label for="nt">Nucleotide collection (nt)</label><br>
        <input type="radio" id="refseq_rna" name="database" value="refseq_rna">
        <label for="refseq_rna">NCBI Transcript Reference Sequences
            (RefSeq RNA)</label><br>
        <input type="radio" id="pdbnt" name="database" value="pdbnt">
        <label for="pdbnt">Protein Data Bank nucleotide sequences
            (PDB)</label><br>
        <br>
        <input type="radio" id="nr" name="database" value="nr">
        <label for="nr">Non-reduntant protein sequences (nr)</label><br>
        <input type="radio" id="refseq_protein" name="database" value="refseq_protein">
        <label for="refseq_protein">Reference proteins (RefSeq
            Protein)</label><br>
        <input type="radio" id="swissprot" name="database" value="swissprot">
        <label for="swissprot">UniProtKB / Swiss-Prot</label><br>
        <input type="radio" id="pdb" name="database" value="pdb">
        <label for="pdb">Protein Data Bank proteins (pdb)</label><br>
        <br>

        <script>
            document.getElementById("nt").disabled = true;
            document.getElementById("refseq_rna").disabled = true;
            document.getElementById("pdbnt").disabled = true;

            document.getElementById("nr").disabled = true;
            document.getElementById("refseq_protein").disabled = true;
            document.getElementById("swissprot").disabled = true;
            document.getElementById("pdb").disabled = true;
        </script>

        <label for="evalue">Expect threshold:</label><br>
        <input type="text" id="evalue" name="evalue"
               pattern="[0-9]*\.?[0-9]+[eE]?-?[0-9]*" value="0.05" required><br>

        <label for="word_size">Word size:</label><br>
        <input type="number" id="word_size" name="word_size"
               pattern="[0-9]*" value="6" required><br>

        <label for="alignment_number">Number of alignments to return:
            (max. 100)</label><br>
        <input type="number" id="alignment_number" name="alignment_number"
               pattern="[0-9]*" max="100" required value="10"><br>

        <input type="hidden" name="orf_id" value="${orf_id}" />
        <input class="button" type="submit" name="action">
    </form>

</t:template_main>