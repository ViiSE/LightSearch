/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.processor;

import lightsearch.client.bot.CommandContentType;
import lightsearch.client.bot.CommandType;
import lightsearch.client.bot.data.LightSearchClientBotDTO;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class ProcessorAuthorizationDefaultImpl implements Processor {

    private final String CONNECT = CommandType.CONNECT.toString();
    
    private final String COMMAND    = CommandContentType.COMMAND.toString();
    private final String IMEI_FIELD = CommandContentType.IMEI.toString();
    private final String USERNAME   = CommandContentType.USERNAME.toString();
    private final String IDENT      = CommandContentType.IDENT.toString();
    
    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;
    
    private final String botName;
    private final String username;
    private final String IMEI;
    private final String userIdent;

    public ProcessorAuthorizationDefaultImpl(LightSearchClientBotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient) {
        this.botName   = botDTO.botName();
        this.username  = botDTO.username();
        this.IMEI      = botDTO.IMEI();
        this.userIdent = botDTO.userIdent();
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
        }
    }
    
    private String generateJSONRequest() {
        JSONObject jObj = new JSONObject();
        jObj.put(COMMAND, CONNECT);
        jObj.put(IMEI_FIELD, IMEI);
        jObj.put(USERNAME, username);
        jObj.put(IDENT, userIdent);
        
        return jObj.toJSONString();
    }
}
