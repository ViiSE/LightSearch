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
public class AdminParametersHolderTestNG {
    
    @Test
    public void adminSocket() {
        testBegin("AdminParametersHolder", "adminSocket()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        AdminParametersHolder adminParamHolder = DataProviderCreator.createDataProvider(AdminParametersHolder.class, serverDTO);
        assertNotNull(adminParamHolder.adminSocket(), "Admin socket is null!");
        System.out.println(adminParamHolder.adminSocket());
        
        testEnd("AdminParametersHolder", "adminSocket()");
    }
    
    @Test
    public void dataStream() {
        testBegin("AdminParametersHolder", "dataStream()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        AdminParametersHolder adminParamHolder = DataProviderCreator.createDataProvider(AdminParametersHolder.class, serverDTO);
        assertNotNull(adminParamHolder.dataStream(), "DataStream is null!");
        System.out.println(adminParamHolder.dataStream());
        
        System.out.println("Class: AdminParametersHolder");
        System.out.println("Method:dataStream(). Test END");
        
        testEnd("AdminParametersHolder", "dataStream()");
    }
    
    @Test
    public void commandHolder() {
        testBegin("AdminParametersHolder", "commandHolder()");

        LightSearchServerDTO serverDTO = DataProviderCreator.createDataProvider(LightSearchServerDTO.class);
        AdminParametersHolder adminParamHolder = DataProviderCreator.createDataProvider(AdminParametersHolder.class, serverDTO);
        assertNotNull(adminParamHolder.commandHolder(), "DataStream is null!");
        System.out.println(adminParamHolder.commandHolder());
        
        testEnd("AdminParametersHolder", "commandHolder()");
    }
}
