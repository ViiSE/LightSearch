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
import lightsearch.client.bot.data.SearchDTO;
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
public class ProcessorSearchDefaultImpl implements Processor {

    private final String SEARCH     = CommandType.SEARCH.toString();
    
    private final String COMMAND    = CommandContentType.COMMAND.toString();
    private final String IMEI_FIELD = CommandContentType.IMEI.toString();
    private final String BARCODE    = CommandContentType.BARCODE.toString();
    private final String SKLAD      = CommandContentType.SKLAD.toString();
    private final String TK_FIELD   = CommandContentType.TK.toString();
    
    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;
    
    private final String botName;
    private final String IMEI;
    private final String barcode;
    private final String sklad;
    private final String TK;

    public ProcessorSearchDefaultImpl(BotDTO botDTO, SearchDTO searchDTO,
            MessageSender messageSender, MessageRecipient messageRecipient) {
        this.botName = botDTO.botName();
        this.IMEI    = botDTO.IMEI();
        this.barcode = searchDTO.barcode();
        this.sklad   = searchDTO.sklad();
        this.TK      = searchDTO.TK();
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
        jObj.put(COMMAND, SEARCH);
        jObj.put(IMEI_FIELD, IMEI);
        jObj.put(BARCODE, barcode);
        jObj.put(SKLAD, sklad);
        jObj.put(TK_FIELD, TK);
        
        return jObj.toJSONString();
    }
}
