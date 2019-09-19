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
package lightsearch.server.data;

import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class LightSearchListenerDTOTestNG {
    
    @Test
    public void checker() {
        testBegin("LightSearchListenerDTO", "checker()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        assertNotNull(listenerDTO, "ListenerDTO is null!");
        System.out.println("LightSearchListenerDTO.checker(): " + listenerDTO.checker());
        
        testEnd("LightSearchListenerDTO", "checker()");
    }
    
    @Test
    public void timerRebootId() {
        testBegin("LightSearchListenerDTO", "timerRebootId()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        assertNotNull(listenerDTO, "ListenerDTO is null!");
        System.out.println("LightSearchListenerDTO.timerRebootId(): " + listenerDTO.timerRebootId());
                
        testEnd("LightSearchListenerDTO", "timerRebootId()");
    }
    
    @Test
    public void currentDateTime() {
        testBegin("LightSearchListenerDTO", "currentDateTime()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        assertNotNull(listenerDTO, "ListenerDTO is null!");
        System.out.println("LightSearchListenerDTO.currentDateTime(): " + listenerDTO.currentDateTime());
        
        testEnd("LightSearchListenerDTO", "currentDateTime()");
    }
    
    @Test
    public void databaseRecordIdentifier() {
        testBegin("LightSearchListenerDTO", "databaseRecordIdentifier()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        assertNotNull(listenerDTO, "ListenerDTO is null!");
        System.out.println("LightSearchListenerDTO.databaseRecordIdentifier(): " + listenerDTO.databaseRecordIdentifier());
                
        testEnd("LightSearchListenerDTO", "databaseRecordIdentifier()");
    }
    
    @Test
    public void databaseRecordIdentifierWriter() {
        testBegin("LightSearchListenerDTO", "databaseRecordIdentifierWriter()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        assertNotNull(listenerDTO, "ListenerDTO is null!");
        System.out.println("LightSearchListenerDTO.databaseRecordIdentifierWriter(): " + listenerDTO.databaseRecordIdentifierWriter());
        
        testEnd("LightSearchListenerDTO", "databaseRecordIdentifierWriter()");
    }
    
    @Test
    public void threadManager() {
        testBegin("LightSearchListenerDTO", "threadManager()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
        assertNotNull(listenerDTO, "ListenerDTO is null!");
        System.out.println("LightSearchListenerDTO.threadManager(): " + listenerDTO.threadManager());
        
        testEnd("LightSearchListenerDTO", "threadManager()");        
    }
}
