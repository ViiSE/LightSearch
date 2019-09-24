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

import test.TestServer;

import static org.testng.Assert.*;

import org.testng.annotations.*;
import test.data.DataProviderCreator;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminDTOTestNG {

    private AdminDTO adminDTO;

    @BeforeTest
    @Parameters({"ip", "port"})
    public void setUpTest(String ip, int port) {
        if(!TestServer.serverOn) {
            Thread testServerTh = new Thread(new TestServer(port));
            testServerTh.start();
        }
    }

    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpMethod(String ip, int port) {
        TestServer.closeClient = false;
        TestServer.setAnswerMessage(null);
        TestServer.setSimpleMode(true);

        adminDTO = DataProviderCreator.createDataProvider(AdminDTO.class, ip, port);
        assertNotNull(adminDTO, "AdminDTO is null!");
    }
    
    @Test
    public void adminSocket() {
        testBegin("AdminDTO", "adminSocket()");

        assertNotNull(adminDTO.adminSocket(), "AdminDTO: adminSocket() is null!");
        System.out.println("AdminDTO.adminSocket(): " + adminDTO.adminSocket());
        
        testEnd("AdminDTO", "adminSocket()");
    }
    
    @Test
    public void adminDAO() {
        testBegin("AdminDTO", "adminDAO()");
        
        assertNotNull(adminDTO.adminSocket(), "AdminDTO: adminDAO() is null!");
        System.out.println("AdminDTO.adminDAO(): " + adminDTO.adminDAO());
        
        testEnd("AdminDTO", "adminDAO()");
    }
    
    @Test
    public void printer() {
        testBegin("AdminDTO", "printer()");

        assertNotNull(adminDTO.printer(), "AdminDTO: printer() is null!");
        System.out.println("AdminDTO.printer(): " + adminDTO.printer());
        
        testEnd("AdminDTO", "printer()");
    }
    
    @Test
    public void messageCommandHolder() {
        testBegin("AdminDTO", "messageCommandHolder()");
        
        assertNotNull(adminDTO.messageCommandHolder(), "AdminDTO: messageCommandHolder() is null!");
        System.out.println("AdminDTO.messageCommandHolder(): " + adminDTO.messageCommandHolder());
        
        testEnd("AdminDTO", "messageCommandHolder()");
    }
    
    @AfterClass
    public void closeMethod() {
        TestServer.setSimpleMode(false);
        TestServer.closeClient = true;
    }
}
