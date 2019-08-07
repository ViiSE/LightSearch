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

import lightsearch.client.bot.data.ProductDTO;
import lightsearch.client.bot.data.ProductDTOInit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ProductDTOTestNG {
    
    private ProductDTO productDTO;
    
    @BeforeClass
    public void setUpClass() {
        String id = "111111";
        String amount = "10";
        productDTO = ProductDTOInit.productDTO(id, amount);
    }
    
    @Test
    public void id() {
        testBegin("ProductDTO", "id()");
        
        assertNotNull(productDTO.id(), "ID is null!");
        try {
            int idInt = Integer.parseInt(productDTO.id());
            assertFalse(idInt < 0, "ID value is less than 0!");
            System.out.println("ID: " + productDTO.id());
        }
        catch(NumberFormatException ex) {
            throw new RuntimeException("ID is not a number! Exception test.message: " + ex.getMessage());
        }
        
        testEnd("ProductDTO", "id()");
    }
    
    @Test
    public void amount() {
        testBegin("ProductDTO", "amount()");
        
        assertNotNull(productDTO.amount(), "Amount is null!");
        try {
            int amountInt = Integer.parseInt(productDTO.amount());
            assertFalse(amountInt < 0, "Amount value is less than 0!");
            System.out.println("Amount: " + productDTO.amount());
        }
        catch(NumberFormatException ex) {
            throw new RuntimeException("Amount is not a number! Exception test.message: " + ex.getMessage());
        }
        
        testEnd("ProductDTO", "amount()");
    }
}
