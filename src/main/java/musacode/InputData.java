package musacode;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class InputData {
    private String date;
    private int students;
    private int price;
    private int invoiceNum;
    private String invoiceTo;
    private String invoiceToAddress;
    private String today;

    
    public InputData(String date, int students, int price, int invoiceNum, String invoiceTo, String invoiceToAddress) {
        this.date = date;
        this.students = students;
        this.price = price;
        this.invoiceNum = invoiceNum;
        this.invoiceTo = invoiceTo;
        this.invoiceToAddress = invoiceToAddress;
        this.today = LocalDate.now().toString();
    }

    public Map<String, String> getMap() {
        Map<String, String> res = new HashMap<>();
        res.put("date", today);
        res.put("zumba_date", date);
        res.put("invoice_to", invoiceTo);
        res.put("invoice_to_address", invoiceToAddress);
        res.put("num", String.valueOf(invoiceNum));
        res.put("price", String.valueOf(price));
        res.put("students", String.valueOf(students));
        return res;
    }

    public String getDate() {
        return date;
    }

    public int getStudents() {
        return students;
    }

    public int getPrice() {
        return price;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public String getInvoiceTo() {
        return invoiceTo;
    }

    public String getInvoiceToAddress() {
        return invoiceToAddress;
    }
}
