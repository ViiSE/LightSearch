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
package lightsearch.client.bot.data;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ConnectionDTOTestNG {
    
    private ConnectionDTO connDTO;
    
    @BeforeClass
    @Parameters({"ip", "port"})
    public void setUpClass(String ip, int port) {
        connDTO = ConnectionDTOInit.connectDTO(ip, port);
    }
    
    @Test
    public void ip() {
        testBegin("ConnectionDTO", "ip()");
        
        assertNotNull(connDTO.ip(), "IP is null!");
        System.out.println("IP: " + connDTO.ip());
        
        testEnd("ConnectionDTO", "ip()");
    }
    
    @Test
    public void port() {
        testBegin("ConnectionDTO", "port()");
        
        assertFalse(connDTO.port() < 1024 || connDTO.port() > 65535, "Port is not valid!");
        System.out.println("Port: " + connDTO.port());
        
        testEnd("ConnectionDTO", "port()");
    }
}
