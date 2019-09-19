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
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ClientCommandTestNG {

    @Test
    @Parameters({"commandMessage"})
    public void command(String message) {
        testBegin("ClientCommand", "command()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Command is null!");
            assertNotEquals(clientCmd.command(), "", "Command is null!");
            System.out.println("------------------------------------");
            System.out.println("Command: " + clientCmd.command());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "command()");
    }

    @Test
    @Parameters({"commandMessage"})
    public void IMEI(String message) {
        testBegin("ClientCommand", "IMEI()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.IMEI(), "IMEI is null!");
            assertNotEquals(clientCmd.IMEI(), "", "IMEI is null!");
            System.out.println("------------------------------------");
            System.out.println("IMEI: " + clientCmd.IMEI());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "IMEI()");
    }

    @Test
    @Parameters({"commandMessage"})
    public void ip(String message) {
        testBegin("ClientCommand", "ip()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.ip(), "IP is null!");
            assertNotEquals(clientCmd.ip(), "", "IP is null!");
            System.out.println("------------------------------------");
            System.out.println("IP: " + clientCmd.ip());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "ip()");
    }

    @Test
    @Parameters({"commandMessage"})
    public void OS(String message) {
        testBegin("ClientCommand", "OS()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.os(), "OS is null!");
            assertNotEquals(clientCmd.os(), "", "OS is null!");
            System.out.println("------------------------------------");
            System.out.println("OS: " + clientCmd.os());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "OS()");
    }

    @Test
    @Parameters({"commandMessage"})
    public void model(String message) {
        testBegin("ClientCommand", "model()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.model(), "Model is null!");
            assertNotEquals(clientCmd.model(), "", "Model is null!");
            System.out.println("------------------------------------");
            System.out.println("Model: " + clientCmd.model());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "model()");
    }

    @Test
    @Parameters({"commandMessage"})
    public void username(String message) {
        testBegin("ClientCommand", "username()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.username(), "Username is null!");
            assertNotEquals(clientCmd.username(), "", "Username is null!");
            System.out.println("------------------------------------");
            System.out.println("Username: " + clientCmd.username());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "username()");
    }
    
    @Test
    @Parameters("commandMessage")
    public void password(String message) {
        testBegin("ClientCommand", "password()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.password(), "Password is null!");
            assertNotEquals(clientCmd.password(), "", "Password is null!");
            System.out.println("------------------------------------");
            System.out.println("Password: " + clientCmd.password());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "password()");
    }

    @Test
    @Parameters({"searchMessage"})
    public void barcode(String message) {
        testBegin("ClientCommand", "barcode()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.barcode(), "Barcode is null!");
            assertNotEquals(clientCmd.barcode(), "", "Barcode is null!");
            System.out.println("------------------------------------");
            System.out.println("Barcode: " + clientCmd.barcode());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "barcode()");
    }

    @Test
    @Parameters({"searchMessage"})
    public void sklad(String message) {
        testBegin("ClientCommand", "sklad()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.sklad(), "Sklad is null!");
            assertNotEquals(clientCmd.sklad(), "", "Sklad is null!");
            System.out.println("------------------------------------");
            System.out.println("Sklad: " + clientCmd.sklad());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "sklad()");
    }

    @Test
    @Parameters({"searchMessage"})
    public void TK(String message) {
        testBegin("ClientCommand", "TK()");
        
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.TK(), "TK is null!");
            assertNotEquals(clientCmd.TK(), "", "TK is null!");
            System.out.println("------------------------------------");
            System.out.println("Sklad: " + clientCmd.TK());
            System.out.println("------------------------------------");
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }
        
        testEnd("ClientCommand", "TK()");
    }
}
