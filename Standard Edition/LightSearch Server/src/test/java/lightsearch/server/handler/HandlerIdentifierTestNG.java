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
package lightsearch.server.handler;

import lightsearch.server.identifier.HandlerIdentifier;
import lightsearch.server.identifier.HandlerIdentifierInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class HandlerIdentifierTestNG {
    
    @Test
    @Parameters({"id"})
    public void next(String id) {
        testBegin("HandlerIdentifier", "next()");
        
        HandlerIdentifier handlerIdentifier = HandlerIdentifierInit.handlerIdentifier();
        System.out.println(id);
        String newId1 =  id + handlerIdentifier.next();
        System.out.println(newId1);
        String newId2 =  id + handlerIdentifier.next();
        System.out.println(newId2);
        String newId3 =  id + handlerIdentifier.next();
        System.out.println(newId3);
        
        HandlerIdentifier newHandlerIdentifier = HandlerIdentifierInit.handlerIdentifier();
        
        String newNewId = id + newHandlerIdentifier.next();
        System.out.println(newNewId);

        assertNotEquals(newId1, newNewId, "Number of id is equals!");
        assertNotEquals(newId2, newNewId, "Number of id is equals!");
        assertNotEquals(newId3, newNewId, "Number of id is equals!");
        
        testEnd("HandlerIdentifier", "next()");
    }
}
