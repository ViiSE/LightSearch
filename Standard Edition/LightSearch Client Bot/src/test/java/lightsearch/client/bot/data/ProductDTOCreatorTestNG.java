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
package lightsearch.client.bot.data;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ProductDTOCreatorTestNG {
    
    private ProductDTOCreator prodDTOCr;
    
    @SuppressWarnings("unchecked")
    @BeforeClass
    public void setUpClass() {
        String impl = "lightsearch.client.bot.data.ProductDTODefaultImpl";
        JSONObject data = new JSONObject();
        data.put("id", "10");
        data.put("amount", "10");
        
        prodDTOCr = ProductDTOCreatorInit.productDTOCreator(impl, data);
    }
    
    @Test
    public void createProductDTO() {
        testBegin("ProductDTO", "createProductDTO()");
        
        assertNotNull(prodDTOCr.createProductDTO(), "ProductDTO is null!");
        System.out.println("ProductDTO: " + prodDTOCr.createProductDTO());
        
        testEnd("ProductDTO", "createProductDTO()");
    }
}
