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

import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.DatabaseConnectionCreator;
import lightsearch.server.database.DatabaseConnectionCreatorInit;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.CurrentServerDirectoryInit;
import lightsearch.server.initialization.DatabaseSettings;
import lightsearch.server.initialization.DatabaseSettingsInit;
import lightsearch.server.initialization.OsDetector;
import lightsearch.server.initialization.OsDetectorInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseConnectionTestNG {
    
    private LightSearchServerDatabaseDTO initDatabaseDTO() {
        OsDetector osDetector = OsDetectorInit.osDetector();
        CurrentServerDirectory currentDirectory = CurrentServerDirectoryInit.currentDirectory(osDetector);
        DatabaseSettings dbSettings = DatabaseSettingsInit.databaseSettings(currentDirectory);
        String dbName = dbSettings.name();
        String dbIP = dbSettings.ip();
        int dbPort = dbSettings.port();
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, dbPort);
        
        return databaseDTO;
    }
    
    @Test
    public void connection() {
        testBegin("DatabaseConnection", "connection()");
        
        try {            
            LightSearchServerDatabaseDTO databaseDTO = initDatabaseDTO();
            String username = "user";
            String password = "password";
            
            DatabaseConnectionCreator connectionCreator = DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO,
                    username, password);
            DatabaseConnection connection = connectionCreator.createFirebirdConnection();
            assertNotNull(connection, "DatabaseConnection is null!");
            System.out.println("DatabaseConnection: " + connection);
            System.out.println("DatabaseConnection.connection: " + connection.connection());
        } catch (DatabaseConnectionCreatorException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }
        
        testEnd("DatabaseConnection", "connection()");
    }
}
