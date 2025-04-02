package musacode;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.*;

public class SwingApplication {
    // Instance variables for the text fields
    // private JTextField dateField;
    // private JTextField numberOfStudentsField;
    private JTextField invoiceNumberField;
    // private JTextField priceField;
    private JTextField invoiceToField;
    private JTextField invoiceAddressField;
    private String today;

    // Panel that will hold all line-item rows
    private JPanel lineItemsPanel;
    private JFrame frame;
    // Keep track of each row’s three fields so you can retrieve their values later
    private final java.util.List<LineItemRow> lineItemRows = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SwingApplication()::createAndShowGUI);
    }

    private void createAndShowGUI() {
        frame = new JFrame("Invoice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Main panel with BoxLayout for vertical alignment
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margin around the panel

        // Initialize text fields
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        today = dateFormat.format(new Date());

        // dateField = new JTextField(today, 20);
        // numberOfStudentsField = new JTextField("1", 20);
        invoiceNumberField = new JTextField(String.valueOf(new Random().nextInt(9999) + 1), 20);
        // priceField = new JTextField("20", 20);
        invoiceToField = new JTextField("Anchorage Senior Activity Center", 20);
        invoiceAddressField = new JTextField("1300 E 19th Ave, Anchorage AK 99501", 20);

        // Add text fields to the panel
        // panel.add(createLabeledPanel("Date (MM/dd/yyyy):", dateField));
        // panel.add(createLabeledPanel("Number of Students:", numberOfStudentsField));
        panel.add(createLabeledPanel("Invoice #:", invoiceNumberField));
        // panel.add(createLabeledPanel("Price: $", priceField));
        panel.add(createLabeledPanel("Invoice to:", invoiceToField));
        panel.add(createLabeledPanel("Invoice Address:", invoiceAddressField));

        // Line Items section
        JPanel lineItemsContainer = new JPanel();
        lineItemsContainer.setLayout(new BoxLayout(lineItemsContainer, BoxLayout.Y_AXIS));
        lineItemsContainer.setBorder(BorderFactory.createTitledBorder("Line Items"));

        // Panel holding line items in a vertical column
        lineItemsPanel = new JPanel();
        lineItemsPanel.setLayout(new BoxLayout(lineItemsPanel, BoxLayout.Y_AXIS));

        // Add an initial line item row by default (optional)
        addLineItemRow("1", today, "20");

        // Put the line items panel inside the container
        lineItemsContainer.add(lineItemsPanel);

        // Button to add more line items
        JButton addLineItemButton = new JButton("+");
        addLineItemButton.addActionListener(e -> addLineItemRow("1", today, "20"));
        lineItemsContainer.add(addLineItemButton);

        // Add line items container to the main panel
        panel.add(lineItemsContainer);

        // Submit button at the bottom
        JButton submitButton = new JButton("Zumba");
        submitButton.addActionListener(e -> onClick());
        panel.add(submitButton);

        // Add panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void addLineItemRow(String quantity, String description, String unitPrice) {
        // Create a new row with a flow layout
        JPanel itemRowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Create text fields for this row
        JTextField quantityField = new JTextField(quantity, 10);
        JTextField descriptionField = new JTextField(description, 10);
        JTextField unitPriceField = new JTextField(unitPrice, 10);

        // Add them to the row panel
        itemRowPanel.add(new JLabel("Date (MM/dd/yyyy):"));
        itemRowPanel.add(descriptionField);
        itemRowPanel.add(new JLabel("Number of Students:"));
        itemRowPanel.add(quantityField);
        itemRowPanel.add(new JLabel("Price:"));
        itemRowPanel.add(unitPriceField);

        // Keep a reference to these fields for later retrieval
        LineItemRow row = new LineItemRow(quantityField, descriptionField, unitPriceField);
        lineItemRows.add(row);

        // Add this row panel to the line items panel and refresh
        lineItemsPanel.add(itemRowPanel);
        lineItemsPanel.revalidate();
        lineItemsPanel.repaint();

        frame.pack();
    }

    private JPanel createLabeledPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        return panel;
    }

    private void onClick() {
        // Gather the standard fields into a map for your existing logic
        Map<String, String> res = new HashMap<>();
        res.put("date", today);
        // res.put("zumba_date", dateField.getText());
        res.put("invoice_to", invoiceToField.getText());
        res.put("invoice_to_address", invoiceAddressField.getText());
        res.put("num", invoiceNumberField.getText());
        // res.put("price", priceField.getText());
        // res.put("students", numberOfStudentsField.getText());

        // Gather line items
        List<LineItem> allLineItems = new ArrayList<>();
        double total = 0.0;
        for (LineItemRow row : lineItemRows) {
            LineItem lineItem = row.toLineItem();
            allLineItems.add(lineItem);
            total += lineItem.getPriceValue();
        }
        res.put("price", String.format("%.2f", total));

        // At this point, you can do whatever you like with `allLineItems`.
        // The rest of your doc-generation logic remains the same:
        String basePath = "docs\\";
        // String basePath = "C:\\Users\\guita\\Documents\\";
        String docPath = basePath + "Template.docx";
        String intermediatePath = basePath + "Output.docx";
        String pdfPath = basePath + "Nami Zumba Invoice.pdf";

        WordTableEditor wordTableEditor = new WordTableEditor(docPath);
        wordTableEditor.AddLineItems(allLineItems);
        wordTableEditor.saveTo(intermediatePath);
        wordTableEditor.close();

        ReplaceTextInDocument textReplacer = new ReplaceTextInDocument(intermediatePath, pdfPath);
        System.out.println("Main fields: " + res);
        System.out.println("Line items: " + allLineItems);
        textReplacer.replace(res);


        OpenDirectory openDir = new OpenDirectory(basePath);
        openDir.open();
    }

    // Simple helper class to hold references to the text fields for each line item row
    private static class LineItemRow {
        JTextField quantityField;
        JTextField descriptionField;
        JTextField unitPriceField;

        LineItemRow(JTextField quantityField, JTextField descriptionField, JTextField unitPriceField) {
            this.quantityField = quantityField;
            this.descriptionField = descriptionField;
            this.unitPriceField = unitPriceField;
        }

        public LineItem toLineItem() {
            return new LineItem(quantityField.getText(), descriptionField.getText(), unitPriceField.getText());
        }
    }
}
