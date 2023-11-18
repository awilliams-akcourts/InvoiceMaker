package musacode;

import java.util.Map;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;

public class ReplaceTextInDocument {
    private String templatePath;
    private String outputPath;

    public ReplaceTextInDocument(String templatePath, String outputPath) {
        this.templatePath = templatePath;
        this.outputPath = outputPath;
    }

    public String withMustache(String input) {
        return "{{ " + input + " }}";
        // return input;
    }

    public void replace(Map<String, String> replaceMe){
        // String docPath = "C:\\Users\\guita\\Code\\java\\findreplace\\findreplace\\docs\\Nami Zumba Invoice.docx";
        // //Load a Word document
        Document document = new Document(templatePath);
        for (Map.Entry<String, String> entry : replaceMe.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();

            key = withMustache(key);
            // val = withMustache(val);

            // Replace a specific text
            document.replace(key, val, false, true);
            // System.out.println("Key: " + key + ", Value: " + val);
        }

        //Save the result document
        document.saveToFile(outputPath, FileFormat.PDF);
        // document.close();
    }
}
