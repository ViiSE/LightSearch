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

import lightsearch.server.data.stream.DataStream;
import lightsearch.server.exception.*;
import lightsearch.server.message.MessageRecipient;
import lightsearch.server.message.MessageSender;
import lightsearch.server.message.parser.MessageParser;
import lightsearch.server.message.result.DeviceInformation;
import lightsearch.server.producer.data.stream.DataStreamProducer;
import lightsearch.server.producer.identifier.ConnectionIdentifierResultProducer;
import lightsearch.server.producer.message.DeviceInformationProducer;
import lightsearch.server.producer.message.MessageParserProducer;
import lightsearch.server.producer.message.MessageRecipientProducer;
import lightsearch.server.producer.message.MessageSenderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.Socket;

/**
 *
 * @author ViiSE
 */
@Component("connectionIdentifierDefault")
public class ConnectionIdentifierDefaultImpl implements ConnectionIdentifier {

    @Autowired private DataStreamProducer dsProducer;
    @Autowired private MessageRecipientProducer msgRecipientProducer;
    @Autowired private MessageSenderProducer msgSenderProducer;
    @Autowired private MessageParserProducer msgParserProducer;
    @Autowired private DeviceInformationProducer devInfoProducer;
    @Autowired private ConnectionIdentifierResultProducer connIdentResProducer;

    private final String OK = IdentifierEnum.OK.stringValue();
    
    @Override
    public ConnectionIdentifierResult identifyConnection(Socket clientSocket) throws ConnectionIdentifierException {
        try {
            DataStream dataStream = dsProducer.getDataStreamDefaultInstance(clientSocket);

            MessageRecipient messageRecipient = msgRecipientProducer.getMessageRecipientProducerDebugInstance(dataStream.dataInputStream());
            String devInfoRaw = messageRecipient.acceptMessage();

            MessageParser devInfoParser = msgParserProducer.getMessageParserJSONInstance();
            Object devInfo = devInfoParser.parse(devInfoRaw);

            DeviceInformation deviceInformation = devInfoProducer.getDeviceInformationJSONInstance(devInfo);
            String identifier = deviceInformation.identifier();

            MessageSender messageSender = msgSenderProducer.getMessageSenderDefaultInstance(dataStream.dataOutputStream());
            messageSender.sendMessage(OK);

            return connIdentResProducer.getConnectionIdentifierResultDefaultInstance(clientSocket, identifier, dataStream);
        } catch(DataStreamCreatorException | 
                MessageRecipientException  | 
                MessageParserException     | 
                MessageSenderException     |
                NullPointerException ex) {
            throw new ConnectionIdentifierException(ex.getMessage());
        }
    }
}
