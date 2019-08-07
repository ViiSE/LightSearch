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
package lightsearch.admin.panel.connect.processor;

import lightsearch.admin.panel.connect.processor.result.ConnectionProcessorResult;
import lightsearch.admin.panel.connect.processor.result.ConnectionProcessorResultInit;
import lightsearch.admin.panel.exception.MessageRecipientException;
import lightsearch.admin.panel.exception.MessageSenderException;
import lightsearch.admin.panel.message.MessageRecipient;
import lightsearch.admin.panel.message.MessageSender;
import lightsearch.admin.panel.message.type.MessageConnection;
import lightsearch.admin.panel.message.type.MessageConnectionInit;

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
            ConnectionProcessorResult connProcRes = 
                    ConnectionProcessorResultInit.connectionProcessorResult(message);
            return connProcRes;
        } 
        catch (MessageSenderException |
                MessageRecipientException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
}
