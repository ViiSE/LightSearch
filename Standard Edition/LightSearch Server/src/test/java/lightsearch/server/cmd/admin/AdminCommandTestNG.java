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
package lightsearch.server.cmd.admin;

import lightsearch.server.exception.CommandConverterException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class AdminCommandTestNG {
    
    @Test
    @Parameters({"bigMessage"})
    public void name(String message) {
        testBegin("AdminCommand", "name()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertNotEquals(admCmd.name(), "", "Admin name is null!");
            System.out.println("------------------------------------");
            System.out.println("Name: " + admCmd.name());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "name()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void command(String message) {
        testBegin("AdminCommand", "command()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.command(), "Command is null!");
            assertNotEquals(admCmd.command(), "", "Command is null!");
            System.out.println("------------------------------------");
            System.out.println("Command: " + admCmd.command());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "command()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void serverTime(String message) {
        testBegin("AdminCommand", "serverTime()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.serverTime(), "Server time is null!");
            assertNotEquals(admCmd.serverTime(), "", "Server time is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("serverTime: " + admCmd.serverTime());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "serverTime()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void clientTimeout(String message) {
        testBegin("AdminCommand", "clientTimeout()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.clientTimeout(), "Client timeout is null!");
            assertNotEquals(admCmd.clientTimeout(), "", "Client timeout is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("client timeout: " + admCmd.clientTimeout());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
             catchMessage(ex);
        }
        
        testEnd("AdminCommand", "clientTimeout()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void IMEI(String message) {
        testBegin("AdminCommand", "IMEI()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.IMEI(), "IMEI is null!");
            assertNotEquals(admCmd.IMEI(), "", "IMEI is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("IMEI: " + admCmd.IMEI());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "IMEI()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void password(String message) {
        testBegin("AdminCommand", "password()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.password(), "password is null!");
            assertNotEquals(admCmd.password(), "", "password is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("password: " + admCmd.password());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "password()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void ip(String message) {
        testBegin("AdminCommand", "ip()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.ip(), "Database IP is null!");
            assertNotEquals(admCmd.ip(), "", "Database IP is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("IP: " + admCmd.ip());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "ip()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void port(String message) {
        testBegin("AdminCommand", "port()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.port(), "Database port is null!");
            assertNotEquals(admCmd.port(), "", "Database port is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("port: " + admCmd.port());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "port()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void dbName(String message) {
        testBegin("AdminCommand", "dbName()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.dbName(), "Database name is null!");
            assertNotEquals(admCmd.dbName(), "", "Database name is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("dbName: " + admCmd.dbName());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "dbName()");
    }
    
    @Test
    @Parameters({"bigMessage"})
    public void adminName(String message) {
        testBegin("AdminCommand", "adminName()");
        
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.adminName(), "New admin name is null!");
            assertNotEquals(admCmd.adminName(), "", "New admin name is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("New admin name: " + admCmd.adminName());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("AdminCommand", "adminName()");
    }
}
