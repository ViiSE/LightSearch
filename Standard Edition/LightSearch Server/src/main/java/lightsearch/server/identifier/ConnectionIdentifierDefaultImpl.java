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
package lightsearch.server.identifier;

import java.net.Socket;
import lightsearch.server.data.stream.DataStream;
import lightsearch.server.data.stream.DataStreamCreator;
import lightsearch.server.data.stream.DataStreamCreatorInit;
import lightsearch.server.data.stream.DataStreamInit;
import lightsearch.server.exception.DataStreamCreatorException;
import lightsearch.server.exception.ConnectionIdentifierException;
import lightsearch.server.message.MessageRecipient;
import lightsearch.server.message.MessageRecipientInit;
import lightsearch.server.message.MessageSender;
import lightsearch.server.message.MessageSenderInit;
import lightsearch.server.exception.MessageRecipientException;
import lightsearch.server.exception.MessageSenderException;
import lightsearch.server.message.parser.MessageParserInit;
import lightsearch.server.message.result.DeviceInformation;
import lightsearch.server.message.result.DeviceInformationInit;
import lightsearch.server.message.parser.MessageParser;
import lightsearch.server.exception.MessageParserException;

/**
 * Реализация интерфейса {@link lightsearch.server.identifier.ConnectionIdentifier} по умолчанию.
 * <p>
 * Идентификация соединения начинается с создания {@code DataStream} клиентского сокета, затем создаются
 * {@code MessageRecipient} и {@code MessageSender}, после этого сообщение, пришедшее от клиента, парсится при помощи
 * {@code MessageParser}. Если идентификация прошла успешно, то LightSearch Server отправляет клиенту сообщение "OK", и
 * создает соответствующий обработчик.
 * @author ViiSE
 * @see lightsearch.server.data.stream.DataStream
 * @see lightsearch.server.message.MessageRecipient
 * @see lightsearch.server.message.MessageSender
 * @see lightsearch.server.message.parser.MessageParser
 * @see lightsearch.server.listener.LightSearchServerListenerSocketDefaultImpl
 * @since 1.0.0
 */
public class ConnectionIdentifierDefaultImpl implements ConnectionIdentifier {
    
    private final String OK = IdentifierEnum.OK.stringValue();
    
    @Override
    public ConnectionIdentifierResult identifyConnection(Socket clientSocket) throws ConnectionIdentifierException {
        DataStreamCreator dataStreamCreator = DataStreamCreatorInit.dataStreamCreator(clientSocket);
        try {
            dataStreamCreator.createDataStream();
            DataStream dataStream = DataStreamInit.dataStream(dataStreamCreator);

            MessageRecipient messageRecipient = MessageRecipientInit.messageRecipient(dataStream.dataInputStream());
            String devInfoRaw = messageRecipient.acceptMessage();

            MessageParser devInfoParser = MessageParserInit.messageParser();
            Object devInfo = devInfoParser.parse(devInfoRaw);

            DeviceInformation deviceInformation = DeviceInformationInit.deviceInformation(devInfo);
            String identifier = deviceInformation.identifier();

            MessageSender messageSender = MessageSenderInit.messageSender(dataStream.dataOutputStream());
            messageSender.sendMessage(OK);

            return new ConnectionIdentifierResult(clientSocket, identifier, dataStream);
        } catch(DataStreamCreatorException | 
                MessageRecipientException  | 
                MessageParserException     | 
                MessageSenderException     |
                NullPointerException ex) {
            throw new ConnectionIdentifierException(ex.getMessage());
        }
    }
}
