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

import java.io.IOException;
import java.net.Socket;
import lightsearch.admin.panel.data.ConnectionDTO;
import lightsearch.admin.panel.exception.SocketException;

/**
 *
 * @author ViiSE
 */
public class SocketCreatorDefaultImpl implements SocketCreator {

    private final ConnectionDTO connectionDTO;
    
    public SocketCreatorDefaultImpl(ConnectionDTO connectionDTO) {
        this.connectionDTO = connectionDTO;
    }

    @Override
    public Socket createSocket() throws SocketException {
        try {
            Socket socket = new Socket(connectionDTO.ip(), connectionDTO.port());
            return socket;
        }
        catch (IOException ex) {
            throw new SocketException("Error: " + ex.getMessage() + "\n. Try again.");
        }
    }    
}
