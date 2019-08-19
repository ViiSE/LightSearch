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

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandConverter;
import lightsearch.server.cmd.admin.AdminCommandConverterInit;
import lightsearch.server.exception.CommandConverterException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminCommandTestNG {
    
    @Test
    public void name() {
        testBegin("AdminCommand", "name()");
        
        try {
            String message = "{"
                            + "\"name\":\"admin\","
                            + "\"command\":\"restart\""
                        + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd;
            admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertFalse(admCmd.name().equals(""), "Admin name is null!");
            System.out.println("------------------------------------");
            System.out.println("Name: " + admCmd.name());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "name()");
    }
    
    @Test
    public void command() {
        testBegin("AdminCommand", "command()");
        
        try {
            String message = "{"
                                + "\"name\":\"admin\","
                                + "\"command\":\"restart\""
                            + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd;
            admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.command(), "Command is null!");
            assertFalse(admCmd.command().equals(""), "Command is null!");
            System.out.println("------------------------------------");
            System.out.println("Command: " + admCmd.command());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "command()");
    }
    
    @Test
    public void serverTime() {
        testBegin("AdminCommand", "serverTime()");
        
        try {
            String message = "{"
                            + "\"name\":\"admin\","
                            + "\"command\":\"toutServer\","
                            + "\"time\":\"3\""
                        + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd;
            admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.serverTime(), "Server time is null!");
            assertFalse(admCmd.serverTime().equals(""), "Server time is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("serverTime: " + admCmd.serverTime());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "serverTime()");
    }
    
    @Test
    public void clientTimeout() {
        testBegin("AdminCommand", "clientTimeout()");
        
        try {
            String message = "{"
                            + "\"name\":\"admin\","
                            +   "\"command\":\"toutClient\","
                            +   "\"time\":\"30000\""
                            + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd;
            admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.clientTimeout(), "Client timeout is null!");
            assertFalse(admCmd.clientTimeout().equals(""), "Client timeout is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("client timeout: " + admCmd.clientTimeout());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
             System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "clientTimeout()");
    }
    
    @Test
    public void IMEI() {
        testBegin("AdminCommand", "IMEI()");
        
        try {
            String message = "{"
                                + "\"name\":\"admin\","
                                + "\"command\":\"addBlacklist\","
                                + "\"IMEI\":\"123456789123456\""
                            + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd;
            admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.IMEI(), "IMEI is null!");
            assertFalse(admCmd.IMEI().equals(""), "IMEI is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("IMEI: " + admCmd.IMEI());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "IMEI()");
    }
    
    @Test
    public void password() {
        testBegin("AdminCommand", "password()");
        
        try {
            String message = "{"
                                + "\"name\":\"admin\","
                                + "\"command\":\"createAdmin\","
                                + "\"adminName\":\"newAdmin\","
                                + "\"password\":\"12345\""
                            + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.password(), "password is null!");
            assertFalse(admCmd.password().equals(""), "password is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("password: " + admCmd.password());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "password()");
    }
    
    @Test
    public void ip() {
        testBegin("AdminCommand", "ip()");
        
        try {
            String message = "{"
                                + "\"name\":\"admin\","
                                + "\"command\":\"chDb\","
                                + "\"dbName\":\"newDbName\","
                                + "\"ip\":\"127.0.0.2\","
                                + "\"port\":\"23456\""
                            + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.ip(), "Database IP is null!");
            assertFalse(admCmd.ip().equals(""), "Database IP is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("IP: " + admCmd.ip());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "ip()");
    }
    
    @Test
    public void port() {
        testBegin("AdminCommand", "port()");
        
        try {
            String message = "{"
                                + "\"name\":\"admin\","
                                + "\"command\":\"chDb\","
                                + "\"dbName\":\"newDbName\","
                                + "\"ip\":\"127.0.0.2\","
                                + "\"port\":\"23456\""
                            + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.port(), "Database port is null!");
            assertFalse(admCmd.port().equals(""), "Database port is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("port: " + admCmd.port());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "port()");
    }
    
    @Test
    public void dbName() {
        testBegin("AdminCommand", "dbName()");
        
        try {
            String message = "{"
                                + "\"name\":\"admin\","
                                + "\"command\":\"chDb\","
                                + "\"dbName\":\"newDbName\","
                                + "\"ip\":\"127.0.0.2\","
                                + "\"port\":\"23456\""
                            + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.dbName(), "Database name is null!");
            assertFalse(admCmd.dbName().equals(""), "Database name is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("dbName: " + admCmd.dbName());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "dbName()");
    }
    
    @Test
    public void adminName() {
        testBegin("AdminCommand", "adminName()");
        
        try {
            String message = "{"
                                + "\"name\":\"admin\","
                                + "\"command\":\"createAdmin\","
                                + "\"adminName\":\"newAdmin\","
                                + "\"password\":\"12345\""
                            + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.adminName(), "New admin name is null!");
            assertFalse(admCmd.adminName().equals(""), "New admin name is null!");
            System.out.println("------------------------------------");
            System.out.println("command: " + admCmd.command());
            System.out.println("New admin name: " + admCmd.adminName());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommand", "adminName()");
    }
}
