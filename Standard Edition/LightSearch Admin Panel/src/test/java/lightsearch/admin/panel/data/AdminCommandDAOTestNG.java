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
package lightsearch.admin.panel.data;

import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.AdminCommandDAOInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminCommandDAOTestNG {
    
    @Test
    public void name() {
        testBegin("AdminCommandDAO", "name()");
        
        String name = "name";
        assertNotNull(name, "Name is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setName(name);
        
        assertNotNull(admCmdDAO.name(), "AdminCommandDAO: name is null!");
        System.out.println("Name: " + admCmdDAO.name());
        
        testEnd("AdminCommandDAO", "name()");
    }
    
    @Test
    public void serverTime() {
        testBegin("AdminCommandDAO", "serverTime()");
        
        String serverTime = "8";
        assertNotNull(serverTime, "serverTime is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setServerTimeout(serverTime);
        
        assertNotNull(admCmdDAO.serverTimeout(), "AdminCommandDAO: serverTime is null!");
        System.out.println("serverTime: " + admCmdDAO.serverTimeout());
        
        testEnd("AdminCommandDAO", "serverTime()");
    }
    
    @Test
    public void clientTimeout() {
        testBegin("AdminCommandDAO", "clientTimeout()");
        
        String clientTimeout = "50000";
        assertNotNull(clientTimeout, "clientTimeout is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setClientTimeout(clientTimeout);
        
        assertNotNull(admCmdDAO.clientTimeout(), "AdminCommandDAO: clientTimeout is null!");
        System.out.println("clientTimeout: " + admCmdDAO.clientTimeout());
        
        testEnd("AdminCommandDAO", "clientTimeout()");
    }
    
    @Test
    public void IMEI() {
        testBegin("AdminCommandDAO", "IMEI()");
        
        String IMEI = "123456789123456";
        assertNotNull(IMEI, "IMEI is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setIMEI(IMEI);
        
        assertNotNull(admCmdDAO.IMEI(), "AdminCommandDAO: IMEI is null!");
        System.out.println("IMEI: " + admCmdDAO.IMEI());
        
        testEnd("AdminCommandDAO", "IMEI()");
    }
    
    @Test
    public void adminName() {
        testBegin("AdminCommandDAO", "adminName()");
        
        String adminName = "adminName";
        assertNotNull(adminName, "adminName is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setAdminName(adminName);
        
        assertNotNull(admCmdDAO.adminName(), "AdminCommandDAO: adminName is null!");
        System.out.println("adminName: " + admCmdDAO.adminName());
        
        testEnd("AdminCommandDAO", "adminName()");
    }
    
    @Test
    public void password() {
        testBegin("AdminCommandDAO", "password()");
        
        String password = "password";
        assertNotNull(password, "password is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setPassword(password);
        
        assertNotNull(admCmdDAO.password(), "AdminCommandDAO: password is null!");
        System.out.println("password: " + admCmdDAO.password());
        
        testEnd("AdminCommandDAO", "password()");
    }
    
    @Test
    public void ip() {
        testBegin("AdminCommandDAO", "ip()");
        
        String ip = "ip";
        assertNotNull(ip, "ip is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setIp(ip);
        
        assertNotNull(admCmdDAO.ip(), "AdminCommandDAO: ip is null!");
        System.out.println("ip: " + admCmdDAO.ip());
        
        testEnd("AdminCommandDAO", "ip()");
    }
    
    @Test
    public void port() {
        testBegin("AdminCommandDAO", "port()");
        
        String port = "port";
        assertNotNull(port, "port is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setPort(port);
        
        assertNotNull(admCmdDAO.port(), "AdminCommandDAO: port is null!");
        System.out.println("port: " + admCmdDAO.port());
        
        testEnd("AdminCommandDAO", "port()");
    }
    
    @Test
    public void dbName() {
        testBegin("AdminCommandDAO", "dbName()");
        
        String dbName = "dbName";
        assertNotNull(dbName, "dbName is null!");
        
        AdminCommandDAO admCmdDAO = AdminCommandDAOInit.adminCommandDAO();
        assertNotNull(admCmdDAO, "AdminCommandDAO is null!");
        
        admCmdDAO.setDbName(dbName);
        
        assertNotNull(admCmdDAO.dbName(), "AdminCommandDAO: dbName is null!");
        System.out.println("dbName: " + admCmdDAO.dbName());
        
        testEnd("AdminCommandDAO", "dbName()");
    }
}
