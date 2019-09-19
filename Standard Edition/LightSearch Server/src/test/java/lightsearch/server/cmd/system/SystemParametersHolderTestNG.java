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
package lightsearch.server.cmd.system;

import lightsearch.server.data.LightSearchServerDTO;
import lightsearch.server.data.SystemParametersHolder;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SystemParametersHolderTestNG {
    
    @Test
    public void clientSocket() {
        testBegin("SystemParametersHolder", "clientSocket()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        SystemParametersHolder systemParamHolder =
                DataProviderCreator.createDataProvider(SystemParametersHolder.class, serverDTO);
        assertNotNull(systemParamHolder.systemSocket(), "System socket is null!");
        System.out.println(systemParamHolder.systemSocket());
        
        testEnd("SystemParametersHolder", "clientSocket()");
    }
    
    @Test
    public void dataStream() {
        testBegin("SystemParametersHolder", "dataStream()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        SystemParametersHolder systemParamHolder =
                DataProviderCreator.createDataProvider(SystemParametersHolder.class, serverDTO);
        assertNotNull(systemParamHolder.dataStream(), "Data stream is null!");
        System.out.println(systemParamHolder.dataStream());
        
        testEnd("SystemParametersHolder", "dataStream()");
    }
    
    @Test
    public void commandHolder() {
        testBegin("SystemParametersHolder", "commandHolder()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        SystemParametersHolder systemParamHolder =
                DataProviderCreator.createDataProvider(SystemParametersHolder.class, serverDTO);
        assertNotNull(systemParamHolder.commandHolder(), "Command holder is null!");
        System.out.println(systemParamHolder.commandHolder());
        
        testEnd("SystemParametersHolder", "commandHolder()");
    }
}
