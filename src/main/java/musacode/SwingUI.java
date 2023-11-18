package musacode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SwingUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SwingUI::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Zumba Invoice");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Main panel with BoxLayout for vertical alignment
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margin around the panel

        // Adding components
        panel.add(createLabeledField("Date (dd/mm/yyyy)", ""));
        panel.add(createLabeledField("Number of Students", "1"));
        panel.add(createLabeledField("Invoice #", String.valueOf(new Random().nextInt(9999) + 1)));
        panel.add(createLabeledField("Price: $", "20"));
        panel.add(createLabeledField("Invoice to", "Anchorage Senior Activity Center"));
        panel.add(createLabeledField("Invoice Address", "1300 E 19th Ave, Anchorage AK 99501"));

        JButton submitButton = new JButton("Zumba");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center align the button
        submitButton.addActionListener(e -> System.out.println("Submit button clicked"));
        panel.add(submitButton);

        // Add the main panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Display the window
        frame.pack();
        frame.setLocationRelativeTo(null); // Center the window
        frame.setVisible(true);
    }

    private static JPanel createLabeledField(String label, String defaultValue) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Padding between fields

        JLabel jLabel = new JLabel(label);
        JTextField jTextField = new JTextField(defaultValue, 20);
        panel.add(jLabel);
        panel.add(jTextField);

        return panel;
    }
}

