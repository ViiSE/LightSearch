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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lightsearch.server.data.LightSearchServerDTOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.data.LightSearchServerSettingsDAOInit;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;

import static org.testng.Assert.*;
import org.testng.annotations.Test;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.LightSearchServerSettingsDAO;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseRecordIdentifierTestNG {
    
    private DatabaseRecordIdentifierReader init() {
        Map<String, String> admins = new HashMap<>();
        Map<String, String> clients = new HashMap<>();
        List<String> blacklist = new ArrayList<>();
        
        String dbIP = "127.0.0.1";
        String dbName = "example_db";
        int dbPort = 8080;
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, dbPort);
        
        int serverReboot = 0;
        int clientTimeout = 0;
        LightSearchServerSettingsDAO settingsDTO = LightSearchServerSettingsDAOInit.settingsDAO(serverReboot, clientTimeout);
        
        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentServerDirectory currentDirectory = CurrentServerDirectoryInit.currentDirectory(osDetector);
        
        LightSearchServerDTO serverDTO = LightSearchServerDTOInit.LightSearchServerDTO(admins, clients, 
                blacklist, databaseDTO, 0, settingsDTO, currentDirectory.currentDirectory());
        DatabaseRecordIdentifierReader identifierReader = DatabaseRecordIdentifierReaderInit.databaseRecordIdentifierReader(serverDTO);
        
        return identifierReader;
    }
    
    private final List<Boolean> threadsDone = new ArrayList<>();
    
    public class Client implements Runnable {
        private final DatabaseRecordIdentifier identifier;
        private final int ID;
        
        public Client(DatabaseRecordIdentifier identifier, int ID) {
            this.identifier = identifier;
            this.ID = ID;
        }
        
        @Override
        public void run() {
            for(int i = 0; i < 100; i++) {
                try { Thread.sleep(200); } catch(InterruptedException ignore) {}
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
        
        DatabaseRecordIdentifierReader identifierReader = init();
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
            try { Thread.sleep(100); } catch(InterruptedException ignore) {}
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
        
        DatabaseRecordIdentifierReader identifierReader = init();
        long identifierValue = identifierReader.read();
        DatabaseRecordIdentifier identifier = DatabaseRecordIdentifierInit.databaseRecordIdentifier(identifierValue);
        assertFalse(identifierValue < 0, "Identifier value is less than 0!");
        System.out.println("databaseRecordIdentifierValue: " + identifier.databaseRecordIdentifier());
        
        testEnd("DatabaseRecordIdentifier", "databaseRecordIdentifier()");
    }
}
