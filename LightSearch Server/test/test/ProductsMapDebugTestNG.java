/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package test;

import java.util.Map;
import lightsearch.server.cmd.client.processor.debug.ProductsMapDebug;
import lightsearch.server.cmd.client.processor.debug.ProductsMapDebugInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ProductsMapDebugTestNG {
    
    @Test
    public void map() {
        testBegin("ProductsMapDebug", "map()");
        
        ProductsMapDebug product = ProductsMapDebugInit.productsMapDebug();
        assertNotNull(product, "ProductMapDebug is null!");
        
        Map map = product.map();
        assertNotNull(map, "Map is null!");
        
        System.out.println("ProductsMapDebug: map: " + map);
        
        testEnd("ProductsMapDebug", "map()");
    }
}
