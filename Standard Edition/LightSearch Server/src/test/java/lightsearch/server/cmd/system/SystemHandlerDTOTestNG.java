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

import lightsearch.server.data.SystemHandlerDTO;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SystemHandlerDTOTestNG {
    
    @Test
    public void systemParametersHolder() {
        testBegin("SystemHandlerDTO", "systemParametersHolder()");
        
        SystemHandlerDTO systemHandlerDTO = DataProviderCreator.createDataProvider(SystemHandlerDTO.class);
        System.out.println("clientHandlerDTO.clientParametersHolder(): " + systemHandlerDTO.systemParametersHolder());
        
        testEnd("SystemHandlerDTO", "systemParametersHolder()");
    }
    
    @Test
    public void currentDateTime() {
        testBegin("SystemHandlerDTO", "currentDateTime()");

        SystemHandlerDTO systemHandlerDTO = DataProviderCreator.createDataProvider(SystemHandlerDTO.class);
        System.out.println("clientHandlerDTO.currentDateTime(): " + systemHandlerDTO.currentDateTime());
        
        testEnd("SystemHandlerDTO", "currentDateTime()");
    }
    
    @Test
    public void threadManager() {
        testBegin("SystemHandlerDTO", "threadManager()");

        SystemHandlerDTO systemHandlerDTO = DataProviderCreator.createDataProvider(SystemHandlerDTO.class);
        System.out.println("clientHandlerDTO.threadManager():  " + systemHandlerDTO.threadManager());
        
        testEnd("SystemHandlerDTO", "threadManager()");
    }
    
    @Test
    public void threadParametersHolder() {
        testBegin("SystemHandlerDTO", "threadParametersHolder()");

        SystemHandlerDTO systemHandlerDTO = DataProviderCreator.createDataProvider(SystemHandlerDTO.class);
        System.out.println("clientHandlerDTO.threadParametersHolder(): " + systemHandlerDTO.threadParametersHolder());
        
        testEnd("SystemHandlerDTO", "threadParametersHolder()");
    }
}
