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
import lightsearch.server.exception.ReceivedCommandVerifierException;
import lightsearch.server.handler.admin.ReceivedAdminCommandVerifier;
import lightsearch.server.handler.admin.ReceivedAdminCommandVerifierInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ReceivedAdminCommandVerifierTestNG {
    
    private AdminCommand initAdminCommand(String message) {
        try {
            AdminCommandConverter admCmdConverter = AdminCommandConverterInit.adminCommandConverter();
            AdminCommand admCmd = admCmdConverter.convertToAdminCommand(message);
            assertNotNull(admCmd, "Admin Command is null!");
            assertNotNull(admCmd.name(), "Admin name is null!");
            assertNotNull(admCmd.command(), "Admin command is null!");
            assertNotNull(admCmd.IMEI(), "IMEI is null!");
            assertNotEquals(admCmd.name(), "", "Admin name is null!");
            return admCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }
    
    @Test
    @Parameters({"bigMessage", "adminName"})
    public void verifyReceivedAdminCommand(String message, String name) {
        testBegin("ReceivedAdminCommandVerifier", "verifyReceivedAdminCommand()");
        
        try {
            AdminCommand admCommand = initAdminCommand(message);
            
            ReceivedAdminCommandVerifier admCmdVerifier = ReceivedAdminCommandVerifierInit.receivedCommandVerifier();
            admCmdVerifier.verifyReceivedAdminCommand(admCommand, name);
            System.out.println("VERIFY IS OK!");
            
        } catch (ReceivedCommandVerifierException ex) {
            catchMessage(ex);
        }
        
        testEnd("ReceivedAdminCommandVerifier", "verifyReceivedAdminCommand()");
    }
}
