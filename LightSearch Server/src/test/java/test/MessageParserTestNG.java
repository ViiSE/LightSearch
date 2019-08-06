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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import lightsearch.server.message.parser.MessageParser;
import lightsearch.server.message.parser.MessageParserInit;
import lightsearch.server.exception.MessageParserException;
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
        
        String message1 = "745689574123658";
        String message2 = "{\"IMEI\":\"745689574123658\"}";
        Object devInfo = null;
        MessageParser devInfoParser = MessageParserInit.messageParser();
      
        try {
            devInfo = devInfoParser.parse(message1);
            System.out.println("Now isParse = true, devInfo value: " + devInfo);
            
        } catch(MessageParserException ex) {
            System.out.println("Now isParse = false, devInfo value: " + devInfo);
            System.out.println("Error message: " + ex.getMessage());
        }
        
        try {
            devInfo = devInfoParser.parse(message2);
            System.out.println("Now isParse = true, devInfo value: " + devInfo);
        } catch(MessageParserException ex) {
            System.out.println("Now isParse = false, devInfo value: " + devInfo);
            System.out.println("Error message: " + ex.getMessage());
        }
        
        testEnd("MessageParser", "parse()");
    }
}
