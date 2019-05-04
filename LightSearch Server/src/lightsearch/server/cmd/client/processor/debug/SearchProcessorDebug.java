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
package lightsearch.server.cmd.client.processor.debug;

import java.util.HashMap;
import java.util.Map;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.processor.AbstractProcessorClient;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class SearchProcessorDebug extends AbstractProcessorClient {
    
    Map<String, ProductDebug> products;
    
    public SearchProcessorDebug(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    
        initProducts();
    }

    private void initProducts() {
        products = new HashMap<>();
        
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
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.barcode(), 
                clientCommand.sklad(), clientCommand.TK())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {
                
                JSONObject resJSON = new JSONObject();
                resJSON.put("IMEI", clientCommand.IMEI());
                
                JSONArray jData = new JSONArray();
                
                products.forEach((id, product) -> {
                    if(id.equals(clientCommand.barcode()))
                        if(product.podrazdelenie().equals(clientCommand.sklad())) {
                            JSONObject jProd = new JSONObject();
                            jProd.put("podrazdelenie", product.podrazdelenie());
                            jProd.put("ID", product.id());
                            jProd.put("name", product.name());
                            jProd.put("price", product.price());
                            jProd.put("amount", product.amount());
                            
                            jData.add(jProd);
                        }
                });
                
                resJSON.put("data", jData);
                
                String result = resJSON.toJSONString();

                return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE, 
                        result, "");
            }
            else
                return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Извините, но вы находитесь в черном списке. Отключение от сервера", null);
        }
        else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Неверный формат команды. Обратитесь к администратору для устранения ошибки. Вы были отключены от сервера", null);
    }
    
}
