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
package lightsearch.server.cmd.client.processor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.cmd.client.ClientCommandConverter;
import lightsearch.server.cmd.client.ClientCommandConverterInit;
import lightsearch.server.cmd.client.ClientCommandCreator;
import lightsearch.server.cmd.result.CommandResult;
import lightsearch.server.data.ClientDAO;
import lightsearch.server.data.ClientDAOInit;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.exception.CommandConverterException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.util.Map;
import java.util.function.Function;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class CloseSoftCheckProcessorTestNG {
    
    private ClientCommand initClientCommand(String message) {
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
    
    @Test
    @Parameters({"closeSoftCheckMessage"})
    public void apply(String message) {
        testBegin("CloseSoftCheckProcessor", "apply()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        System.out.println("serverDTO.clients BEFORE: ");
        serverDTO.clients().keySet().forEach((clientName) ->
                System.out.println("\t" + clientName + " " + serverDTO.clients().get(clientName)));
        
        ClientDAO clientDAO = ClientDAOInit.clientDAO();
        System.out.println("clientDAO BEFORE: ");
        System.out.println("\t clientDAO.isFirst: " + clientDAO.isFirst());
        System.out.println("\t clientDAO.IMEI: " + clientDAO.IMEI());
        System.out.println("\t clientDAO.databaseConnection: " + clientDAO.databaseConnection());
        
        Map<String, Function<ClientCommand, CommandResult>> clientCmdHolder =
                DataProviderCreator.createDataProvider(ClientCommandCreator.class, serverDTO, clientDAO)
                .createCommandHolder();
        ClientCommand clientCmd = initClientCommand(message);
        assertNotNull(clientCmd, "Client Command is null!");
        
        String command = clientCmd.command();
        assertNotNull(command, "Command is null!");
        assertNotEquals(command, "", "Command is null!");
        
        Function<ClientCommand, CommandResult> processor = clientCmdHolder.get(command);
        assertNotNull(processor, "processor is null!");
        CommandResult cmdRes = processor.apply(clientCmd);
        
        System.out.println("Results:");
        System.out.println("serverDTO.clients: ");
        serverDTO.clients().keySet().forEach((clientName) ->
                System.out.println("\t" + clientName + " " + serverDTO.clients().get(clientName)));
        
        System.out.println("clientDAO: ");
        System.out.println("\t clientDAO.isFirst: " + clientDAO.isFirst());
        System.out.println("\t clientDAO.IMEI: " + clientDAO.IMEI());
        System.out.println("\t clientDAO.databaseConnection: " + clientDAO.databaseConnection());
        
        System.out.println("CommandResult.Type: " + cmdRes.type());
        System.out.println("CommandResult.Message: " + cmdRes.message());
        System.out.println("CommandResult.LogMessage: " + cmdRes.logMessage());
    
        testEnd("CloseSoftCheckProcessor", "apply()");
    }
}
