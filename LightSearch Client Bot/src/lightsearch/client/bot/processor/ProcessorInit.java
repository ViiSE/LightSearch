/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightsearch.client.bot.processor;

import java.util.List;
import lightsearch.client.bot.data.LightSearchClientBotDTO;
import lightsearch.client.bot.data.ProductDTO;
import lightsearch.client.bot.data.SearchDTO;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;

/**
 *
 * @author ViiSE
 */
public class ProcessorInit {

    public static Processor processorAuthorization(LightSearchClientBotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorAuthorizationDefaultImpl(botDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorSearch(LightSearchClientBotDTO botDTO, 
            SearchDTO searchDTO, MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorSearchDefaultImpl(botDTO, searchDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorOpenSoftCheck(LightSearchClientBotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorOpenSoftCheckDefaultImpl(botDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorCancelSoftCheck(LightSearchClientBotDTO botDTO,
            MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorCancelSoftCheckDefaultImpl(botDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorCloseSoftCheck(LightSearchClientBotDTO botDTO,
            String delivery, MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorCloseSoftCheckDefaultImpl(botDTO, delivery, messageSender, messageRecipient);
    }
    
    public static Processor processorConfirmSoftCheckProducts(LightSearchClientBotDTO botDTO,
            MessageSender messageSender, MessageRecipient messageRecipient, List<ProductDTO> products) {
        return new ProcessorConfirmSoftCheckProductsDefaultImpl(botDTO, messageSender, 
                messageRecipient, products);
    }
}
