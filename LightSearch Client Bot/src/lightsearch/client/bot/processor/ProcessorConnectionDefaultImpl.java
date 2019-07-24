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
public class ProcessorConnectionDefaultImpl implements Processor {

    private final String IDENTIFIER = CommandType.IDENTIFIER.toString();
    
    private final String CLIENT = CommandContentType.CLIENT.toString();
    
    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;
    private final String botName;
    
    public ProcessorConnectionDefaultImpl(BotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient) {
        this.botName = botDTO.botName();
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
        jObj.put(IDENTIFIER, CLIENT);
        
        return jObj.toJSONString();
    }
}
