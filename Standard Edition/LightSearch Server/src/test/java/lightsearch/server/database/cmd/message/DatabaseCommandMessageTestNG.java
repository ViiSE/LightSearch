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
package lightsearch.server.database.cmd.message;

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
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
public class DatabaseCommandMessageTestNG {
    
    private ClientCommand initClientCommandSearch(String message) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
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
     
    private ClientCommand initClientCommandConnect(String message) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.ip(), "Client IP is null!");
            assertNotNull(clientCmd.os(), "Client OS is null!");
            assertNotNull(clientCmd.model(), "Client model is null!");
            assertNotNull(clientCmd.username(), "Client username is null!");
            assertNotNull(clientCmd.password(), "Client password is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user identifier is null!");           
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }
    
    private ClientCommand initClientCommandCloseSoftCheck(String message) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user ident is null!");
            assertNotNull(clientCmd.cardCode(), "Client card code is null!");
            assertNotNull(clientCmd.delivery(), "Client delivery is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }
    
    private ClientCommand initClientCommandCancelSoftCheck(String message) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user ident is null!");
            assertNotNull(clientCmd.cardCode(), "Client card code is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }
    
    private ClientCommand initClientCommandOpenSoftCheck(String message) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user ident is null!");
            assertNotNull(clientCmd.cardCode(), "Client card code is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }
    
    private ClientCommand initClientCommandConfirmSoftCheckProducts(String message) {
        try {
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user ident is null!");
            assertNotNull(clientCmd.cardCode(), "Client card code is null!");
            assertNotNull(clientCmd.data(), "Client data is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            catchMessage(ex);
            return null;
        }
    }

    @Test
    @Parameters({"connectMessage"})
    private void connectMessage(String message) {
        testBegin("DatabaseCommandMessage", "message()");

        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageConnection(): ");
        ClientCommand clientCmdConnect = initClientCommandConnect(message);
        assertNotNull(clientCmdConnect, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageConnection(
            clientCmdConnect.command(), clientCmdConnect.IMEI(), 
                clientCmdConnect.username(), clientCmdConnect.userIdentifier());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());

        testEnd("DatabaseCommandMessage", "message()");
    }

    @Test
    @Parameters({"searchMessage"})
    private void searchMessage(String message) {
        testBegin("DatabaseCommandMessage", "message()");

        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageSearch(): ");
        ClientCommand clientCmdSearch = initClientCommandSearch(message);
        assertNotNull(clientCmdSearch, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageSearch(
            clientCmdSearch.command(), clientCmdSearch.IMEI(), clientCmdSearch.barcode(),
                clientCmdSearch.sklad(), clientCmdSearch.TK());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());

        testEnd("DatabaseCommandMessage", "message()");
    }

    @Test
    @Parameters({"closeSoftCheckMessage"})
    private void closeSoftCheckMessage(String message) {
        testBegin("DatabaseCommandMessage", "message()");

        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageCloseSoftCheck(): ");
        ClientCommand clientCmdCloseSoftCheck = initClientCommandCloseSoftCheck(message);
        assertNotNull(clientCmdCloseSoftCheck, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageCloseSoftCheck(
                clientCmdCloseSoftCheck.command(), clientCmdCloseSoftCheck.IMEI(), 
                clientCmdCloseSoftCheck.userIdentifier(), clientCmdCloseSoftCheck.cardCode(),
                clientCmdCloseSoftCheck.delivery());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());

        testEnd("DatabaseCommandMessage", "message()");
    }

    @Test
    @Parameters({"cancelSoftCheckMessage"})
    private void cancelSoftCheckMessage(String message) {
        testBegin("DatabaseCommandMessage", "message()");

        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageCancelSoftCheck(): ");
        ClientCommand clientCmdCancelSoftCheck = initClientCommandCancelSoftCheck(message);
        assertNotNull(clientCmdCancelSoftCheck, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageCancelSoftCheck(
                clientCmdCancelSoftCheck.command(), clientCmdCancelSoftCheck.IMEI(),
                clientCmdCancelSoftCheck.userIdentifier(), clientCmdCancelSoftCheck.cardCode());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());

        testEnd("DatabaseCommandMessage", "message()");
    }

    @Test
    @Parameters({"openSoftCheckMessage"})
    private void openSoftCheckMessage(String message) {
        testBegin("DatabaseCommandMessage", "message()");

        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageOpenSoftCheck(): ");
        ClientCommand clientCmdOpenSoftCheck = initClientCommandOpenSoftCheck(message);
        assertNotNull(clientCmdOpenSoftCheck, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageOpenSoftCheck(
                clientCmdOpenSoftCheck.command(), clientCmdOpenSoftCheck.IMEI(), 
                clientCmdOpenSoftCheck.userIdentifier(), clientCmdOpenSoftCheck.cardCode());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());

        testEnd("DatabaseCommandMessage", "message()");
    }

    @Test
    @Parameters({"confirmSoftCheckProductsMessage"})
    private void confirmSoftCheckProductsMessage(String message) {
        testBegin("DatabaseCommandMessage", "message()");

        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageConfirmSoftCheckProducts(): ");

        ClientCommand clientCmdConfirmSoftCheckProducts = initClientCommandConfirmSoftCheckProducts(message);
        assertNotNull(clientCmdConfirmSoftCheckProducts, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageConfirmSoftCheckProducts(
                clientCmdConfirmSoftCheckProducts.command(), 
                clientCmdConfirmSoftCheckProducts.IMEI(), 
                clientCmdConfirmSoftCheckProducts.userIdentifier(), 
                clientCmdConfirmSoftCheckProducts.cardCode(),
                clientCmdConfirmSoftCheckProducts.data());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());

        testEnd("DatabaseCommandMessage", "message()");
    }
}
