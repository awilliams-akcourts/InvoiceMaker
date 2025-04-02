package musacode;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.xwpf.usermodel.*;

public class WordTableEditor implements AutoCloseable {
    private XWPFDocument document;
    private FileInputStream inputStream;

    public WordTableEditor(String filePath) {
        try {
            // inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            // if (inputStream == null) {
            //     throw new FileNotFoundException(filePath.toString() + " not found");
            // } 
            inputStream = new FileInputStream(filePath);
            document = new XWPFDocument(inputStream);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public XWPFTable findIthTable(int i) {
        XWPFTable xwpfTable;
        if (!document.getTables().isEmpty()) {
            xwpfTable = document.getTables().get(i);
        } else {
            xwpfTable = document.createTable();
        }
        return xwpfTable;
    }

    public void addTableRow(XWPFTable table, List<String> inputStrings) {
        XWPFTableRow row = table.createRow();

        for (int i = 0; i < inputStrings.size(); i++) {
            XWPFTableCell cell;
            cell = row.getCell(i);
            if (cell == null) {
                cell = row.addNewTableCell();
            }
            set11ptBoldText(cell, inputStrings.get(i));
        }
    }

    private void set11ptBoldText(XWPFTableCell cell, String text) {
        XWPFParagraph para = cell.addParagraph();
        XWPFRun run = para.createRun();
        run.setBold(true);
        run.setFontSize(11);
        run.setText(text);
    }

    public void saveTo(String filePath) {
        try (FileOutputStream out = new FileOutputStream(filePath)) {
            document.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            document.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddLineItems(List<LineItem> lineItems) {
        XWPFTable table = findIthTable(2);

        for (LineItem lineItem : lineItems) {
            addTableRow(table, lineItem.toArray());
        }
    }

    public static void demo() {
        WordTableEditor wordTableEditor = new WordTableEditor("Template.docx");
        XWPFTable table = wordTableEditor.findIthTable(2);

        String[] inputStrings = new String[] {"Hello", "Here", "is", "SOME", "txt"};

        wordTableEditor.addTableRow(table, Arrays.asList(inputStrings));
        wordTableEditor.saveTo("output.docx");
        wordTableEditor.close();
    }
}
