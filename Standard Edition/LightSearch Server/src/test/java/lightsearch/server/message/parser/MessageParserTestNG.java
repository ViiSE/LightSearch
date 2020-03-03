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
package lightsearch.server.message.parser;

import lightsearch.server.exception.MessageParserException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class MessageParserTestNG {

    @Test
    @Parameters({"OKMessage", "failMessage"})
    public void parse(String OKMessage, String failMessage) {
        testBegin("MessageParser", "parse()");
        
        Object devInfo = null;
        MessageParser devInfoParser = MessageParserInit.messageParser();
      
        try {
            devInfo = devInfoParser.parse(OKMessage);
            System.out.println("Now isParse = true, devInfo value: " + devInfo);
            
        } catch(MessageParserException ex) {
            System.out.println("Now isParse = false, devInfo value: " + devInfo);
            catchMessage(ex);
        }
        
        try {
            devInfo = null;
            devInfo = devInfoParser.parse(failMessage);
            System.out.println("Now isParse = true, devInfo value: " + devInfo);
        } catch(MessageParserException ex) {
            System.out.println("Now isParse = false, devInfo value: " + devInfo);
            catchMessage(ex);
        }
        
        testEnd("MessageParser", "parse()");
    }
}
