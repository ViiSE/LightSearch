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
        ProductDebug pr1 = createProduct("Склад 1", "111111", "Товар 1", "100 руб.", "5 шт.");
        ProductDebug pr2 = createProduct("Склад 1", "222222", "Товар 2", "150 руб.", "10 шт.");
        ProductDebug pr3 = createProduct("Склад 2", "333333", "Товар 3", "10 руб.", "100 шт.");
        ProductDebug pr4 = createProduct("Склад 2", "444444", "Товар 4", "65 руб.", "7 шт.");
        
        products.put(pr1.id(), pr1);
        products.put(pr2.id(), pr2);
        products.put(pr3.id(), pr3);
        products.put(pr4.id(), pr4);
    }
    
    private ProductDebug createProduct(String podr, String id, String name, 
            String price, String amount) {
        ProductDebug pr = 
                ProductDebugInit.productDebug(podr, id, name, price, amount);
        return pr;
    }

    @Override
    public Map<String, ProductDebug> map() {
        return products;
    }
}
