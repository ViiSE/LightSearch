/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.cmd.client.processor.debug;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ViiSE
 */
public class ProductsMapDebugDefaultImpl implements ProductsMapDebug {

    private final Map<String, ProductDebug> products;
    
    public ProductsMapDebugDefaultImpl() {
        products = new HashMap<>();
        initProducts();
    }

    private void initProducts() {
        ProductDebug pr1 = createProduct("Склад 1", "111111", "Товар 1", "100.0", "5", "шт.");
        ProductDebug pr2 = createProduct("Склад 1", "222222", "Товар 2", "1500.0", "10", "шт.");
        ProductDebug pr3 = createProduct("Склад 2", "111111", "Товар 1", "100.0", "100", "шт.");
        ProductDebug pr4 = createProduct("Склад 2", "444444", "Товар 4", "65.0", "7", "шт.");
        ProductDebug pr5 = createProduct("ТК 1", "111111", "Товар 1", "100.0", "7", "шт.");
        ProductDebug pr6 = createProduct("ТК 2", "444444", "Товар 4", "65.0", "12", "шт.");
        
        products.put("1", pr1);
        products.put("2", pr2);
        products.put("3", pr3);
        products.put("4", pr4);
        products.put("5", pr5);
        products.put("6", pr6);
    }
    
    private ProductDebug createProduct(String subdiv, String id, String name, 
            String price, String amount, String unit) {
        ProductDebug pr = 
                ProductDebugInit.productDebug(subdiv, id, name, price, amount, unit);
        return pr;
    }

    @Override
    public Map<String, ProductDebug> map() {
        return products;
    }
}
