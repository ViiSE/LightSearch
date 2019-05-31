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
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import lightsearch.server.database.cmd.message.DatabaseCommandMessageInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class DatabaseCommandMessageTestNG {
    
    private ClientCommand initClientCommandSearch() {
        try {
            String message = "{"
                            + "\"command\":\"search\","
                            + "\"IMEI\":\"123456789123456\","
                            + "\"barcode\":\"5421354\","
                            + "\"sklad\":\"null\","
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
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
     
    private ClientCommand initClientCommandConnect() {
        try {
            String message = "{"
                            + "\"command\":\"connect\","
                            + "\"IMEI\":\"123456789123456\","
                            + "\"ip\":\"127.0.0.1\","
                            + "\"os\":\"Android 8.1 Oreo\","
                            + "\"model\":\"Nexus 5\","
                            + "\"username\":\"androidUser\","
                            + "\"password\":\"superSecretPass!12\""
                        + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.ip(), "Client IP is null!");
            assertNotNull(clientCmd.os(), "Client OS is null!");
            assertNotNull(clientCmd.model(), "Client model is null!");
            assertNotNull(clientCmd.username(), "Client username is null!");
            assertNotNull(clientCmd.password(), "Client password is null!");
            assertFalse(clientCmd.IMEI().equals(""), "Client IMEI is null!");
            assertFalse(clientCmd.ip().equals(""), "Client IP is null!");
            assertFalse(clientCmd.os().equals(""), "Client OS is null!");
            assertFalse(clientCmd.model().equals(""), "Client model is null!");
            assertFalse(clientCmd.username().equals(""), "Client username is null!");
            assertFalse(clientCmd.password().equals(""), "Client password is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    private ClientCommand initClientCommandCloseSoftCheck() {
        try {
            String message = "{"
                              + "\"command\": \"closeSoftCheck\","
                              + "\"IMEI\": \"12346789123456\","
                              + "\"user_ident\": \"111\","
                              + "\"card_code\": \"123456\","
                              + "\"delivery\": \"1\""
                            + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user ident is null!");
            assertNotNull(clientCmd.cardCode(), "Client card code is null!");
            assertNotNull(clientCmd.data(), "Client data is null!");
            assertNotNull(clientCmd.delivery(), "Client delivery is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    private ClientCommand initClientCommandCancelSoftCheck() {
        try {
            String message = "{"
                              + "\"command\": \"cancelSoftCheck\","
                              + "\"IMEI\": \"12346789123456\","
                              + "\"user_ident\": \"111\","
                              + "\"card_code\": \"123456\","
                            + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user ident is null!");
            assertNotNull(clientCmd.cardCode(), "Client card code is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    private ClientCommand initClientCommandOpenSoftCheck() {
        try {
            String message = "{"
                              + "\"command\": \"openSoftCheck\","
                              + "\"IMEI\": \"12346789123456\","
                              + "\"user_ident\": \"111\","
                              + "\"card_code\": \"5425489\""
                            + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user ident is null!");
            assertNotNull(clientCmd.cardCode(), "Client card code is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    private ClientCommand initClientCommandConfirmSoftCheckProducts() {
        try {
            String message = "{"
                              + "\"command\": \"confirm_prod_sf\","
                              + "\"IMEI\": \"12346789123456\","
                              + "\"user_ident\": \"111\","
                              + "\"card_code\": \"123456\","
                              + "\"data\":" 
                              + "["
                              +     "{\"ID\": \"111111\", \"amount\": \"1\"},"
                              +     "{\"ID\": \"222222\", \"amount\": \"3\"},"
                              + "]"
                            + "}";
            ClientCommandConverter clientCmdConverter = ClientCommandConverterInit.clientCommandConverter();
            ClientCommand clientCmd;
            clientCmd = clientCmdConverter.convertToClientCommand(message);
            assertNotNull(clientCmd, "Client Command is null!");
            assertNotNull(clientCmd.command(), "Client command is null!");
            assertNotNull(clientCmd.IMEI(), "Client IMEI is null!");
            assertNotNull(clientCmd.userIdentifier(), "Client user ident is null!");
            assertNotNull(clientCmd.cardCode(), "Client card code is null!");
            assertNotNull(clientCmd.data(), "Client data is null!");
            
            return clientCmd;
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
            return null;
        }
    }
    
    private void messageConnect() {
        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageConnection(): ");
        ClientCommand clientCmdConnect = initClientCommandConnect();
        assertNotNull(clientCmdConnect, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageConnection(
            clientCmdConnect.command(), clientCmdConnect.IMEI());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());
    }
    
    private void messageSearch() {
        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageSearch(): ");
        ClientCommand clientCmdSearch = initClientCommandSearch();
        assertNotNull(clientCmdSearch, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageSearch(
            clientCmdSearch.command(), clientCmdSearch.IMEI(), clientCmdSearch.barcode(),
                clientCmdSearch.sklad(), clientCmdSearch.TK());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());
    }
    
    private void messageCloseSoftCheck() {
        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageCloseSoftCheck(): ");
        ClientCommand clientCmdCloseSoftCheck = initClientCommandCloseSoftCheck();
        assertNotNull(clientCmdCloseSoftCheck, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageCloseSoftCheck(
                clientCmdCloseSoftCheck.command(), clientCmdCloseSoftCheck.IMEI(), 
                clientCmdCloseSoftCheck.userIdentifier(), clientCmdCloseSoftCheck.cardCode(),
                clientCmdCloseSoftCheck.delivery());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());
    }
    
    private void messageCancelSoftCheck() {
        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageCancelSoftCheck(): ");
        ClientCommand clientCmdCancelSoftCheck = initClientCommandCancelSoftCheck();
        assertNotNull(clientCmdCancelSoftCheck, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageCancelSoftCheck(
                clientCmdCancelSoftCheck.command(), clientCmdCancelSoftCheck.IMEI(),
                clientCmdCancelSoftCheck.userIdentifier(), clientCmdCancelSoftCheck.cardCode());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());
    }
    
    private void messageOpenSoftCheck() {
        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageOpenSoftCheck(): ");
        ClientCommand clientCmdOpenSoftCheck = initClientCommandOpenSoftCheck();
        assertNotNull(clientCmdOpenSoftCheck, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageOpenSoftCheck(
                clientCmdOpenSoftCheck.command(), clientCmdOpenSoftCheck.IMEI(), 
                clientCmdOpenSoftCheck.userIdentifier(), clientCmdOpenSoftCheck.cardCode());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());
    }
    
    private void messageConfirmSoftCheckProducts() {
        System.out.println("DatabaseCommandMessageInit.databaseCommandMessageConfirmSoftCheckProducts(): ");
        ClientCommand clientCmdConfirmSoftCheckProducts = initClientCommandConfirmSoftCheckProducts();
        assertNotNull(clientCmdConfirmSoftCheckProducts, "Client Command is null!");
        
        DatabaseCommandMessage dbMessageConn = DatabaseCommandMessageInit.databaseCommandMessageConfirmSoftCheckProducts(
                clientCmdConfirmSoftCheckProducts.command(), 
                clientCmdConfirmSoftCheckProducts.IMEI(), 
                clientCmdConfirmSoftCheckProducts.userIdentifier(), 
                clientCmdConfirmSoftCheckProducts.cardCode(),
                clientCmdConfirmSoftCheckProducts.data());
        assertNotNull(dbMessageConn, "Database command message is null!");
        
        System.out.println("DatabaseCommandMessageConnection.message(): " + dbMessageConn.message());
    }
    
    @Test
    public void message() {
        testBegin("DatabaseCommandMessage", "message()");
        
        messageConnect();
        messageSearch();
        messageCloseSoftCheck();
        messageCancelSoftCheck();
        messageOpenSoftCheck();
        messageConfirmSoftCheckProducts();
        
        testEnd("DatabaseCommandMessage", "message()");
    }
}
