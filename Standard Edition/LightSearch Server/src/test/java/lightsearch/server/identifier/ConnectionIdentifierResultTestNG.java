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
package lightsearch.server.identifier;

import lightsearch.server.exception.ConnectionIdentifierException;
import lightsearch.server.handler.HandlerTestUtils;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ConnectionIdentifierResultTestNG {
    
    @Test
    @Parameters({"ip", "port"})
    public void identifyConnection(String ip, int port) {
        testBegin("ConnectionIdentifierResult", "identifyConnection()");

        try(ServerSocket serverSocket = new ServerSocket(port)) {
            HandlerTestUtils.close = false;
            Thread client = new Thread(new HandlerTestUtils.Client(ip, port));
            client.start();
            Socket clientSocket = serverSocket.accept();
            ConnectionIdentifier connIndent = ConnectionIdentifierInit.connectionIdentifier();
            ConnectionIdentifierResult connRes = connIndent.identifyConnection(clientSocket);
            assertNotNull(connRes, "Connection identifier result is null!");
            System.out.println("OK");
            System.out.println("Connection identifier result: " + connRes);
            System.out.println("Identifier: " + connRes.identifier());
            System.out.println("Client socket: " + connRes.clientSocket());
            System.out.println("Data stream: " + connRes.dataStream());

            HandlerTestUtils.close = true;
        } catch(IOException | ConnectionIdentifierException ex) {
            catchMessage(ex);
        }
        
        testEnd("ConnectionIdentifierResult", "identifyConnection()");
    }
}
