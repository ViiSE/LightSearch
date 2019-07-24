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
import lightsearch.client.bot.data.ProductDTO;
import lightsearch.client.bot.data.SearchDTO;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.data.BotDTO;

/**
 *
 * @author ViiSE
 */
public class ProcessorInit {

    public static Processor processorConnection(BotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorConnectionDefaultImpl(botDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorAuthorization(BotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorAuthorizationDefaultImpl(botDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorSearch(BotDTO botDTO, 
            SearchDTO searchDTO, MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorSearchDefaultImpl(botDTO, searchDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorOpenSoftCheck(BotDTO botDTO, 
            MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorOpenSoftCheckDefaultImpl(botDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorCancelSoftCheck(BotDTO botDTO,
            MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorCancelSoftCheckDefaultImpl(botDTO, messageSender, messageRecipient);
    }
    
    public static Processor processorCloseSoftCheck(BotDTO botDTO,
            String delivery, MessageSender messageSender, MessageRecipient messageRecipient) {
        return new ProcessorCloseSoftCheckDefaultImpl(botDTO, delivery, messageSender, messageRecipient);
    }
    
    public static Processor processorConfirmSoftCheckProducts(BotDTO botDTO,
            MessageSender messageSender, MessageRecipient messageRecipient, List<ProductDTO> products) {
        return new ProcessorConfirmSoftCheckProductsDefaultImpl(botDTO, messageSender, 
                messageRecipient, products);
    }
}
