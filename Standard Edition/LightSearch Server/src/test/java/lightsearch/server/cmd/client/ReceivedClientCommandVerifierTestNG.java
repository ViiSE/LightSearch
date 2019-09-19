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
package lightsearch.server.cmd.client;

import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.exception.ReceivedCommandVerifierException;
import lightsearch.server.handler.client.ReceivedClientCommandVerifier;
import lightsearch.server.handler.client.ReceivedClientCommandVerifierInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ReceivedClientCommandVerifierTestNG {
    
    private ClientCommand initClientCommand(String message) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.barcode(), "Client barcode is null!");
            assertNotNull(clientCmd.sklad(), "Client sklad is null!");
            assertNotNull(clientCmd.TK(), "Client TK is null!");
            assertNotEquals(clientCmd.IMEI(), "", "Client IMEI is null!");
            assertNotEquals(clientCmd.sklad(), "", "Client sklad is null!");
            assertNotEquals(clientCmd.TK(), "", "Client TK is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }

    @Test
    @Parameters({"searchMessage", "IMEI"})
    public void verifyReceivedClientCommand(String message, String IMEI) {
        testBegin("ReceivedClientCommandVerifier", "verifyReceivedClientCommand()");
        
        try {
            assertNotNull(IMEI, "IMEI is null!");
            ClientCommand clientCommand = initClientCommand(message);
            
            ReceivedClientCommandVerifier cmdVerifier = ReceivedClientCommandVerifierInit.receivedCommandVerifier();
            cmdVerifier.verifyReceivedClientCommand(clientCommand, IMEI);
            System.out.println("VERIFY is OK!");
            
        } catch (ReceivedCommandVerifierException ex) {
            catchMessage(ex);
        }
        
        testEnd("ReceivedClientCommandVerifier", "verifyReceivedClientCommand()");
    }
}
