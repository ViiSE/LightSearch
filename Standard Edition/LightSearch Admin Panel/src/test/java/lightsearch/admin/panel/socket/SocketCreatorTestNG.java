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
package lightsearch.admin.panel.socket;

import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreator;
import lightsearch.admin.panel.data.creator.ConnectionDTOCreatorInit;
import lightsearch.admin.panel.exception.SocketException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.Socket;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class SocketCreatorTestNG {
    
    private ConnectionDTO connectionDTO;
    
    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpMethod(String ip, int port) {
        ConnectionDTOCreator connDTOCreator = ConnectionDTOCreatorInit.connectionDTOCreatorTest(ip, port);
        assertNotNull(connDTOCreator, "ConnectionDTOCreator is null!");
        connectionDTO = connDTOCreator.createConnectionDTO();
    }
    
    @Test
    public void createSocket() {
        testBegin("SocketCreator", "createSocket()");
        
        try {
            SocketCreator socketCreator = SocketCreatorInit.socketCreator(connectionDTO);
            try (Socket socket = socketCreator.createSocket()) {
                System.out.println("Socket created: socket = " + socket);
            } catch(IOException ex) {
                catchMessage(ex);
            }
        } catch(SocketException ex) {
            catchMessage(ex);
        }
        
        testEnd("SocketCreator", "createSocket()");
    }
}
