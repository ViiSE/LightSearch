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
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import org.json.simple.JSONObject;
import lightsearch.client.bot.data.BotDTO;

/**
 *
 * @author ViiSE
 */
public class ProcessorOpenSoftCheckDefaultImpl implements Processor {
    
    private final String OPEN_SOFT_CHECK = CommandType.OPEN_SOFT_CHECK.toString();
    
    private final String COMMAND    = CommandContentType.COMMAND.toString();
    private final String IMEI_FIELD = CommandContentType.IMEI.toString();
    private final String IDENT      = CommandContentType.IDENT.toString();
    private final String CARD_CODE  = CommandContentType.CARD_CODE.toString();
    
    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;
    
    private final String botName;
    private final String IMEI;
    private final String ident;
    private final String cardCode;

    public ProcessorOpenSoftCheckDefaultImpl(BotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient) {
        this.botName  = botDTO.botName();
        this.IMEI     = botDTO.IMEI();
        this.ident    = botDTO.userIdent();
        this.cardCode = botDTO.cardCode();
        this.messageSender = messageSender;
        this.messageRecipient = messageRecipient;
    }
    
    @Override
    public void apply() {
        try {
            String request = generateJSONRequest();
            messageSender.sendMessage(request);
            String response = messageRecipient.acceptMessage();
            System.out.println("Bot " + botName + ": Authorization, RESPONSE: " + response);
        } catch (MessageSenderException | MessageRecipientException ex) {
            System.out.println("CATCH! Bot " + botName + ": Authorization, message - " + ex.getMessage());
            try {Thread.sleep(1);} catch(InterruptedException ignore) {}
        }
    }
    
    private String generateJSONRequest() {
        JSONObject jObj = new JSONObject();
        jObj.put(COMMAND, OPEN_SOFT_CHECK);
        jObj.put(IMEI_FIELD, IMEI);
        jObj.put(IDENT, ident);
        jObj.put(CARD_CODE, cardCode);
        
        return jObj.toJSONString();
    }
}
