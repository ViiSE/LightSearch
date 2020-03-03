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

package lightsearch.client.bot.producer;

import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotEntityDTO;
import lightsearch.client.bot.data.BotSettingsDTO;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.net.Socket;

@Service("botEntityDTOProducerDefault")
public class BotEntityDTOProducerDefaultImpl implements BotEntityDTOProducer {

    private final String BOT_ENTITY_DTO = "botEntityDTODefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public BotEntityDTO getBotEntityDTODefaultInstance(BotDAO botDAO, Socket socket, BotSettingsDTO botSettingsDTO,
                                                       MessageSender messageSender, MessageRecipient messageRecipient,
                                                       long delayMessageDisplay) {
        return (BotEntityDTO) ctx.getBean(BOT_ENTITY_DTO,
                botDAO, socket, botSettingsDTO, messageSender, messageRecipient, delayMessageDisplay);
    }
}
