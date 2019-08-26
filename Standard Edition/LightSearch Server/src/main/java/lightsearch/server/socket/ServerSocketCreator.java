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

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Создает серверный сокет для LightSearch Server.
 * @author ViiSE
 * @see lightsearch.server.listener.LightSearchServerListener
 * @see lightsearch.server.listener.LightSearchServerListenerSocketDefaultImpl
 * @since 1.0.0
 */
public class ServerSocketCreator {
    
    public static ServerSocket createServerSocket(int serverPort) {
        try {
            return new ServerSocket(serverPort);
        }
        catch(IOException ex) {
            throw new RuntimeException("ServerSocket not create: " + ex.getMessage());
        }
    }
}
