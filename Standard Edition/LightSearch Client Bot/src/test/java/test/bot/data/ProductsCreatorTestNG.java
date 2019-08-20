/*
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.bot.data;

import java.util.List;
import lightsearch.client.bot.data.ProductDTO;
import lightsearch.client.bot.data.ProductsCreator;
import lightsearch.client.bot.data.ProductsCreatorInit;
import lightsearch.client.bot.settings.BotSettingsEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ProductsCreatorTestNG {
    
    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String PRODUCT_LIST   = BotSettingsEnum.PRODUCT_LIST.toString();
    private final String ID             = BotSettingsEnum.ID.toString();
    private final String AMOUNT         = BotSettingsEnum.AMOUNT.toString();
    
    private ProductsCreator prodCr;
    
    @BeforeClass
    public void setUpClass() {
        JSONObject prodsData = getProductsData();
        prodCr = ProductsCreatorInit.productsCreator(prodsData);
    }

    @Test
    public void createProducts() {
        testBegin("ProductsCreator", "createProducts()");
        
        List<ProductDTO> products = prodCr.createProducts();
        assertNotNull(products, "List<ProductDTO> products is null!");
        
        System.out.println("products: " + products);
        products.forEach(prod -> {
            System.out.println("Product!");
            System.out.println("ID: " + prod.id());
            System.out.println("Amount: " + prod.amount());
        });
        
        testEnd("ProductsCreator", "createProducts()");
    }
    
    private JSONObject getProductsData() {
        JSONObject data = new JSONObject();
        JSONArray prods = new JSONArray();
        
        JSONObject prod1 = new JSONObject();
        prod1.put(ID, "111111");
        prod1.put(AMOUNT, "10");
        
        JSONObject prod2 = new JSONObject();
        prod2.put(ID, "222222");
        prod2.put(AMOUNT, "20");
        
        prods.add(prod1);
        prods.add(prod2);
        
        data.put(IMPLEMENTATION, "lightsearch.client.test.bot.data.ProductDTODefaultImpl");
        data.put(PRODUCT_LIST, prods);
        
        return data;
    }
}
