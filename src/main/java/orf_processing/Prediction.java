package orf_processing;

import java.io.File;

public class Prediction {
    private String seq;
    private final String input;
    private String header;
    private File fastaFromUser;

    public Prediction(String input) {
        this.input = input;
    }

    //            boolean fasta = textInput.startsWith(">");
//            Pattern accPattern = Pattern.compile("([a-zA-Z]_?[0-9]{5}|[a-zA-Z]{2}_?([0-9]{6}|[0-9]{8}))(\\.[0-9])?");
//            Matcher accMatcher = accPattern.matcher(textInput);
//            if (fasta) {
//                String[] fastaInput = inputSeq.split("\\r?\\n");
//                String header = fastaInput[0];
//                String[] slice = Arrays.copyOfRange(fastaInput, 1, fastaInput.length);
//                String seq = String.join("", slice);
//            } else if (accMatcher.find()) {
//                String accessionCode = textInput;
//            } else {
//                String message = "Input is not valid";
//                request.setAttribute("message", message);
//                RequestDispatcher dispatcher =
//                        this.getServletContext().getRequestDispatcher(
//                                "/predict.jsp");
//                dispatcher.forward(request, response);
//            }

    private String getSeqByAcc() {
        return "";
    }

    private String getSeqByFile() {
        return "";
    }

    public ORFResult predictSeq() {
        ORFResult result = new ORFResult("", "", 0);

        // predict ORFs

        return result;
    }
}
