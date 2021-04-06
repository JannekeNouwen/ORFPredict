# ORFPredict
# <https://orfpredict.live/>
## Authors:
* Yuri Wit
* Max Nollet
* Janneke Nouwen

## Veranderingen t.o.v. het UML:
### blast_handler (geen wijzigingen)
  * BlastResult
    * Extra variabele: qseq: String
    * Extra functie: getQseq(): String 
  * BlastProcessor
### servlets
  * ResultHistoryServlet
    * GEEN functie doPost()
### database_handler
  * UserInfoHandler
    * HashPassword(String): String --> HashPassword(String, String): String (Salt toegevoegd)
  * DataBaseHandler
    * Extra functies: connect(): Connection, getORF(String): ORF
### orf_processing
  * Prediction
    * GEEN functies getSeqByAcc(), getSeqByFile()
    * Constructor Prediction(String, int, String, String, int, String) i.p.v. 3 losse constructors
    * Extra functies: fastaExtract(), typeCheck(), getSeq(), getType()
    * Extra variabelen: input: String, type: String, header: String, minSize: String, startCodon: String, stopCodon: String, userId: int, name: String
  * ORFResult
    * Constructor ORFResult(String, String, int, String, String) niet nodig 
    * getORFs(): ORF --> getORFs(): ArrayList<ORF>
    * getAccCode() en variabele accCode: String niet nodig
  * ORF
    * Extra variabelen: id, readingFrame
    * Implements comparable (nodig voor visualisatie)
    * Extra functies: compareTo(ORF), getReadingFrame(), getId()
  * Nieuwe class: Translating
    * revComp(String): String 
