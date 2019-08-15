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
package lightsearch.server.listener;

import lightsearch.server.data.LightSearchListenerDTO;
import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.exception.ConnectionIdentifierException;
import lightsearch.server.handler.HandlerCreator;
import lightsearch.server.handler.HandlerExecutor;
import lightsearch.server.identifier.ConnectionIdentifier;
import lightsearch.server.identifier.ConnectionIdentifierResult;
import lightsearch.server.iterator.HandlerIterator;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import lightsearch.server.producer.handler.HandlerCreatorProducer;
import lightsearch.server.producer.handler.HandlerExecutorProducer;
import lightsearch.server.producer.handler.HandlerIteratorProducer;
import lightsearch.server.producer.identifier.ConnectionIdentifierProducer;
import lightsearch.server.producer.socket.ServerSocketCreatorProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author ViiSE
 */
@Component("lightSearchServerListenerSocketDefault")
@Scope("prototype")
public class LightSearchServerListenerSocketDefaultImpl implements LightSearchServerListener {

    private final LightSearchServerDTO serverDTO;
    private final LightSearchListenerDTO listenerDTO;
    private final LoggerServer loggerServer;

    @Autowired private ServerSocketCreatorProducer serverSockCrProducer;
    @Autowired private ConnectionIdentifierProducer connIdentProducer;
    @Autowired private HandlerExecutorProducer handlerExecProd;
    @Autowired private HandlerCreatorProducer handlerCrProducer;
    @Autowired private HandlerIteratorProducer handlerIteratorProducer;

    public LightSearchServerListenerSocketDefaultImpl(LightSearchServerDTO serverDTO,
            LightSearchListenerDTO listenerDTO, LoggerServer loggerServer) {
        this.serverDTO = serverDTO;
        this.listenerDTO = listenerDTO;
        this.loggerServer = loggerServer;
    }

    @Override
    public void startServer() {
            ServerSocket serverSocket = serverSockCrProducer.getServerSocketCreatorDefaultInstance().createServerSocket(
                    serverDTO.serverPort());
            
            loggerServer.log(LogMessageTypeEnum.INFO, listenerDTO.currentDateTime(), "Server started");
            
            ConnectionIdentifier connectionIdentifier = connIdentProducer.getConnectionIdentifierDefaultInstance();
            HandlerExecutor handlerExecutor = handlerExecProd.getHandlerExecutorDefaultInstance(listenerDTO.threadManager());
            HandlerIterator handlerIterator = handlerIteratorProducer.getHandlerIteratorDefaultInstance();

            while(true) {
                Socket clientSocket = null;
                try {
                    clientSocket = serverSocket.accept();
                    clientSocket.setSoTimeout(serverDTO.settingsDAO().clientTimeoutValue());
                    ConnectionIdentifierResult connectionIdentifierResult = connectionIdentifier.identifyConnection(clientSocket);

                    HandlerCreator handlerCreator = handlerCrProducer.getHandlerCreatorDefaultInstance(
                            connectionIdentifierResult, serverDTO, listenerDTO, loggerServer, handlerIterator);
                    handlerExecutor.executeHandler(handlerCreator.getHandler());
                }
                catch(IOException ex) {
                    loggerServer.log(LogMessageTypeEnum.ERROR, listenerDTO.currentDateTime(), "StartServer, acceptSocket, message - " + ex.getMessage());
                    if(clientSocket != null)
                        try { clientSocket.close(); } catch (IOException ignore) {}
                }
                catch(ConnectionIdentifierException ex) {
                    if(ex.getMessage() != null)
                        loggerServer.log(LogMessageTypeEnum.ERROR, listenerDTO.currentDateTime(), "StartServer, connectionIdentifier, message - " + ex.getMessage());
                    if(clientSocket != null)
                    try { clientSocket.close(); } catch (IOException ignore) {}
                }
            }
    }
}
