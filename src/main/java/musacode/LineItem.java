package musacode;

import java.util.Arrays;
import java.util.List;

public class LineItem {
    private String quantity;
    private String description;
    private String unitPrice;

    public LineItem(String quantity, String description, String unitPrice) {
        this.quantity = quantity;
        this.description = description;
        this.unitPrice = unitPrice;
    }

    public String getLineTotal(String quantity, String unitPrice) {
        try {
            int quant = Integer.parseInt(quantity);
            double perStudent = Double.parseDouble(unitPrice);
            double ans = quant * perStudent;
            return String.format("%.2f", ans);
        } catch (NumberFormatException e) {
            return unitPrice;
        }
    }

    public String getPrice(String unitPrice) {
        try {
            double perStudent = Double.parseDouble(unitPrice);
            return String.format("%.2f", perStudent);
        } catch (NumberFormatException e) {
            return unitPrice;
        }
    }

    public double getPriceValue() {
        try {
            double perStudent = Double.parseDouble(unitPrice);
            return perStudent;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return 20.0;
    }

    public List<String> toArray() {
        String price = getPrice(unitPrice);
        return Arrays.asList(new String[] {"Zumba - (" + quantity + ") student(s)", description, "$" + price, "$" + price});
    }
}