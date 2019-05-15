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
    
    private final ProductsMapDebug products;
    
    public SearchProcessorDebug(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    
        products = ProductsMapDebugInit.productsMapDebug();
    }
    
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.barcode(), 
                clientCommand.sklad(), clientCommand.TK())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {
                JSONArray jData = new JSONArray();
                
                products.map().forEach((id, product) -> {
                    if(product.id().equals(clientCommand.barcode()))
                        if(clientCommand.sklad().equals("all") &&
                                clientCommand.TK().equals("all")) {
                            JSONObject jProd = new JSONObject();
                                jProd.put("podrazdelenie", product.podrazdelenie());
                                jProd.put("ID", product.id());
                                jProd.put("name", product.name());
                                jProd.put("price", product.price());
                                jProd.put("amount", product.amount());

                                jData.add(jProd);
                        }
                        else if(clientCommand.sklad().equals("all")) {
                            if(product.podrazdelenie().contains("Склад")) {
                                JSONObject jProd = new JSONObject();
                                jProd.put("podrazdelenie", product.podrazdelenie());
                                jProd.put("ID", product.id());
                                jProd.put("name", product.name());
                                jProd.put("price", product.price());
                                jProd.put("amount", product.amount());

                                jData.add(jProd);
                            }
                        }
                        else if(clientCommand.TK().equals("all")) {
                            if(product.podrazdelenie().contains("Склад")) {
                                JSONObject jProd = new JSONObject();
                                jProd.put("podrazdelenie", product.podrazdelenie());
                                jProd.put("ID", product.id());
                                jProd.put("name", product.name());
                                jProd.put("price", product.price());
                                jProd.put("amount", product.amount());

                                jData.add(jProd);
                            }
                        }
                        else if(product.podrazdelenie().equals(clientCommand.sklad())) {
                            JSONObject jProd = new JSONObject();
                            jProd.put("podrazdelenie", product.podrazdelenie());
                            jProd.put("ID", product.id());
                            jProd.put("name", product.name());
                            jProd.put("price", product.price());
                            jProd.put("amount", product.amount());
                            
                            jData.add(jProd);
                        }
                        else if(product.podrazdelenie().equals(clientCommand.TK())) {
                            JSONObject jProd = new JSONObject();
                            jProd.put("podrazdelenie", product.podrazdelenie());
                            jProd.put("ID", product.id());
                            jProd.put("name", product.name());
                            jProd.put("price", product.price());
                            jProd.put("amount", product.amount());
                            
                            jData.add(jProd);
                        } 
                });
                
                JSONObject resJSON = new JSONObject();
                resJSON.put("IMEI", clientCommand.IMEI());
                resJSON.put("is_done", "True");
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
