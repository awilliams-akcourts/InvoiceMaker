package musacode;

import javax.swing.*;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SwingApplication {
    // Instance variables for the text fields
    private JTextField dateField;
    private JTextField numberOfStudentsField;
    private JTextField invoiceNumberField;
    private JTextField priceField;
    private JTextField invoiceToField;
    private JTextField invoiceAddressField;
    private String today;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new SwingApplication()::createAndShowGUI);
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Invoice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

        // Main panel with BoxLayout for vertical alignment
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margin around the panel

        // Initialize text fields
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        today = dateFormat.format(new Date());

        dateField = new JTextField(today, 15);
        numberOfStudentsField = new JTextField("1", 15);
        invoiceNumberField = new JTextField(String.valueOf(new Random().nextInt(9999) + 1), 15);
        priceField = new JTextField("20", 15);
        invoiceToField = new JTextField("Anchorage Senior Activity Center", 15);
        invoiceAddressField = new JTextField("1300 E 19th Ave, Anchorage AK 99501", 15);

        // Add text fields to the frame
        panel.add(createLabeledPanel("Date (MM/dd/yyyy):", dateField));
        panel.add(createLabeledPanel("Number of Students:", numberOfStudentsField));
        panel.add(createLabeledPanel("Invoice #:", invoiceNumberField));
        panel.add(createLabeledPanel("Price: $", priceField));
        panel.add(createLabeledPanel("Invoice to:", invoiceToField));
        panel.add(createLabeledPanel("Invoice Address:", invoiceAddressField));

        JButton submitButton = new JButton("Zumba");
        submitButton.addActionListener(e -> onClick());
        panel.add(submitButton);
        frame.add(panel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createLabeledPanel(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        return panel;
    }

    private void onClick() {
        Map<String, String> res = new HashMap<>();
        res.put("date", today);
        res.put("zumba_date", dateField.getText());
        res.put("invoice_to", invoiceToField.getText());
        res.put("invoice_to_address", invoiceAddressField.getText());
        res.put("num", invoiceNumberField.getText());
        res.put("price", priceField.getText());
        res.put("students", numberOfStudentsField.getText());

        String basePath = "docs\\";
        String docPath = basePath + "Template.docx";
        String pdfPath = basePath + "Nami Zumba Invoice.pdf";

        ReplaceTextInDocument textReplacer = new ReplaceTextInDocument(docPath, pdfPath);
        System.out.println(res.toString());
        textReplacer.replace(res);

        OpenDirectory openDir = new OpenDirectory(basePath);
        openDir.open();
    }
}

