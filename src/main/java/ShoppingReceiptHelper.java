import com.fasterxml.jackson.databind.ObjectMapper;
import dto.PrintReceiptRequest;
import dto.PrintReceiptResponse;
import dto.PurchaseItem;
import exception.PrintReceiptRequestProcessException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;

public class ShoppingReceiptHelper {

    public static void main(String[] args) throws IOException {
        ShoppingReceiptHelper shoppingReceiptHelper = new ShoppingReceiptHelper();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println(SEPARATOR);
        while(true) {
            System.out.println("Enter file path of purchase details json file (enter -1 to terminate): ");
            String filePath = reader.readLine();
            if(filePath.equals("-1"))
                break;

            PrintReceiptResponse response = null;
            try{
                response = shoppingReceiptHelper.getPrintReceiptResponse(filePath);
            } catch (PrintReceiptRequestProcessException e) {
                System.out.println("Failed to read pruchase details from json file.");
                System.out.println("Terminate this job (Y/N)?");
                String option = reader.readLine();
                if(option.equalsIgnoreCase("Y"))
                    break;
                else {
                    System.out.println(SEPARATOR);
                    continue;
                }
            }
            shoppingReceiptHelper.printReceipt(response);
        }
    }

    private static final String SEPARATOR = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";

    public PrintReceiptRequest readPrintReceiptRequestFromJson(String filePath) throws PrintReceiptRequestProcessException {
        ObjectMapper objectMapper = new ObjectMapper();
        File jsonFile = new File(filePath);
        PrintReceiptRequest request;
        try{
            request = objectMapper.readValue(jsonFile, PrintReceiptRequest.class);
        } catch (IOException e) {
            throw new PrintReceiptRequestProcessException();
        }
        return request;
    }

    public PrintReceiptResponse getPrintReceiptResponse(String filePath) throws PrintReceiptRequestProcessException {
        PrintReceiptRequest request = this.readPrintReceiptRequestFromJson(filePath);
        return new PrintReceiptResponse(request.getLocation(), request.getItems());
    }

    public void printReceipt(PrintReceiptResponse printReceiptResponse) {
        List<PurchaseItem> items = printReceiptResponse.getPurchaseItems();
        String template = getRowTemplate(items);

        BigDecimal subTotal = printReceiptResponse.getSubtotalAmt();
        BigDecimal tax = printReceiptResponse.getTaxAmt();
        BigDecimal total = printReceiptResponse.getTotalAmt();

        System.out.println(SEPARATOR);
        System.out.println(String.format(template, "item", "price", "qty"));
        System.out.println();

        items.forEach(item -> {
            String itemInfo = String.format(template, item.getProductName(), "$"+item.getPrice(), item.getQuantity());
            System.out.println(itemInfo);
        });

        System.out.println(String.format(template, "subtotal:", "", "$"+subTotal));
        System.out.println(String.format(template, "tax:", "", "$"+tax));
        System.out.println(String.format(template, "total:", "", "$"+total));
        System.out.println(SEPARATOR);
    }

    private String getRowTemplate(List<PurchaseItem> items) {
        int maxProductNameLength = items.stream().mapToInt(item -> item.getProductName().length()).max().orElse(-1);
        int itemColumnLength = maxProductNameLength < 10 ? 10 : maxProductNameLength + 5;
        String template = "%-" + itemColumnLength + "s %-10s %-10s";
        return template;
    }

}
