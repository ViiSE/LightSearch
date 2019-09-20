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
package lightsearch.server.handler;

import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.exception.ConnectionIdentifierException;
import lightsearch.server.identifier.ConnectionIdentifier;
import lightsearch.server.identifier.ConnectionIdentifierInit;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.LightSearchThread;
import lightsearch.server.thread.LightSearchThreadInit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class HandlerCreatorTestNG {
    
    private void test(ServerSocket serverSocket, LightSearchThread test, String logDir) {
        try {
            test.start();
            Socket clientSocket = serverSocket.accept();
            
            ConnectionIdentifier connectionIdentifier = ConnectionIdentifierInit.connectionIdentifier();
            ConnectionIdentifierResult connectionIdentifierResult = connectionIdentifier.identifyConnection(clientSocket);
            
            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");
            
            LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
            assertNotNull(listenerDTO, "ListenerDTO is null!");
            
            LoggerServer logger = DataProviderCreator.createDataProvider(LoggerServer.class,  logDir);
            
            HandlerCreator handlerCreator =
                    HandlerCreatorInit.handlerCreator(connectionIdentifierResult, serverDTO, listenerDTO, logger);
            
            Handler handler = handlerCreator.getHandler();
            System.out.println("HandlerCreator.getHandler(): " + handler);
        } catch (IOException | ConnectionIdentifierException ex) {
            catchMessage(ex);
        }
    }
    
    @Test
    @Parameters({"ip", "port", "logDirectory"})
    public void getHandlerAdminTest(String ip, int port, String logDir) {
        testBegin("HandlerCreator", "getHandler(). Admin Test");
        
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            HandlerTestUtils.close = false;
            LightSearchThread admin = LightSearchThreadInit.lightSearchThread(new HandlerTestUtils.Admin(ip, port));
            test(serverSocket, admin, logDir);
            HandlerTestUtils.close = true;
        } catch(IOException ex) {
            catchMessage(ex);
        }
        
        testEnd("HandlerCreator", "getHandler(). Admin Test");
    }

    @Test
    @Parameters({"ip", "port", "logDirectory"})
    public void getHandlerClientTest(String ip, int port, String logDir) {
        testBegin("HandlerCreator", "getHandler(). Client Test");

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            HandlerTestUtils.close = false;
            LightSearchThread client = LightSearchThreadInit.lightSearchThread(new HandlerTestUtils.Client(ip, port));
            test(serverSocket, client, logDir);
            HandlerTestUtils.close = true;
        } catch(IOException ex) {
            catchMessage(ex);
        }

        testEnd("HandlerCreator", "getHandler(). Client Test");
    }

    @Test
    @Parameters({"ip", "port", "logDirectory"})
    public void getHandlerSystemTest(String ip, int port, String logDir) {
        testBegin("HandlerCreator", "getHandler(). System Test");

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            HandlerTestUtils.close = false;
            LightSearchThread system = LightSearchThreadInit.lightSearchThread(new HandlerTestUtils.SystemBot(ip, port));
            test(serverSocket, system, logDir);
            HandlerTestUtils.close = true;
        } catch(IOException ex) {
            catchMessage(ex);
        }

        testEnd("HandlerCreator", "getHandler(). System Test");
    }
}
