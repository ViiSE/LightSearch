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
public class ClientCommandTestNG {
    
    private String initCommandMessage() {
        String commandMessage = "{"
                            + "\"IMEI\":\"123456789123456\","
                            + "\"command\":\"connect\","
                            + "\"ip\":\"192.168.3.129\","
                            + "\"os\":\"Android 8.1 Oreo\","
                            + "\"model\":\"Nexus 6p\","
                            + "\"username\":\"clientname\","
                            + "\"password\":\"pasS1234!\""
                        + "}";
        
        return commandMessage;
    }
    
    private String initSearchMessage() {
        String searchMessage = "{"
                            + "\"IMEI\":\"123456789123456\","
                            + "\"command\":\"search\","
                            + "\"barcode\":\"56897536\","
                            + "\"sklad\":\"sklad1\","
                            + "\"TK\":\"TK1\""
                        + "}";
        return searchMessage;
    }
    
    @Test
    public void command() {
        testBegin("ClientCommand", "command()");
        
        try {
            String message = initCommandMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Command is null!");
            assertFalse(clientCmd.command().equals(""), "Command is null!");
            System.out.println("------------------------------------");
            System.out.println("Command: " + clientCmd.command());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "command()");
    }

    @Test
    public void IMEI() {
        testBegin("ClientCommand", "IMEI()");
        
        try {
            String message = initCommandMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.IMEI(), "IMEI is null!");
            assertFalse(clientCmd.IMEI().equals(""), "IMEI is null!");
            System.out.println("------------------------------------");
            System.out.println("IMEI: " + clientCmd.IMEI());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "IMEI()");
    }

    @Test
    public void ip() {
        testBegin("ClientCommand", "ip()");
        
        try {
            String message = initCommandMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.ip(), "IP is null!");
            assertFalse(clientCmd.ip().equals(""), "IP is null!");
            System.out.println("------------------------------------");
            System.out.println("IP: " + clientCmd.ip());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "ip()");
    }

    @Test
    public void OS() {
        testBegin("ClientCommand", "OS()");
        
        try {
            String message = initCommandMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.os(), "OS is null!");
            assertFalse(clientCmd.os().equals(""), "OS is null!");
            System.out.println("------------------------------------");
            System.out.println("OS: " + clientCmd.os());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "OS()");
    }

    @Test
    public void model() {
        testBegin("ClientCommand", "model()");
        
        try {
            String message = initCommandMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.model(), "Model is null!");
            assertFalse(clientCmd.model().equals(""), "Model is null!");
            System.out.println("------------------------------------");
            System.out.println("Model: " + clientCmd.model());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "model()");
    }

    @Test
    public void username() {
        testBegin("ClientCommand", "username()");
        
        try {
            String message = initCommandMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.username(), "Username is null!");
            assertFalse(clientCmd.username().equals(""), "Username is null!");
            System.out.println("------------------------------------");
            System.out.println("Username: " + clientCmd.username());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "username()");
    }
    
    @Test
    public void password() {
        testBegin("ClientCommand", "password()");
        
        try {
            String message = initCommandMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.password(), "Password is null!");
            assertFalse(clientCmd.password().equals(""), "Password is null!");
            System.out.println("------------------------------------");
            System.out.println("Password: " + clientCmd.password());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "password()");
    }

    @Test
    public void barcode() {
        testBegin("ClientCommand", "barcode()");
        
        try {
            String message = initSearchMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.barcode(), "Barcode is null!");
            assertFalse(clientCmd.barcode().equals(""), "Barcode is null!");
            System.out.println("------------------------------------");
            System.out.println("Barcode: " + clientCmd.barcode());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "barcode()");
    }

    @Test
    public void sklad() {
        testBegin("ClientCommand", "sklad()");
        
        try {
            String message = initSearchMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.sklad(), "Sklad is null!");
            assertFalse(clientCmd.sklad().equals(""), "Sklad is null!");
            System.out.println("------------------------------------");
            System.out.println("Sklad: " + clientCmd.sklad());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "sklad()");
    }

    @Test
    public void TK() {
        testBegin("ClientCommand", "TK()");
        
        try {
            String message = initSearchMessage();
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.TK(), "TK is null!");
            assertFalse(clientCmd.TK().equals(""), "TK is null!");
            System.out.println("------------------------------------");
            System.out.println("Sklad: " + clientCmd.TK());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        
        testEnd("ClientCommand", "TK()");
    }
}
