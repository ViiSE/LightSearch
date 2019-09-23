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
package lightsearch.admin.panel.message.parser;

import lightsearch.admin.panel.exception.MessageParserException;
import lightsearch.admin.panel.message.parser.MessageParser;
import lightsearch.admin.panel.message.parser.MessageParserInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageParserTestNG {
    
    @Test
    public void parse() {
        testBegin("MessageParser", "parse()");
        
        try {
            String rawMessage = "{\"name\":\"admin\"}";
            assertNotNull(rawMessage, "Raw message is null!");
            assertFalse(rawMessage.isEmpty(), "Raw message is null!");

            MessageParser msgParser = MessageParserInit.messageParser();
            assertNotNull(msgParser, "MessageParser is null!");

            Object msgParse = msgParser.parse(rawMessage);
            System.out.println("Parse message: " + msgParse);
        }
        catch(MessageParserException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage());
        }
        
        testEnd("MessageParser", "parse()");
    }
}
