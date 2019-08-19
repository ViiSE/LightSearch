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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import lightsearch.server.message.result.MessageCreator;
import lightsearch.server.message.result.MessageCreatorInit;
import lightsearch.server.message.result.ResultTypeMessageEnum;
import lightsearch.server.message.result.type.MessageType;
import lightsearch.server.message.result.type.MessageTypeInit;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageCreatorTestNG {
    
    @Test
    public void createMessage() {
        testBegin("MessageCreator", "createMessage()");
        
        MessageType messageType = MessageTypeInit.messageTypeJSONAdmin();
        
        String message = "I am message";
        String name = "Admin";
        MessageCreator messageCreator = MessageCreatorInit.messageCreator(messageType);
        String doneMessage = messageCreator.createMessage(name, ResultTypeMessageEnum.TRUE, message);
        System.out.println(doneMessage);
        
        List<String> list = new ArrayList<>();
        list.add("IMEI1");
        list.add("IMEI2");
        list.add("IMEI3");
        String listMessage = messageCreator.createMessage(name, ResultTypeMessageEnum.TRUE, list);
        System.out.println(listMessage);
        
        HashMap<String, String> map = new HashMap<>();
        map.put("IMEI1", "Client1");
        map.put("IMEI2", "Client2");
        map.put("IMEI3", "Client3");
        String mapMessage = messageCreator.createMessage(name, ResultTypeMessageEnum.TRUE, map);
        System.out.println(mapMessage);
        
        String nullString = null;
        String nullMessage = messageCreator.createMessage(name, ResultTypeMessageEnum.TRUE, nullString);
        System.out.println(nullMessage);
        
        MessageCreator messageTypeClient = MessageCreatorInit.messageCreator(messageType);
        String IMEI = "123456879765";
        String clientMessage = messageCreator.createMessage(IMEI, ResultTypeMessageEnum.TRUE, messageTypeClient);
        System.out.println(clientMessage);
        
        testEnd("MessageCreator", "createMessage()");
    }
}
