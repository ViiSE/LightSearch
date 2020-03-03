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
package lightsearch.client.bot.data;

import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;

import java.net.Socket;

/**
 *
 * @author ViiSE
 */
public class BotEntityDTODefaultImpl implements BotEntityDTO {

    private final BotDAO botDAO;
    private final Socket socket;
    private final BotSettingsDTO botSettingsDTO;
    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;
    private final long delayMessageDisplay;

    public BotEntityDTODefaultImpl(BotDAO botDAO, Socket socket, 
            BotSettingsDTO botSettingsDTO, MessageSender messageSender, 
            MessageRecipient messageRecipient, long delayMessageDisplay) {
        this.botDAO = botDAO;
        this.socket = socket;
        this.botSettingsDTO = botSettingsDTO;
        this.messageSender = messageSender;
        this.messageRecipient = messageRecipient;
        this.delayMessageDisplay = delayMessageDisplay;
    }

    @Override
    public BotDAO botDAO() {
        return botDAO;
    }

    @Override
    public Socket socket() {
        return socket;
    }

    @Override
    public BotSettingsDTO botSettingsDTO() {
        return botSettingsDTO;
    }

    @Override
    public MessageSender messageSender() {
        return messageSender;
    }

    @Override
    public MessageRecipient messageRecipient() {
        return messageRecipient;
    }

    @Override
    public long delayMessageDisplay() {
        return delayMessageDisplay;
    }
}
