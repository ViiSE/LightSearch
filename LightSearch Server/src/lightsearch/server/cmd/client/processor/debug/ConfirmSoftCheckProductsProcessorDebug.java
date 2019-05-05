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

import lightsearch.server.cmd.client.processor.*;
import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.cmd.client.ClientCommand;
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
 *
 * @author ViiSE
 */
public class ConfirmSoftCheckProductsProcessorDebug extends AbstractProcessorClient {
    
    private final ProductsMapDebug products;
    
    public ConfirmSoftCheckProductsProcessorDebug(LightSearchServerDTO serverDTO, 
            LightSearchChecker checker, ProductsMapDebug products) {
        super(serverDTO, checker);
        this.products = products;
    }
    
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.username(), 
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
                        String id = productJSON.get("id").toString();
                        int amount = Integer.parseInt(productJSON.get("amount").toString());
                        if(products.map().get(id) != null) {
                            int maxAmountProduct = 
                                    Integer.parseInt(products.map().get(id).amount());
                            
                            if(maxAmountProduct < amount) {
                                JSONObject newProd = new JSONObject();
                                newProd.put("id", id);
                                newProd.put("amount", maxAmountProduct);
                                
                                newData.add(newProd);
                            }
                        }
                    }

                    JSONObject resJSON = new JSONObject();
                    resJSON.put("IMEI", clientCommand.IMEI());
                    resJSON.put("isDone", "True");
                    resJSON.put("data", newData);
                    
                    String result = resJSON.toJSONString();

                    String logMessage = "Client " + clientCommand.IMEI() + " confirm SoftCheck products, "
                                + "username - " + clientCommand.username() 
                                + ", card code - " + clientCommand.cardCode();

                    return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                            result, logMessage);        
                
                }
                catch(MessageParserException ignore) {
                    return super.commandResult("Unknown", LogMessageTypeEnum.ERROR, ResultTypeMessageEnum.FALSE,
                    "Неверный формат команды. Обратитесь к администратору для устранения ошибки. Вы были отключены от сервера", null);
                }    
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
