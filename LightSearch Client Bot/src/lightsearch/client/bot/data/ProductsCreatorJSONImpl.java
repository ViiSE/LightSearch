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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.settings.BotSettingsEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class ProductsCreatorJSONImpl implements ProductsCreator {

    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String PRODUCT_LIST   = BotSettingsEnum.PRODUCT_LIST.toString();
    
    private final JSONObject jProductsData;
    
    public ProductsCreatorJSONImpl(Object rawProductsData) {
        jProductsData = (JSONObject) rawProductsData;
    }

    @Override
    public List<ProductDTO> createProducts() {
        List<ProductDTO> prods = new ArrayList<>();
        String impl = jProductsData.get(IMPLEMENTATION).toString();
        
        JSONArray jProdList = (JSONArray) jProductsData.get(PRODUCT_LIST);
            
        jProdList.forEach(prodObj -> {
            JSONObject jProd = (JSONObject) prodObj;
            ProductDTOCreator prodDTOCr = ProductDTOCreatorInit.productDTOCreator(impl, jProd);
            ProductDTO prodDTO = prodDTOCr.createProductDTO();
            prods.add(prodDTO);
        });
        
        return prods;
    }
}
