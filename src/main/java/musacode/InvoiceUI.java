package musacode;

import java.time.LocalDate;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class InvoiceUI extends Application {
    private VBox layout;
    private DatePicker datePicker;
    private TextField numberOfStudentsField;
    private TextField invoiceNumberField;
    private TextField priceField;
    private TextField invoiceToField;
    private TextField invoiceAddressField;

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

        // Submission Button
        Button submitButton = new Button("Zumba");
        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                InputData input = new InputData(
                    datePicker.getValue().toString(),
                    Integer.parseInt(numberOfStudentsField.getText()),
                    Integer.parseInt(priceField.getText()),
                    Integer.parseInt(invoiceNumberField.getText()),
                    invoiceToField.getText(),
                    invoiceAddressField.getText()
                );
                handleSubmitButtonAction(input);
            }
        });
        layout.getChildren().add(submitButton);

        // Scene and Stage
        Scene scene = new Scene(layout, 400, 460);
        stage.setTitle("Zumba Invoice");
        stage.setScene(scene);
        stage.show();
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
}
