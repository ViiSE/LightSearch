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
public class AdminCommandConverterTestNG {
    
    @Test
    @Parameters({"bigMessage"})
    public void convertToAdminCommand(String message) {
        testBegin("AdminCommandConverter", "convertToAdminCommand()");
        
        AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
        try {
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
        
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertNotEquals(admCmd.name(), "", "Admin name is null!");

            assertNotNull(admCmd.command(), "Command is null!");
            assertNotEquals(admCmd.command(), "", "Command is null!");

            assertNotNull(admCmd.serverTime(), "Server time is null!");
            assertNotEquals(admCmd.serverTime(), "", "Server time is null!");

            assertNotNull(admCmd.clientTimeout(), "Client timeout is null!");
            assertNotEquals(admCmd.clientTimeout(), "", "Client timeout is null!");

            assertNotNull(admCmd.IMEI(), "IMEI is null!");
            assertNotEquals(admCmd.IMEI(), "", "IMEI is null!");

            assertNotNull(admCmd.password(), "Password is null!");
            assertNotEquals(admCmd.password(), "", "Password is null!");

            assertNotNull(admCmd.ip(), "Database IP is null!");
            assertNotEquals(admCmd.ip(), "", "Database IP is null!");

            assertNotNull(admCmd.port(), "Database port is null!");
            assertNotEquals(admCmd.port(), "", "Database port is null!");

            assertNotNull(admCmd.dbName(), "Database name is null!");
            assertNotEquals(admCmd.dbName(), "", "Database name is null!");

            assertNotNull(admCmd.adminName(), "New admin name is null!");
            assertNotEquals(admCmd.adminName(), "", "New admin name is null!");

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
            catchMessage(ex);
        }
        
        testEnd("AdminCommandConverter", "convertToAdminCommand()");
    }
}
