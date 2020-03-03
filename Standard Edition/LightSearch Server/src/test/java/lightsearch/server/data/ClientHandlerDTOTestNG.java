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
package lightsearch.server.data;

import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class ClientHandlerDTOTestNG {
    
    @Test
    public void clientDAO() {
        testBegin("ClientHandlerDTO", "clientDAO()");
        
        ClientHandlerDTO clientHandlerDTO = DataProviderCreator.createDataProvider(ClientHandlerDTO.class);
        assertNotNull(clientHandlerDTO, "ClientHandlerDTO is null!");
        System.out.println("clientHandlerDTO.clientDAO():  " + clientHandlerDTO.clientDAO());
        
        testEnd("ClientHandlerDTO", "clientDAO()");
    }
    
    @Test
    public void clientParametersHolder() {
        testBegin("ClientHandlerDTO", "clientParametersHolder()");

        ClientHandlerDTO clientHandlerDTO = DataProviderCreator.createDataProvider(ClientHandlerDTO.class);
        assertNotNull(clientHandlerDTO, "ClientHandlerDTO is null!");
        System.out.println("clientHandlerDTO.clientParametersHolder():  " + clientHandlerDTO.clientParametersHolder());
        
        testEnd("ClientHandlerDTO", "clientParametersHolder()");
    }
    
    @Test
    public void currentDateTime() {
        testBegin("ClientHandlerDTO", "currentDateTime()");

        ClientHandlerDTO clientHandlerDTO = DataProviderCreator.createDataProvider(ClientHandlerDTO.class);
        assertNotNull(clientHandlerDTO, "ClientHandlerDTO is null!");
        System.out.println("clientHandlerDTO.currentDateTime():  " + clientHandlerDTO.currentDateTime());
        
        testEnd("ClientHandlerDTO", "currentDateTime()");
    }
    
    @Test
    public void threadManager() {
        testBegin("ClientHandlerDTO", "threadManager()");

        ClientHandlerDTO clientHandlerDTO = DataProviderCreator.createDataProvider(ClientHandlerDTO.class);
        assertNotNull(clientHandlerDTO, "ClientHandlerDTO is null!");
        System.out.println("clientHandlerDTO.threadManager():  " + clientHandlerDTO.threadManager());
        
        testEnd("ClientHandlerDTO", "threadManager()");
    }
    
    @Test
    public void threadParametersHolder() {
        testBegin("ClientHandlerDTO", "threadParametersHolder()");

        ClientHandlerDTO clientHandlerDTO = DataProviderCreator.createDataProvider(ClientHandlerDTO.class);
        assertNotNull(clientHandlerDTO, "ClientHandlerDTO is null!");
        System.out.println("clientHandlerDTO.threadParametersHolder():  " + clientHandlerDTO.threadParametersHolder());
        
        testEnd("ClientHandlerDTO", "threadParametersHolder()");
    }
}
