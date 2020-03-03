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
package lightsearch.server.socket;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.net.ServerSocket;

import static org.testng.Assert.assertFalse;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ServerSocketCreatorTestNG {
    
    @Test
    @Parameters({"serverPort"})
    public void createServerSocket(int serverPort) {
        testBegin("ServerSocketCreator", "createServerSocket()");

        assertFalse(serverPort < 0 || serverPort > 65535, "Wrong server port number!");
        ServerSocket socket = ServerSocketCreator.createServerSocket(serverPort);
        System.out.println("Server socket: " + socket);
        
        testEnd("ServerSocketCreator", "createServerSocket()");
    }
}
