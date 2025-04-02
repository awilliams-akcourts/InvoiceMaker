package musacode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class InvoiceUI extends Application {
    private VBox layout;
    private DatePicker datePicker;
    private TextField numberOfStudentsField;
    private TextField invoiceNumberField;
    private TextField priceField;
    private TextField invoiceToField;
    private TextField invoiceAddressField;// Container to hold all line item rows
    private VBox lineItemsContainer;
    private List<LineItemRow> lineItemRows = new ArrayList<LineItemRow>();

    @Override
    public void start(Stage stage) {
        layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        // Date Picker
        datePicker = new DatePicker(LocalDate.now());
        layout.getChildren().add(new Label("Date when do zumba"));
        layout.getChildren().add(datePicker);

        // Number of Students
        numberOfStudentsField = new TextField("1");
        layout.getChildren().add(new Label("Number of students"));
        layout.getChildren().add(numberOfStudentsField);

        // Invoice Number
        Random rand = new Random();
        invoiceNumberField = new TextField(String.valueOf(rand.nextInt(10000) + 1));
        layout.getChildren().add(new Label("Invoice #"));
        layout.getChildren().add(invoiceNumberField);

        // Price
        priceField = new TextField("20");
        layout.getChildren().add(new Label("Price: $"));
        layout.getChildren().add(priceField);

        // Invoice to
        invoiceToField = new TextField("Anchorage Senior Activity Center");
        layout.getChildren().add(new Label("Invoice to"));
        layout.getChildren().add(invoiceToField);

        // Invoice Address
        invoiceAddressField = new TextField("1300 E 19th Ave, Anchorage AK 99501");
        layout.getChildren().add(new Label("Invoice address"));
        layout.getChildren().add(invoiceAddressField);

        // Line Items Section
        lineItemsContainer = new VBox(10);
        layout.getChildren().add(new Label("Line Items (students, zumba date, price per student)"));
        layout.getChildren().add(lineItemsContainer);

        // Add an initial line item by default
        addLineItemRow();

        // Submission Button
        Button submitButton = new Button("Zumba");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<LineItem> lineItems = new ArrayList<LineItem>();
                for (LineItemRow row : lineItemRows) {
                    lineItems.add(row.toLineItem());
                }
                InputData input = new InputData(
                    datePicker.getValue().toString(),
                    Integer.parseInt(numberOfStudentsField.getText()),
                    Integer.parseInt(priceField.getText()),
                    Integer.parseInt(invoiceNumberField.getText()),
                    invoiceToField.getText(),
                    invoiceAddressField.getText(),
                    lineItems
                );
                handleSubmitButtonAction(input);
            }
        });
        layout.getChildren().add(submitButton);

        // Scene and Stage
        Scene scene = new Scene(layout, 500, 550);
        stage.setTitle("Zumba Invoice");
        stage.setScene(scene);
        stage.show();
    }

    private void addLineItemRow() {
        LineItemRow row = new LineItemRow();
        lineItemRows.add(row);
        lineItemsContainer.getChildren().add(row.createHBox());  
    }

    public void handleSubmitButtonAction(InputData input) {
        // String basePath = "C:\\Users\\guita\\Code\\java\\zumba-zumba\\docs\\";
        String basePath = "docs\\";
        String docPath = basePath + "Template.docx";
        String pdfPath = basePath + "Nami Zumba Invoice.pdf";

        ReplaceTextInDocument textReplacer = new ReplaceTextInDocument(docPath, pdfPath);
        Map<String, String> replaceMe = input.getMap();
        System.out.println(replaceMe.toString());
        textReplacer.replace(replaceMe);

        OpenDirectory openDir = new OpenDirectory(basePath);
        openDir.open();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private static class LineItemRow {
        TextField quantityField;
        TextField descriptionField;
        TextField unitPriceField;

        public HBox createHBox() {
            HBox hBox = new HBox(10);

            quantityField = new TextField();
            quantityField.setPromptText("Qty");

            descriptionField = new TextField();
            descriptionField.setPromptText("Description");

            unitPriceField = new TextField();
            unitPriceField.setPromptText("Unit Price");

            hBox.getChildren().addAll(
                new Label("Quantity:"),
                quantityField,
                new Label("Description:"),
                descriptionField,
                new Label("Unit Price:"),
                unitPriceField
            );
            return hBox;
        }

        public LineItem toLineItem() {
            return new LineItem(quantityField.getText(), descriptionField.getText(), unitPriceField.getText());
        }
    }
} 

