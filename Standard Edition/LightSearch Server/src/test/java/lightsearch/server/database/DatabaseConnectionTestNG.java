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

import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class DatabaseConnectionTestNG {
    
    @Test
    @Parameters({"usernameDb", "passwordDb"})
    public void connection(String username, String password) {
        testBegin("DatabaseConnection", "connection()");
        
        try {            
            LightSearchServerDatabaseDTO databaseDTO = DataProviderCreator.createDataProvider(LightSearchServerDatabaseDTO.class);
            
            DatabaseConnectionCreator connectionCreator =
                    DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO, username, password);
            DatabaseConnection connection = connectionCreator.createFirebirdConnection();
            assertNotNull(connection, "DatabaseConnection is null!");
            System.out.println("DatabaseConnection: " + connection);
            System.out.println("DatabaseConnection.connection: " + connection.connection());
        } catch (DatabaseConnectionCreatorException ex) {
            catchMessage(ex);
        }
        
        testEnd("DatabaseConnection", "connection()");
    }
}
