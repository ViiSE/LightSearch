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
public class ClientParametersHolderTestNG {
    
    @Test
    public void clientSocket() {
        testBegin("ClientParametersHolder", "clientSocket()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        ClientParametersHolder clientParamHolder = DataProviderCreator.createDataProvider(ClientParametersHolder.class, serverDTO);
        assertNotNull(clientParamHolder.clientSocket(), "Client socket is null!");
        System.out.println(clientParamHolder.clientSocket());
        
        testEnd("ClientParametersHolder", "clientSocket()");
    }
    
    @Test
    public void dataStream() {
        testBegin("ClientParametersHolder", "dataStream()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        ClientParametersHolder clientParamHolder = DataProviderCreator.createDataProvider(ClientParametersHolder.class, serverDTO);
        assertNotNull(clientParamHolder.dataStream(), "Data stream is null!");
        System.out.println(clientParamHolder.dataStream());
        
        testEnd("ClientParametersHolder", "dataStream()");
    }
    
    @Test
    public void commandHolder() {
        testBegin("ClientParametersHolder", "commandHolder()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        ClientParametersHolder clientParamHolder = DataProviderCreator.createDataProvider(ClientParametersHolder.class, serverDTO);
        assertNotNull(clientParamHolder.commandHolder(), "Command holder is null!");
        System.out.println(clientParamHolder.commandHolder());
        
        testEnd("ClientParametersHolder", "commandHolder()");
    }
}
