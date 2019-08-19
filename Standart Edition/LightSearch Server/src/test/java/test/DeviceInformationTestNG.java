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
package test;

import lightsearch.server.message.parser.MessageParser;
import lightsearch.server.message.parser.MessageParserInit;
import lightsearch.server.exception.MessageParserException;
import lightsearch.server.message.result.DeviceInformation;
import lightsearch.server.message.result.DeviceInformationInit;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DeviceInformationTestNG {
    
    @Test
    public void identifier() {
        testBegin("DeviceInformation", "identifier()");
        
        String message = "{\"IMEI\":\"745689574123658\"}";
        
        MessageParser devInfoParser = MessageParserInit.messageParser();
        try {
            Object devInfo = devInfoParser.parse(message);

            DeviceInformation deviceInformation = DeviceInformationInit.deviceInformation(devInfo);
            String identifier = deviceInformation.identifier();
            System.out.print(identifier);
        } catch(MessageParserException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("DeviceInformation", "identifier()");
    }
}
