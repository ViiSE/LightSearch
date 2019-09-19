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
package lightsearch.server.database;

import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.exception.DatabaseReaderException;
import lightsearch.server.identifier.DatabaseRecordIdentifier;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DatabaseReaderTestNG {
    
    @Test
    @Parameters({"usernameDb", "passwordDb"})
    public void read(String username, String password) {
        testBegin("DatabaseReader", "read()");
        
        try {    
            DatabaseConnection databaseConnection =
                    DataProviderCreator.createDataProvider(DatabaseConnection.class, username, password);
            assertNotNull(databaseConnection, "Database connection is null!");
            
            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");
            
            DatabaseRecordIdentifier identifier =
                    DataProviderCreator.createDataProvider(DatabaseRecordIdentifier.class, serverDTO);
            assertNotNull(identifier, "Identifier is null!");
            
            DatabaseReader reader = DatabaseReaderInit.databaseReader(databaseConnection, identifier.next());
            
            String result = reader.read();
            
            System.out.println("Reader.read(): RESULT: " + result);
            
        } catch (DatabaseReaderException ex) {
            catchMessage(ex);
        }
        
        testEnd("DatabaseReader", "read()");;
    }
}
