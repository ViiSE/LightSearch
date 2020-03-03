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

import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.DatabaseConnectionCreator;
import lightsearch.server.database.DatabaseConnectionCreatorInit;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ClientDAOTestNG {
    
    @Test
    @Parameters({"IMEI"})
    public void IMEI(String IMEI) {
        testBegin("ClientDAO", "IMEI()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        assertNotNull(IMEI, "IMEI is null!");
        assertNotEquals(IMEI, "", "IMEI is null!");
        clientDAO.setIMEI(IMEI);
        System.out.println("IMEI: " + clientDAO.IMEI());
        
        testEnd("ClientDAO", "IMEI()");
    }
    
    @Test
    @Parameters({"dbIP", "dbName", "port", "username", "password"})
    public void databaseConnection(String dbIP, String dbName, int port, String username, String password) {
        testBegin("ClientDAO", "databaseConnection()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        assertNotNull(dbIP, "Database ip is null!");
        assertNotEquals(dbIP, "", "Database ip is null!");
        
        assertNotNull(dbName, "Database name is null!");
        assertNotEquals(dbName, "", "Database name is null!");
        
        assertFalse(port < 1024 || port > 65535, "Wrong port number!");
        
        LightSearchServerDatabaseDTO databaseDTO =
                LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, port);
        
        assertNotNull(username, "Username is null!");
        assertNotEquals(username, "", "Username is null!");
        
        assertNotNull(password, "Password is null!");
        assertNotEquals(password, "", "Password is null!");
        
        try {
            DatabaseConnectionCreator dbConnCreator =
                    DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO, username, password);
            DatabaseConnection dbConn = dbConnCreator.createFirebirdConnection();

            clientDAO.setDatabaseConnection(dbConn);
            System.out.println("Database connection: " + clientDAO.databaseConnection());
        } catch(DatabaseConnectionCreatorException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientDAO", "databaseConnection()");
    }
    
    @Test
    public void isFirst() {
        testBegin("ClientDAO", "isFirst()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        System.out.println("isFirst: " + clientDAO.isFirst());
        
        testEnd("ClientDAO", "isFirst()");
    }
    
    @Test
    @Parameters({"IMEI"})
    public void setIMEI(String IMEI) {
        testBegin("ClientDAO", "setIMEI()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        assertNotNull(IMEI, "IMEI is null!");
        assertNotEquals(IMEI, "", "IMEI is null!");
        System.out.println("SetIMEI: before: IMEI " + clientDAO.IMEI());
        clientDAO.setIMEI(IMEI);
        System.out.println("SetIMEI: after: IMEI " + clientDAO.IMEI());    
        
        testEnd("ClientDAO", "setIMEI()");
    }
    
    @Test
    @Parameters({"dbIP", "dbName", "port", "username", "password"})
    public void setDatabaseConnection(String dbIP, String dbName, int port, String username, String password) {
        testBegin("ClientDAO", "setDatabaseConnection()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        assertNotNull(dbIP, "Database ip is null!");
        assertNotEquals(dbIP, "", "Database ip is null!");
        
        assertNotNull(dbName, "Database name is null!");
        assertNotEquals(dbName, "", "Database name is null!");
        
        assertFalse(port < 1024 || port > 65535, "Wrong port number!");
        
        LightSearchServerDatabaseDTO databaseDTO =
                LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, port);
        
        assertNotNull(username, "Username is null!");
        assertNotEquals(username, "", "Username is null!");
        
        assertNotNull(password, "Password is null!");
        assertNotEquals(password, "", "Password is null!");
        
        try {
            DatabaseConnectionCreator dbConnCreator =
                    DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO, username, password);
            DatabaseConnection dbConn = dbConnCreator.createFirebirdConnection();

            System.out.println("SetDatabaseConnection: before: DatabaseConnection " + clientDAO.databaseConnection()); 
            clientDAO.setDatabaseConnection(dbConn);
            System.out.println("SetDatabaseConnection: after: DatabaseConnection " + clientDAO.databaseConnection());
        } catch(DatabaseConnectionCreatorException ex) {
            catchMessage(ex);
        } 
        
        testEnd("ClientDAO", "setDatabaseConnection()");
    }
    
    @Test
    @Parameters({"isFirst"})
    public void setIsFirst(boolean isFirst) {
        testBegin("ClientDAO", "setIsFirst()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        System.out.println("SetIsFirst: before: isFirst " + clientDAO.isFirst());
        clientDAO.setIsFirst(isFirst);
        System.out.println("SetIsFirst: after: isFirst " + clientDAO.isFirst());
        
        testEnd("ClientDAO", "setIsFirst()");
    }
}
