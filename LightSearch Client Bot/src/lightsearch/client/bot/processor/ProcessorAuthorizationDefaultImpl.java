/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;
    
    private final String botName;
    private final String username;
    private final String IMEI;
    private final String userIdent;

    public ProcessorAuthorizationDefaultImpl(BotDTO botDTO, 
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
            try {Thread.sleep(1);} catch(InterruptedException ignore) {}
        }
    }
    
    private String generateJSONRequest() {
        JSONObject jObj = new JSONObject();
        jObj.put(COMMAND, CONNECT);
        jObj.put(IMEI_FIELD, IMEI);
        jObj.put(IP, "127.0.0.1");
        jObj.put(OS, "Windows bots └[∵┌]└[ ∵ ]┘[┐∵]┘");
        jObj.put(MODEL, "HAL 9000");
        jObj.put(USERNAME, username);
        jObj.put(PASSWORD, "00000");
        jObj.put(IDENT, userIdent);
        
        return jObj.toJSONString();
    }
}