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
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ClientCommandConverterTestNG {
    
    private String initMessage() {
        String message = "{"
                            + "\"command\":\"exampleCommand\","
                            + "\"IMEI\":\"123456789123456\","
                            + "\"ip\":\"127.0.0.1\","
                            + "\"os\":\"Android 8.1 Oreo\","
                            + "\"model\":\"Nexus 5\","
                            + "\"username\":\"androidUser\","
                            + "\"password\":\"superSecretPass!12\","
                            + "\"sklad\":[\"sklad1\", \"sklad2\"],"
                            + "\"TK\":[\"TK1\", \"TK2\"],"
                            + "\"barcode\":\"55531548\""
                        + "}";
        return message;
    }
    
    @Test
    public void convertToClientCommand() {
        try {
            testBegin("ClientCommandConverter", "convertToClientCommand()");
            
            ClientCommandConverter clientConverter = ClientCommandConverterInit.clientCommandConverter();
            String message = initMessage();
            assertNotNull(message, "Message is null!");
            assertFalse(message.equals(""), "Message is null!");
            ClientCommand clientCommand = clientConverter.convertToClientCommand(message);
            System.out.println("ClientCommand: ");
            System.out.println("\t clientCommand.IMEI: " + clientCommand.IMEI());
            System.out.println("\t clientCommand.ip: " + clientCommand.ip());
            System.out.println("\t clientCommand.os: " + clientCommand.os());
            System.out.println("\t clientCommand.model: " + clientCommand.model());
            System.out.println("\t clientCommand.username: " + clientCommand.username());
            System.out.println("\t clientCommand.password: " + clientCommand.password());
            System.out.println("\t clientCommand.sklad: " + clientCommand.sklad());
            System.out.println("\t clientCommand.TK: " + clientCommand.TK());
            System.out.println("\t clientCommand.barcode: " + clientCommand.barcode());
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }

        testEnd("ClientCommandConverter", "convertToClientCommand()");
    }
}
