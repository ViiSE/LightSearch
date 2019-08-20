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

import java.net.Socket;
import lightsearch.server.data.stream.DataStream;

/**
 *
 * @author ViiSE
 */
public class ConnectionIdentifierResult {
    
    private final Socket clientSocket;
    private final String identifier;
    private final DataStream dataStream;
    
    public ConnectionIdentifierResult(Socket clienSocket, String identifier, DataStream dataStream) {
        this.clientSocket = clienSocket;
        this.identifier = identifier;
        this.dataStream = dataStream;
    }
    
    public Socket clientSocket() {
        return clientSocket;
    }
    
    public String identifier() {
        return identifier;
    }
    
    public DataStream dataStream() {
        return dataStream;
    }
}
