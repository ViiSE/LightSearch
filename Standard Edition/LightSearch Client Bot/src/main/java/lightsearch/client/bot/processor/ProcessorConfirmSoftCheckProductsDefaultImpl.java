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
package lightsearch.client.bot.processor;

import java.util.List;
import lightsearch.client.bot.CommandContentType;
import lightsearch.client.bot.CommandType;
import lightsearch.client.bot.data.ProductDTO;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import lightsearch.client.bot.data.BotDAO;

/**
 *
 * @author ViiSE
 */
public class ProcessorConfirmSoftCheckProductsDefaultImpl implements ProcessorConfirmSoftCheckProducts {
    
    private final String CONFIRM_SOFT_CHECK_PRODUCTS = CommandType.CONFIRM_SOFT_CHECK_PRODUCTS.toString();
    
    private final String COMMAND    = CommandContentType.COMMAND.toString();
    private final String IMEI_FIELD = CommandContentType.IMEI.toString();
    private final String IDENT      = CommandContentType.IDENT.toString();
    private final String CARD_CODE  = CommandContentType.CARD_CODE.toString();
    private final String DATA       = CommandContentType.DATA.toString();
    private final String ID         = CommandContentType.ID.toString();
    private final String AMOUNT     = CommandContentType.AMOUNT.toString();
    
    private final List<ProductDTO> products;

    public ProcessorConfirmSoftCheckProductsDefaultImpl(List<ProductDTO> products) {
        this.products = products;
    }
    
    @Override
    public void apply(BotDAO botDAO, MessageSender messageSender, MessageRecipient messageRecipient, long delay) {
        try {
            String request = generateJSONRequest(botDAO);
            messageSender.sendMessage(request);
            String response = messageRecipient.acceptMessage();
            System.out.println("[BOT] " + botDAO.botName() + " -> ConfirmSoftCheckProducts, RESPONSE: " + response);
            try {Thread.sleep(delay);} catch(InterruptedException ignore) {}
        } catch (MessageSenderException | MessageRecipientException ex) {
            System.out.println("CATCH! [BOT] " + botDAO.botName() + " -> ConfirmSoftCheckProducts, test.message - " + ex.getMessage());
            try {Thread.sleep(delay);} catch(InterruptedException ignore) {}
        }
    }
    
    private String generateJSONRequest(BotDAO botDAO) {
        JSONObject jObj = new JSONObject();
        JSONArray jData = new JSONArray();
        products.stream().map((product) -> {
            JSONObject jPr = new JSONObject();
            jPr.put(ID, product.id());
            jPr.put(AMOUNT, product.amount());
            return jPr;
        }).forEachOrdered((jPr) -> { jData.add(jPr); });
        jObj.put(COMMAND, CONFIRM_SOFT_CHECK_PRODUCTS);
        jObj.put(IMEI_FIELD, botDAO.IMEI());
        jObj.put(IDENT, botDAO.userIdent());
        jObj.put(CARD_CODE, botDAO.cardCode());
        jObj.put(DATA, jData);
        
        return jObj.toJSONString();
    }
}
