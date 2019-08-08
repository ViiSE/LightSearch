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

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
import lightsearch.server.exception.CommandConverterException;
import lightsearch.server.handler.client.ReceivedClientCommandVerifier;
import lightsearch.server.handler.client.ReceivedClientCommandVerifierInit;
import lightsearch.server.exception.ReceivedCommandVerifierException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ReceivedClientCommandVerifierTestNG {
    
    private ClientCommand initClientCommand() {
        try {
            String message = "{"
                            + "\"command\":\"search\""
                            + "\"IMEI\":\"123456789123456\""
                            + "\"barcode\":\"5421354\""
                            + "\"sklad\":\"null\""
                            + "\"TK\":\"null\""
                        + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.barcode(), "Client barcode is null!");
            assertNotNull(clientCmd.sklad(), "Client sklad is null!");
            assertNotNull(clientCmd.TK(), "Client TK is null!");
            assertFalse(clientCmd.IMEI().equals(""), "Client IMEI is null!");
            assertFalse(clientCmd.sklad().equals(""), "Client sklad is null!");
            assertFalse(clientCmd.TK().equals(""), "Client TK is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! ClientCommand: " + ex.getMessage());
            return null;
        }
    }
    
    private String initIMEI() {
        return "123456789123456";
    }
    
    @Test
    public void verifyReceivedClientCommand() {
        testBegin("ReceivedClientCommandVerifier", "verifyReceivedClientCommand()");
        
        try {
            String IMEI = initIMEI();
            assertNotNull(IMEI, "IMEI is null!");
            ClientCommand clientCommand = initClientCommand();
            
            ReceivedClientCommandVerifier cmdVerifier = ReceivedClientCommandVerifierInit.receivedCommandVerifier();
            cmdVerifier.verifyReceivedClientCommand(clientCommand, IMEI);
            System.out.println("VERIFY is OK!");
            
        } catch (ReceivedCommandVerifierException ex) {
            System.out.println("CATCH! verifyReceivedClientCommand: " + ex.getMessage());
        }
        
        testEnd("ReceivedClientCommandVerifier", "verifyReceivedClientCommand()");
    }
}
