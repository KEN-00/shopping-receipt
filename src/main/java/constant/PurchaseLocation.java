package constant;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.*;

@Getter
public enum PurchaseLocation {
    CA(0.0975, Collections.singletonList(ProductCategory.FOOD)),
    NY(0.08875, Arrays.asList(ProductCategory.FOOD, ProductCategory.CLOTHING));

    private BigDecimal salesTaxRate;
    private List<ProductCategory> taxExemptedProductCategories;

    private PurchaseLocation(double salesTaxRate, List<ProductCategory> taxExemptedProductCategories) {
        this.salesTaxRate = BigDecimal.valueOf(salesTaxRate);
        this.taxExemptedProductCategories = taxExemptedProductCategories;
    }

    public Map<String, ProductCategory> getTaxExemptedProductMap() {

        Map<String, ProductCategory> productMap = new HashMap<>();
        this.taxExemptedProductCategories.forEach(
            productCategory -> productCategory.getRegisteredItems().forEach(registeredItem -> {
                productMap.put(registeredItem, productCategory);
            })
        );
        return productMap;
    }
}
