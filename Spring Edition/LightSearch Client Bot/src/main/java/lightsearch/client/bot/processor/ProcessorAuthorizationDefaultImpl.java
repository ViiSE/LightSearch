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

import lightsearch.client.bot.constants.CommandContentType;
import lightsearch.client.bot.constants.CommandType;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("lightsearch.client.bot.processor.ProcessorAuthorizationDefaultImpl")
@Scope("prototype")
public class ProcessorAuthorizationDefaultImpl implements Processor {

    private final String CONNECT = CommandType.CONNECT.toString();
    
    private final String COMMAND    = CommandContentType.COMMAND.toString();
    private final String IMEI_FIELD = CommandContentType.IMEI.toString();
    private final String IP         = CommandContentType.IP.toString();
    private final String OS         = CommandContentType.OS.toString();
    private final String MODEL      = CommandContentType.MODEL.toString();
    private final String USERNAME   = CommandContentType.USERNAME.toString();
    private final String PASSWORD   = CommandContentType.PASSWORD.toString();
    private final String IDENT      = CommandContentType.IDENT.toString();
    
    @Override
    public void apply(BotDAO botDAO, MessageSender messageSender, MessageRecipient messageRecipient, long delay) {
        try {
            String request = generateJSONRequest(botDAO);
            messageSender.sendMessage(request);
            String response = messageRecipient.acceptMessage();
            System.out.println("[BOT] " + botDAO.botName() + " -> Authorization, RESPONSE: " + response);
            try {Thread.sleep(delay);} catch(InterruptedException ignore) {}
        } catch (MessageSenderException | MessageRecipientException ex) {
            System.out.println("CATCH! [BOT] " + botDAO.botName() + " -> Authorization, test.message - " + ex.getMessage());
            try {Thread.sleep(delay);} catch(InterruptedException ignore) {}
        }
    }
    
    private String generateJSONRequest(BotDAO botDAO) {
        JSONObject jObj = new JSONObject();
        jObj.put(COMMAND, CONNECT);
        jObj.put(IMEI_FIELD, botDAO.IMEI());
        jObj.put(IP, "127.0.0.1");
        jObj.put(OS, "LS bots [-_-] [`_`] ['_']");
        jObj.put(MODEL, "HAL 9000");
        jObj.put(USERNAME, botDAO.username());
        jObj.put(PASSWORD, botDAO.userIdent());
        jObj.put(IDENT, botDAO.userIdent());
        
        return jObj.toJSONString();
    }
}
