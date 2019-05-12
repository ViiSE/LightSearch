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

import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.ClientDAOInit;
import lightsearch.server.data.LightSearchServerDatabaseDTO;
import lightsearch.server.data.LightSearchServerDatabaseDTOInit;
import lightsearch.server.database.DatabaseConnection;
import lightsearch.server.database.DatabaseConnectionCreator;
import lightsearch.server.database.DatabaseConnectionCreatorInit;
import lightsearch.server.exception.DatabaseConnectionCreatorException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ClientDAOTestNG {
    
    @Test
    public void IMEI() {
        testBegin("ClientDAO", "IMEI()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        String IMEI = "123456789123456";
        assertNotNull(IMEI, "IMEI is null!");
        assertFalse(IMEI.equals(""), "IMEI is null!");
        clientDAO.setIMEI(IMEI);
        System.out.println("IMEI: " + clientDAO.IMEI());
        
        testEnd("ClientDAO", "IMEI()");
    }
    
    @Test
    public void databaseConnection() {
        testBegin("ClientDAO", "databaseConnection()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        String dbIP = "127.0.0.1";
        assertNotNull(dbIP, "Database ip is null!");
        assertFalse(dbIP.equals(""), "Database ip is null!");
        
        String dbName = "database";
        assertNotNull(dbName, "Database name is null!");
        assertFalse(dbName.equals(""), "Database name is null!");
        
        int port = 8080;
        assertFalse(port < 1024 && port > 65535, "Wrong port number!");
        
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, 8080);
        
        String username = "username_db";
        assertNotNull(username, "Username is null!");
        assertFalse(username.equals(""), "Username is null!");
        
        String password = "pass1234!";
        assertNotNull(password, "Password is null!");
        assertFalse(password.equals(""), "Password is null!");
        
        try {
            DatabaseConnectionCreator dbConnCreator = DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO, username, password);
            DatabaseConnection dbConn = dbConnCreator.createFirebirdConnection();

            clientDAO.setDatabaseConnection(dbConn);
            System.out.println("Database connection: " + clientDAO.databaseConnection());
        } catch(DatabaseConnectionCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage() + ", messageRU: " + ex.getMessageRU());
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
    public void setIMEI() {
        testBegin("ClientDAO", "setIMEI()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        String IMEI = "123456789123456";
        assertNotNull(IMEI, "IMEI is null!");
        assertFalse(IMEI.equals(""), "IMEI is null!");
        System.out.println("SetIMEI: before: IMEI " + clientDAO.IMEI());
        clientDAO.setIMEI(IMEI);
        System.out.println("SetIMEI: after: IMEI " + clientDAO.IMEI());    
        
        testEnd("ClientDAO", "setIMEI()");
    }
    
    @Test
    public void setDatabaseConnection() {
        testBegin("ClientDAO", "setDatabaseConnection()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        String dbIP = "127.0.0.1";
        assertNotNull(dbIP, "Database ip is null!");
        assertFalse(dbIP.equals(""), "Database ip is null!");
        
        String dbName = "database";
        assertNotNull(dbName, "Database name is null!");
        assertFalse(dbName.equals(""), "Database name is null!");
        
        int port = 8080;
        assertFalse(port < 1024 && port > 65535, "Wrong port number!");
        
        LightSearchServerDatabaseDTO databaseDTO = LightSearchServerDatabaseDTOInit.lightSearchServerDatabaseDTO(dbIP, dbName, 8080);
        
        String username = "username_db";
        assertNotNull(username, "Username is null!");
        assertFalse(username.equals(""), "Username is null!");
        
        String password = "pass1234!";
        assertNotNull(password, "Password is null!");
        assertFalse(password.equals(""), "Password is null!");
        
        try {
            DatabaseConnectionCreator dbConnCreator = DatabaseConnectionCreatorInit.databaseConnectionCreator(databaseDTO, username, password);
            DatabaseConnection dbConn = dbConnCreator.createFirebirdConnection();

            System.out.println("SetDatabaseConnection: before: DatabaseConnection " + clientDAO.databaseConnection()); 
            clientDAO.setDatabaseConnection(dbConn);
            System.out.println("SetDatabaseConnection: after: DatabaseConnection " + clientDAO.databaseConnection());
        } catch(DatabaseConnectionCreatorException ex) {
            System.out.println("CATCH! Message: " + ex.getMessage() + ", MessageRU: " + ex.getMessageRU());
        } 
        
        testEnd("ClientDAO", "setDatabaseConnection()");
    }
    
    @Test
    public void setIsFirst() {
        testBegin("ClientDAO", "setIsFirst()");
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        boolean isFirst = false;
        System.out.println("SetIsFirst: before: isFirst " + clientDAO.isFirst());
        clientDAO.setIsFirst(isFirst);
        System.out.println("SetIsFirst: after: isFirst " + clientDAO.isFirst());
        
        testEnd("ClientDAO", "setIsFirst()");
    }
}
