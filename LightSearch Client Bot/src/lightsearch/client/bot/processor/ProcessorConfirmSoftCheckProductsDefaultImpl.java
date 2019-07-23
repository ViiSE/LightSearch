/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.processor;

import java.util.List;
import lightsearch.client.bot.CommandContentType;
import lightsearch.client.bot.CommandType;
import lightsearch.client.bot.data.LightSearchClientBotDTO;
import lightsearch.client.bot.data.ProductDTO;
import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class ProcessorConfirmSoftCheckProductsDefaultImpl implements Processor {
    
    private final String CONFIRM_SOFT_CHECK_PRODUCTS = CommandType.CONFIRM_SOFT_CHECK_PRODUCTS.toString();
    
    private final String COMMAND    = CommandContentType.COMMAND.toString();
    private final String IMEI_FIELD = CommandContentType.IMEI.toString();
    private final String IDENT      = CommandContentType.IDENT.toString();
    private final String CARD_CODE  = CommandContentType.CARD_CODE.toString();
    private final String DATA       = CommandContentType.DATA.toString();
    private final String ID         = CommandContentType.ID.toString();
    private final String AMOUNT     = CommandContentType.AMOUNT.toString();
    
    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;
    
    private final String botName;
    private final String IMEI;
    private final String ident;
    private final String cardCode;
    private final List<ProductDTO> products;

    public ProcessorConfirmSoftCheckProductsDefaultImpl(LightSearchClientBotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient, List<ProductDTO> products) {
        this.botName  = botDTO.botName();
        this.IMEI     = botDTO.IMEI();
        this.ident    = botDTO.userIdent();
        this.cardCode = botDTO.cardCode();
        this.products = products;
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
        JSONArray jData = new JSONArray();
        products.stream().map((product) -> {
            JSONObject jPr = new JSONObject();
            jPr.put(ID, product.id());
            jPr.put(AMOUNT, product.amount());
            return jPr;
        }).forEachOrdered((jPr) -> { jData.add(jPr); });
        jObj.put(COMMAND, CONFIRM_SOFT_CHECK_PRODUCTS);
        jObj.put(IMEI_FIELD, IMEI);
        jObj.put(IDENT, ident);
        jObj.put(CARD_CODE, cardCode);
        jObj.put(DATA, jData);
        
        return jObj.toJSONString();
    }
}
