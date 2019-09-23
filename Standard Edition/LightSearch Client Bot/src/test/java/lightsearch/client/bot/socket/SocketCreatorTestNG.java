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
package lightsearch.client.bot.socket;

import lightsearch.client.bot.data.ConnectionDTO;
import lightsearch.client.bot.data.ConnectionDTOInit;
import lightsearch.client.bot.exception.SocketException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
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
public class SocketCreatorTestNG {
    
    private ConnectionDTO connDTO;
    private boolean isExit = true;
    
    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpClass(String ip, int port) {
        Thread th = new Thread(new TestServer(port));
        th.start();
        connDTO = ConnectionDTOInit.connectDTO(ip, port);
    }
    
    @Test
    public void createSocket() {
        testBegin("SocketCreator", "createSocket()");
        
        try {
            SocketCreator socketCr = SocketCreatorInit.socketCreator(connDTO);
            Socket socket = socketCr.createSocket();
            assertNotNull(socket, "Socket is null!");
            System.out.println("Socket: " + socket);
        } catch(SocketException ex) {
            catchMessage(ex);
        }
        
        testEnd("SocketCreator", "createSocket()");
    }
    
    @AfterMethod
    public void tearDownClass() {
        isExit = false;
    }
    
    private final class TestServer implements Runnable {

        private final int port;

        TestServer(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try(ServerSocket sock = new ServerSocket(port)) {
                System.out.println("Test server started.");
                sock.accept();
                System.out.println("CONNECT!");
                while(isExit) {}
                System.out.println("Server shutdown.");
            } catch (IOException ex) {
                catchMessage(ex);
            }
        }
        
    }
}
