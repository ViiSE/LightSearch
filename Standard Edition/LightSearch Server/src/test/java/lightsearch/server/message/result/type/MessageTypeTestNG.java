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
package lightsearch.server.message.result.type;

import lightsearch.server.message.result.ResultTypeMessageEnum;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class MessageTypeTestNG {
    
    @Test
    @Parameters({"message", "name", "IMEI"})
    public void createFormattedMessage(String message, String name, String IMEI) {
        testBegin("MessageType", "createFormattedMessage()");

        String isDone = ResultTypeMessageEnum.TRUE.stringValue();

        MessageType messageType = MessageTypeInit.messageTypeJSONAdmin();
        String doneMessage = messageType.createFormattedMessage(name, isDone, message);
        System.out.println(doneMessage);
        
        List<String> list = new ArrayList<>();
        list.add("IMEI1");
        list.add("IMEI2");
        list.add("IMEI3");
        
        MessageType messageTypeList = MessageTypeInit.messageTypeJSONAdmin();
        String listMessage = messageTypeList.createFormattedMessage(name, isDone, list);
        System.out.println(listMessage);
        
        
        HashMap<String, String> map = new HashMap<>();
        map.put("IMEI1", "Client1");
        map.put("IMEI2", "Client2");
        map.put("IMEI3", "Client3");
        
        MessageType messageTypeMap = MessageTypeInit.messageTypeJSONAdmin();
        String mapMessage = messageTypeMap.createFormattedMessage(name, isDone, map);
        System.out.println(mapMessage);
        
        String nullString = null;
        
        MessageType messageTypeNull = MessageTypeInit.messageTypeJSONAdmin();
        String nullMessage = messageTypeNull.createFormattedMessage(name, isDone, nullString);
        System.out.println(nullMessage);
        
        MessageType messageTypeClient = MessageTypeInit.messageTypeJSONClientSuccess();
        String clientMessage = messageTypeClient.createFormattedMessage(IMEI, isDone, message);
        System.out.println(clientMessage);
        
        testEnd("MessageType", "createFormattedMessage()");
    }
}
