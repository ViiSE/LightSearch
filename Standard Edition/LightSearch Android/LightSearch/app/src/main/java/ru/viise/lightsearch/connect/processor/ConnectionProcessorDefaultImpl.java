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
package ru.viise.lightsearch.connect.processor;

import ru.viise.lightsearch.connect.processor.result.ConnectionProcessorResult;
import ru.viise.lightsearch.connect.processor.result.ConnectionProcessorResultInit;
import ru.viise.lightsearch.exception.MessageRecipientException;
import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.type.MessageConnection;
import ru.viise.lightsearch.message.type.MessageConnectionInit;

/**
 *
 * @author ViiSE
 */
public class ConnectionProcessorDefaultImpl implements ConnectionProcessor {

    private final MessageSender messageSender;
    private final MessageRecipient messageRecipient;

    public ConnectionProcessorDefaultImpl(MessageSender messageSender, MessageRecipient messageRecipient) {
        this.messageSender = messageSender;
        this.messageRecipient = messageRecipient;
    }

    @Override
    public ConnectionProcessorResult apply() {
        try {
            MessageConnection msgConn = MessageConnectionInit.messageConnection();
            String msgConnStr = msgConn.message();
            messageSender.sendMessage(msgConnStr);
            String message = messageRecipient.acceptMessage();
            return ConnectionProcessorResultInit.connectionProcessorResult(message);
        } catch (MessageSenderException |
                MessageRecipientException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
