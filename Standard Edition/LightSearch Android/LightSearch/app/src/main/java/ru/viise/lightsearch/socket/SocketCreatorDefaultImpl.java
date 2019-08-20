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
package ru.viise.lightsearch.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import ru.viise.lightsearch.data.ConnectionDTO;
import ru.viise.lightsearch.exception.SocketException;

/**
 *
 * @author ViiSE
 */
public class SocketCreatorDefaultImpl implements SocketCreator {

    private final int TIMEOUT = 10000;
    private final int MAX_TRY = 3;

    private final String ip;
    private final int port;
    
    public SocketCreatorDefaultImpl(ConnectionDTO connectionDTO) {
        ip = connectionDTO.ip();
        port = connectionDTO.port();
    }

    @Override
    public Socket createSocket() throws SocketException {
        for(int i = 0; i < MAX_TRY; i++)
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(ip, port), TIMEOUT);
                return socket;
            } catch (IOException ignored) {}
        throw new SocketException("Cannot create socket");
    }
}
