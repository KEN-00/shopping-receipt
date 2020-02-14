package constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum ProductCategory {
    FOOD(Arrays.asList("fish", "potato chips")),
    CLOTHING(Arrays.asList("shirts")),
    OTHERS(null);

    private List<String> registeredItems;

    private ProductCategory(List<String> registeredItems) {
        this.registeredItems = registeredItems == null? Collections.emptyList() : registeredItems;
    }

    public List<String> getRegisteredItems() {
        return registeredItems;
    }
}
