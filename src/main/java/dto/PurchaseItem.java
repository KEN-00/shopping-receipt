package dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class PurchaseItem {
    private String productName;
    private BigDecimal price;
    private int quantity;
}
