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
import lightsearch.server.exception.MessageParserException;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.message.parser.MessageParser;
import lightsearch.server.message.parser.MessageParserInit;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Обработчик команды подтверждения товаров мягкого чека для отладки.
 * <p>
 * Для симуляции мягкого чека используется {@link lightsearch.server.cmd.client.processor.debug.SoftCheckDebug}.
 * @author ViiSE
 * @since 2.0.0
 */
public class ConfirmSoftCheckProductsProcessorDebug extends AbstractProcessorClient {
    
    public ConfirmSoftCheckProductsProcessorDebug(LightSearchServerDTO serverDTO, 
            LightSearchChecker checker) {
        super(serverDTO, checker);
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.userIdentifier(), 
                clientCommand.cardCode(), clientCommand.data())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {
                
                String jMsgStr = "{\"data\": " + clientCommand.data() + "}";
                MessageParser msgParser = MessageParserInit.messageParser();
                try {
                    JSONObject message = (JSONObject)msgParser.parse(jMsgStr);
                    JSONArray data = (JSONArray)message.get("data");
                    
                    JSONArray newData = new JSONArray();
                    
                    for(Object product : data) {
                        JSONObject productJSON = (JSONObject)product;
                        String id = productJSON.get("ID").toString();
                        float amount = Float.parseFloat(productJSON.get("amount").toString());
                        
                        final MaxAmount maxAmount = new MaxAmount();
                        
                        ProductsMapDebug.PRODUCTS.forEach((ignore, productM) -> {
                            if(productM.id().equals("2200000738592"))
                                productM.delMaxAmount(1.f);
                            else if(productM.id().equals("111111"))
                                productM.delMaxAmount(1.f);
                                
                            if(productM.id().equals(id))
                                maxAmount.add(Float.parseFloat(productM.amount()));
                        });
                        
                        float maxAmountProduct = maxAmount.get();
                        
                        if(maxAmountProduct < amount) {
                            JSONObject newProd = new JSONObject();
                            newProd.put("ID", id);
                            newProd.put("amount", maxAmountProduct);
                            newData.add(newProd);
                        }
                    }

                    JSONObject resJSON = new JSONObject();
                    resJSON.put("IMEI", clientCommand.IMEI());
                    resJSON.put("is_done", "True");
                    resJSON.put("data", newData);
                    
                    String result = resJSON.toJSONString();

                    String logMessage = "Client " + clientCommand.IMEI() + " confirm SoftCheck products, "
                                + "user ident - " + clientCommand.userIdentifier()
                                + ", card code - " + clientCommand.cardCode();

                    return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                            result, logMessage);        
                
                } catch(MessageParserException ignore) {
                    return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Неверный формат команды. Вы были отключены от сервера", null);
                }    
            } else
                return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                        "Извините, но вы находитесь в черном списке. Отключение от сервера", null);
        } else
            return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Неверный формат команды. Вы были отключены от сервера", null);
    }
    
    private class MaxAmount {
        
        private float maxAmount = 0.f;
        
        public void add(float value) {
            maxAmount += value;
        }
        
        public float get() {
            return maxAmount;
        }
    }
}
