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

import lightsearch.server.cmd.admin.AdminCommand;
import lightsearch.server.cmd.admin.AdminCommandConverter;
import lightsearch.server.cmd.admin.AdminCommandConverterInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.handler.admin.ReceivedAdminCommandVerifier;
import lightsearch.server.handler.admin.ReceivedAdminCommandVerifierInit;
import lightsearch.server.exception.ReceivedCommandVerifierException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ReceivedAdminCommandVerifierTestNG {
    
    private AdminCommand initAdminCommand() {
        try {
            String message = "{"
                    + "\"name\":\"admin\" "
                    + "\"command\":\"addBlacklist\""
                    + "\"IMEI\":\"123456789123456\""
                    + "}";
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd;
            admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertNotNull(admCmd.command(), "Admin command is null!");
            assertNotNull(admCmd.IMEI(), "IMEI is null!");
            assertFalse(admCmd.name().equals(""), "Admin name is null!");
            return admCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    @Test
    public void verifyReceivedAdminCommand() {
        testBegin("ReceivedAdminCommandVerifier", "verifyReceivedAdminCommand()");
        
        try {
            AdminCommand admCommand = initAdminCommand();
            String name = "admin";
            
            ReceivedAdminCommandVerifier admCmdVerifier = ReceivedAdminCommandVerifierInit.receivedCommandVerifier();
            admCmdVerifier.verifyReceivedAdminCommand(admCommand, name);
            System.out.println("VERIFY IS OK!");
            
        } catch (ReceivedCommandVerifierException ex) {
            System.out.println("CATCH! verifyReceivedAdminCommand: " + ex.getMessage());
        }
        
        testEnd("ReceivedAdminCommandVerifier", "verifyReceivedAdminCommand()");
    }
}
