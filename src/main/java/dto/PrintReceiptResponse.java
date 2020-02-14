package dto;

import constant.ProductCategory;
import constant.PurchaseLocation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class PrintReceiptResponse {
    private PurchaseLocation location;
    private List<PurchaseItem> purchaseItems;
    private BigDecimal subtotalAmt;
    private BigDecimal taxAmt;
    private BigDecimal totalAmt;

    public PrintReceiptResponse(PurchaseLocation location, List<PurchaseItem> purchaseItems) {
        this.location = location;
        this.purchaseItems = purchaseItems;
        this.subtotalAmt = BigDecimal.ZERO;
        this.taxAmt = BigDecimal.ZERO;
        this.totalAmt = BigDecimal.ZERO;
        this.calculateTotal();
    }

    private void calculateTotal() {
        BigDecimal locationSalesTaxRate = this.location.getSalesTaxRate();
        Map<String, ProductCategory> taxExemptedProductMap = this.location.getTaxExemptedProductMap();

        purchaseItems.forEach(item -> {
            BigDecimal amt = BigDecimal.valueOf(item.getQuantity()).multiply(item.getPrice());

            String productName = item.getProductName();
            ProductCategory productCategory = taxExemptedProductMap.get(productName);

            BigDecimal salesTaxRate = productCategory == null ? locationSalesTaxRate : BigDecimal.ZERO;
            BigDecimal salesTax = amt.multiply(salesTaxRate).setScale(2, RoundingMode.UP);

            this.subtotalAmt = this.subtotalAmt.add(amt);
            this.taxAmt = this.taxAmt.add(salesTax);
        });

        this.totalAmt = this.subtotalAmt.add(this.taxAmt);
    }

}
