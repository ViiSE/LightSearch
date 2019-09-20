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
import lightsearch.server.timer.TimersIDEnum;
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
public class HandlerExecutorTestNG {

    private void test(ServerSocket serverSocket, LightSearchListenerDTO listenerDTO, LightSearchThread test, String logDir) {
        try {
            test.start();
            Socket clientSocket = serverSocket.accept();
            
            ConnectionIdentifier connectionIdentifier = ConnectionIdentifierInit.connectionIdentifier();
            ConnectionIdentifierResult connectionIdentifierResult = connectionIdentifier.identifyConnection(clientSocket);
            
            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            assertNotNull(serverDTO, "ServerDTO is null!");
            
            LoggerServer logger = DataProviderCreator.createDataProvider(LoggerServer.class, logDir);
            
            HandlerCreator handlerCreator =
                    HandlerCreatorInit.handlerCreator(connectionIdentifierResult, serverDTO, listenerDTO, logger);
            
            Handler handler = handlerCreator.getHandler();
            
            HandlerExecutor handlerExecutor = HandlerExecutorInit.handlerExecutor(listenerDTO.threadManager());
            assertNotNull(handlerExecutor, "HandlerExecutor is null!");
            
            handlerExecutor.executeHandler(handler);
            
            System.out.println("ExecuteHandler: " + handler);
            System.out.println("HandlerExecutor execute handler: " + handler);
            System.out.println("ThreadManager: ");
            listenerDTO.threadManager().holder().getThreads().forEach((thread) ->
                    System.out.println("\t ThreadName: " + listenerDTO.threadManager().holder().getId(thread)
                            + "; Thread: " + thread));
            
        } catch (IOException | ConnectionIdentifierException ex) {
            catchMessage(ex);
        }
    }

    @Test
    @Parameters({"ip", "port", "logDirectory"})
    public void executeHandlerAdmin(String ip, int port, String logDirectory) {
        testBegin("HandlerExecutor", "executeHandler(). Admin tesst");
        
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            HandlerTestUtils.close = false;
            
            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
            assertNotNull(listenerDTO, "ListenerDTO is null!");

            LightSearchThread admin = LightSearchThreadInit.lightSearchThread(new HandlerTestUtils.Admin(ip, port));
            test(serverSocket, listenerDTO, admin, logDirectory);

            HandlerTestUtils.close = true;
            serverSocket.close();
            
            listenerDTO.threadManager().interruptAll(TimersIDEnum.REBOOT_TIMER_ID.stringValue());
            System.out.println("ThreadManager: ");
            listenerDTO.threadManager().holder().getThreads().forEach((thread) ->
                    System.out.println("\t ThreadName: " + listenerDTO.threadManager().holder().getId(thread)
                            + "; Thread: " + thread));
        } catch(IOException ex) {
            catchMessage(ex);
        }
        
        testEnd("HandlerExecutor", "executeHandler(). Admin test");
    }

    @Test
    @Parameters({"ip", "port", "logDirectory"})
    public void executeHandlerClient(String ip, int port, String logDirectory) {
        testBegin("HandlerExecutor", "executeHandler(). Client test");

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            HandlerTestUtils.close = false;

            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
            assertNotNull(listenerDTO, "ListenerDTO is null!");

            LightSearchThread client = LightSearchThreadInit.lightSearchThread(new HandlerTestUtils.Client(ip, port));
            test(serverSocket, listenerDTO, client, logDirectory);

            HandlerTestUtils.close = true;
            serverSocket.close();

            listenerDTO.threadManager().interruptAll(TimersIDEnum.REBOOT_TIMER_ID.stringValue());
            System.out.println("ThreadManager: ");
            listenerDTO.threadManager().holder().getThreads().forEach((thread) ->
                    System.out.println("\t ThreadName: " + listenerDTO.threadManager().holder().getId(thread)
                            + "; Thread: " + thread));
        } catch(IOException ex) {
            catchMessage(ex);
        }

        testEnd("HandlerExecutor", "executeHandler(). Client test");
    }

    @Test
    @Parameters({"ip", "port", "logDirectory"})
    public void executeHandlerSystem(String ip, int port, String logDirectory) {
        testBegin("HandlerExecutor", "executeHandler(). System test");

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            HandlerTestUtils.close = false;

            LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
            LightSearchListenerDTO listenerDTO = DataProviderCreator.createDataProvider(LightSearchListenerDTO.class, serverDTO);
            assertNotNull(listenerDTO, "ListenerDTO is null!");

            LightSearchThread systemBot = LightSearchThreadInit.lightSearchThread(new HandlerTestUtils.SystemBot(ip, port));
            test(serverSocket, listenerDTO, systemBot, logDirectory);

            HandlerTestUtils.close = true;
            serverSocket.close();

            listenerDTO.threadManager().interruptAll(TimersIDEnum.REBOOT_TIMER_ID.stringValue());
            System.out.println("ThreadManager: ");
            listenerDTO.threadManager().holder().getThreads().forEach((thread) ->
                    System.out.println("\t ThreadName: " + listenerDTO.threadManager().holder().getId(thread)
                            + "; Thread: " + thread));
        } catch(IOException ex) {
            catchMessage(ex);
        }

        testEnd("HandlerExecutor", "executeHandler(). System test");
    }
}
