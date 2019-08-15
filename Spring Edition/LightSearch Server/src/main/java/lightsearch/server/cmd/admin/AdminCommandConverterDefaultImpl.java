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
package lightsearch.server.cmd.admin;

import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.exception.MessageParserException;
import lightsearch.server.message.parser.MessageParser;
import lightsearch.server.producer.cmd.admin.AdminCommandProducer;
import lightsearch.server.producer.message.MessageParserProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("adminCommandConverterDefault")
public class AdminCommandConverterDefaultImpl implements AdminCommandConverter {

    @Autowired AdminCommandProducer admCmdProducer;
    @Autowired MessageParserProducer msgParserProducer;

    @Override
    public AdminCommand convertToAdminCommand(String message) throws CommandConverterException {
        if(message != null) {
            MessageParser parser = msgParserProducer.getMessageParserJSONInstance();
            try {
                Object adminInfo = parser.parse(message);
                return admCmdProducer.getAdminCommandDefaultJSONInstance(adminInfo);
            } catch(MessageParserException ex) {
                throw new CommandConverterException(ex.getMessage());
            }
        }
        else throw new CommandConverterException("Message is null");
    }
}
