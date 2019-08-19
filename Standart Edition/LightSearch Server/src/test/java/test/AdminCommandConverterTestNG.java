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
public class AdminCommandConverterTestNG {
    
    @Test
    public void convertToAdminCommand() {
        testBegin("AdminCommandConverter", "convertToAdminCommand()");
        
        String message = "{"
                            + "\"name\":\"admin\","
                            + "\"command\":\"someCommand\","
                            + "\"time\":\"30000\","
                            + "\"IMEI\":\"123456789123456\","
                            + "\"password\":\"pass12345!#\","
                            + "\"ip\":\"127.0.0.1\","
                            + "\"port\":\"12345\","
                            + "\"dbName\":\"exampleDatabase\","
                            + "\"adminName\":\"newAdminName\""
                        + "}";
        AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
        AdminCommand admCmd;
        try {
            admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
        
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertFalse(admCmd.name().equals(""), "Admin name is null!");

            assertNotNull(admCmd.command(), "Command is null!");
            assertFalse(admCmd.command().equals(""), "Command is null!");

            assertNotNull(admCmd.serverTime(), "Server time is null!");
            assertFalse(admCmd.serverTime().equals(""), "Server time is null!");

            assertNotNull(admCmd.clientTimeout(), "Client timeout is null!");
            assertFalse(admCmd.clientTimeout().equals(""), "Client timeout is null!");

            assertNotNull(admCmd.IMEI(), "IMEI is null!");
            assertFalse(admCmd.IMEI().equals(""), "IMEI is null!");

            assertNotNull(admCmd.password(), "Password is null!");
            assertFalse(admCmd.password().equals(""), "Password is null!");

            assertNotNull(admCmd.ip(), "Database IP is null!");
            assertFalse(admCmd.ip().equals(""), "Database IP is null!");

            assertNotNull(admCmd.port(), "Database port is null!");
            assertFalse(admCmd.port().equals(""), "Database port is null!");

            assertNotNull(admCmd.dbName(), "Database name is null!");
            assertFalse(admCmd.dbName().equals(""), "Database name is null!");

            assertNotNull(admCmd.adminName(), "New admin name is null!");
            assertFalse(admCmd.adminName().equals(""), "New admin name is null!");

            System.out.println("Name: " + admCmd.name());
            System.out.println("Command: " + admCmd.command());
            System.out.println("Server time: " + admCmd.serverTime());
            System.out.println("Client timeout: " + admCmd.clientTimeout());
            System.out.println("IMEI: " + admCmd.IMEI());
            System.out.println("Password: " + admCmd.password());
            System.out.println("Database ip: " + admCmd.ip());
            System.out.println("Database port: " + admCmd.port());
            System.out.println("Database name: " + admCmd.dbName());
            System.out.println("AdminName: " + admCmd.adminName());
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("AdminCommandConverter", "convertToAdminCommand()");
    }
}
