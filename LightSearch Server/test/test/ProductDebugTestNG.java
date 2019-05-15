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

import lightsearch.server.cmd.client.processor.debug.ProductDebug;
import lightsearch.server.cmd.client.processor.debug.ProductDebugInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ProductDebugTestNG {
    
    private ProductDebug product;
    
    @BeforeTest
    public void setUpMethod() {
        String subdiv = "Склад 1";
        assertNotNull(subdiv, "Subdivision is null!");
        assertFalse(subdiv.isEmpty(), "Subdivision is empty!");
        
        String id = "111111";
        assertNotNull(id, "ID is null!");
        assertFalse(id.isEmpty(), "Subdivision is empty!");
        
        String name = "Товар 1";
        assertNotNull(name, "Name is null!");
        assertFalse(name.isEmpty(), "Name is empty!");
        
        String price = "100.0";
        assertNotNull(price, "Price is null!");
        assertFalse(price.isEmpty(), "price is empty!");
        
        String amount = "45.0";
        assertNotNull(amount, "Amount is null!");
        assertFalse(amount.isEmpty(), "Amount is empty!");
        
        String unit = "шт.";
        assertNotNull(unit, "Unit is null!");
        assertFalse(unit.isEmpty(), "Unit is empty!");
        
        product = ProductDebugInit.productDebug(subdiv, id, name, price, amount, unit);
        assertNotNull(product, "ProductDebug is null!");
    }
    
    @Test
    public void subdivision() {
        testBegin("ProductDebug", "subdivision()");
        
        String subdiv = product.subdivision();
        assertNotNull(subdiv, "ProductDebug: subdivision is null!");
        System.out.println("ProductDebug: subdivision: " + subdiv);
        
        testEnd("ProductDebug", "subdivision()");
    }
    
    @Test
    public void id() {
        testBegin("ProductDebug", "id()");
        
        String id = product.id();
        assertNotNull(id, "ProductDebug: id is null!");
        System.out.println("ProductDebug: id: " + id);
        
        testEnd("ProductDebug", "id()");
    }
    
    @Test
    public void name() {
        testBegin("ProductDebug", "name()");
        
        String name = product.name();
        assertNotNull(name, "ProductDebug: name is null!");
        System.out.println("ProductDebug: name: " + name);
        
        testEnd("ProductDebug", "name()");
    }
    
    @Test
    public void price() {
        testBegin("ProductDebug", "price()");
        
        String price = product.price();
        assertNotNull(price, "ProductDebug: id is null!");
        System.out.println("ProductDebug: price: " + price);
        
        testEnd("ProductDebug", "price()");
    }
    
    @Test
    public void amount() {
        testBegin("ProductDebug", "amount()");
        
        String amount = product.amount();
        assertNotNull(amount, "ProductDebug: amount is null!");
        System.out.println("ProductDebug: amount: " + amount);
        
        testEnd("ProductDebug", "amount()");
    }
    
    @Test
    public void unit() {
        testBegin("ProductDebug", "unit()");
        
        String unit = product.unit();
        assertNotNull(unit, "ProductDebug: unit is null!");
        System.out.println("ProductDebug: unit: " + unit);
        
        testEnd("ProductDebug", "unit()");
    }
}
