/*
 * Copyright @today.year ViiSE.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
 * Обработчик команды проверки привязки для отладки.
 * @author ViiSE
 * @since 2.8.1
 */
public class BindCheckProcessorDebug extends AbstractProcessorClient {

    public BindCheckProcessorDebug(LightSearchServerDTO serverDTO, LightSearchChecker checker) {
        super(serverDTO, checker);
    }
    
    @Override
    public CommandResult apply(ClientCommand clientCommand) {
        if(!super.checker.isNull(clientCommand.IMEI(), clientCommand.barcode())) {
            if(!serverDTO.blacklist().contains(clientCommand.IMEI())) {       
                if(clientCommand.barcode().equals("111111")) {
                    String result =
                        "{\n"
                            + "\"IMEI\": \"" + clientCommand.IMEI() + "\",\n"
                            + "\"is_done\": \"True\",\n"
                            + "\"data\": []\n"
                        + "}";
                    return super.commandResult(
                            clientCommand.IMEI(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE, result, "");
                } else {
                    JSONArray jData = new JSONArray();

                    ProductsMapDebug.PRODUCTS.forEach((id, product) -> {
                        if(product.name().contains(clientCommand.barcode()))
                            addProductJSONtoData(jData, product);
                    });

                    JSONObject resJSON = new JSONObject();
                    resJSON.put("IMEI", clientCommand.IMEI());
                    resJSON.put("is_done", "True");
                    resJSON.put("data", jData);

                    String result = resJSON.toJSONString();

                    return super.commandResult(clientCommand.IMEI(), LogMessageTypeEnum.INFO, ResultTypeMessageEnum.TRUE,
                            result, "");
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

    private void addProductJSONtoData(JSONArray jData, ProductDebug product) {
        JSONObject jProd = new JSONObject();
        jProd.put("ID", product.id());
        jProd.put("name", product.name());
        jData.add(jProd);
    }
}
