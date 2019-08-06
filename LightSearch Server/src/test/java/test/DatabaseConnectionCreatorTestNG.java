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
public class DatabaseConnectionCreatorTestNG {
    
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
    public void createFirebirdConnection() {
        testBegin("DatabaseConnectionCreator", "createFirebirdConnection()");
        
        try {
            LightSearchServerDatabaseDTO databaseDTO = initDatabaseDTO();
            String username = "user";
            String password = "password";
            
            DatabaseConnectionCreator connectionCreator = DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO,
                    username, password);
            assertNotNull(connectionCreator, "DatabaseConnectionCreator is null!");
            System.out.println("DatabaseConnectionCreator: " + connectionCreator);
            
            DatabaseConnection connection = connectionCreator.createFirebirdConnection();
            System.out.println("DatabaseConnection: " + connection);
            System.out.println("DatabaseConnection.connection: " + connection.connection());
        } catch (DatabaseConnectionCreatorException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }
        
        testEnd("DatabaseConnectionCreator", "createFirebirdConnection()");
    }
}
