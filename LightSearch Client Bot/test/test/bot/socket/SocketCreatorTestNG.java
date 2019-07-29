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
package test.bot.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lightsearch.client.bot.data.ConnectionDTO;
import lightsearch.client.bot.data.ConnectionDTOInit;
import lightsearch.client.bot.exception.SocketException;
import lightsearch.client.bot.socket.SocketCreator;
import lightsearch.client.bot.socket.SocketCreatorInit;
import static org.testng.Assert.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SocketCreatorTestNG {
    
    private ConnectionDTO connDTO;
    private boolean isExit = true;
    
    @BeforeClass
    public void setUpClass() {
        Thread th = new Thread(new TestServer());
        th.start();
        
        String ip = "127.0.0.1";
        int port = 50000;
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
        }
        catch(SocketException ex) {
            System.out.println("CATCH! Exception: " + ex.getMessage());
        }
        
        testEnd("SocketCreator", "createSocket()");
    }
    
    @AfterMethod
    public void tearDownClass() {
        isExit = false;
    }
    
    private final class TestServer implements Runnable {

        @Override
        public void run() {
            try(ServerSocket sock = new ServerSocket(50000)) {
                System.out.println("Test server started.");
                sock.accept();
                System.out.println("CONNECT!");
                while(isExit) {}
                System.out.println("Server shutdown.");
            } catch (IOException ex) {
                throw new RuntimeException("Cannot create test server.");
            }
        }
        
    }
}
