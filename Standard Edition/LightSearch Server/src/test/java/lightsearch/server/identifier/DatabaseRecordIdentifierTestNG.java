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

import lightsearch.server.data.LightSearchServerDTO;
import org.testng.annotations.Test;
import test.TestUtils;
import test.data.DataProviderCreator;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseRecordIdentifierTestNG {
    
    private final List<Boolean> threadsDone = new ArrayList<>();
    
    private class Client implements Runnable {
        private final DatabaseRecordIdentifier identifier;
        private final int ID;
        
        Client(DatabaseRecordIdentifier identifier, int ID) {
            this.identifier = identifier;
            this.ID = ID;
        }
        
        @Override
        public void run() {
            for(int i = 0; i < 100; i++) {
                TestUtils.sleep(200);
                identifier.next();
                System.out.println("Thread: ID - " + ID + " identifierValue: " + identifier.databaseRecordIdentifier());
            }
            System.out.println("Thread: ID - " + ID + " DONE!");
            threadsDone.set(ID, Boolean.TRUE);
        }
        
    }
    
    @Test
    public void next() {
        testBegin("DatabaseRecordIdentifier", "next()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        DatabaseRecordIdentifierReader identifierReader =
                DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        long identifierValue = identifierReader.read();
        
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierValue);
        assertFalse(identifierValue < 0, "Identifier value is less than 0!");
        
        System.out.println("One thread test. BEGIN");
        System.out.println("Identifier value: before: " + identifier.databaseRecordIdentifier());
        identifier.next();
        System.out.println("Identifier value: after: " + identifier.databaseRecordIdentifier());
        System.out.println("One thread test. END");
        
        System.out.println("20 threads test. BEGIN");
        for(int i = 0; i < 20; i++) {
            threadsDone.add(Boolean.FALSE);
            TestUtils.sleep(100);
            new Thread(new Client(identifier, i)).start();
        }
        while(true) {
            int count = 0;
            for(int i = 0; i < 20; i++) {
                if(threadsDone.get(i))
                    count++;
            }
            if(count == 20)
                break;
        }
        System.out.println("20 threads test. END");
        
        testEnd("DatabaseRecordIdentifier", "next()");
    }
    
    @Test
    public void databaseRecordIdentifier() {
        testBegin("DatabaseRecordIdentifier", "databaseRecordIdentifier()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        DatabaseRecordIdentifierReader identifierReader =
                DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        long identifierValue = identifierReader.read();
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierValue);
        assertFalse(identifierValue < 0, "Identifier value is less than 0!");
        System.out.println("databaseRecordIdentifierValue: " + identifier.databaseRecordIdentifier());
        
        testEnd("DatabaseRecordIdentifier", "databaseRecordIdentifier()");
    }
}
