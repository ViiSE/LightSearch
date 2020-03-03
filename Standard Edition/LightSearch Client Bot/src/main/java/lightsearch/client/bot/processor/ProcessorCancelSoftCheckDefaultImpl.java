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

import lightsearch.client.bot.CommandContentType;
import lightsearch.client.bot.CommandType;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class ProcessorCancelSoftCheckDefaultImpl implements Processor {

    private final String CANCEL_SOFT_CHECK = CommandType.CANCEL_SOFT_CHECK.toString();
    
    private final String COMMAND    = CommandContentType.COMMAND.toString();
    private final String IMEI_FIELD = CommandContentType.IMEI.toString();
    private final String IDENT      = CommandContentType.IDENT.toString();
    private final String CARD_CODE  = CommandContentType.CARD_CODE.toString();
    
    @Override
    public void apply(BotDAO botDAO, MessageSender messageSender, MessageRecipient messageRecipient, long delay) {
        try {
            String request = generateJSONRequest(botDAO);
            messageSender.sendMessage(request);
            String response = messageRecipient.acceptMessage();
            System.out.println("[BOT] " + botDAO.botName() + " -> CancelSoftCheck, RESPONSE: " + response);
            try {Thread.sleep(delay);} catch(InterruptedException ignore) {}
        } catch (MessageSenderException | MessageRecipientException ex) {
            System.out.println("CATCH! [BOT] " + botDAO.botName() + " -> CancelSoftCheck, test.message - " + ex.getMessage());
            try {Thread.sleep(delay);} catch(InterruptedException ignore) {}
        }
    }
    
    @SuppressWarnings("unchecked")
    private String generateJSONRequest(BotDAO botDAO) {
        JSONObject jObj = new JSONObject();
        jObj.put(COMMAND, CANCEL_SOFT_CHECK);
        jObj.put(IMEI_FIELD, botDAO.IMEI());
        jObj.put(IDENT, botDAO.userIdent());
        jObj.put(CARD_CODE, botDAO.cardCode());
        
        return jObj.toJSONString();
    }
}
