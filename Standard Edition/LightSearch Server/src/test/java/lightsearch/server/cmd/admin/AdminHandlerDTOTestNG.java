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
package lightsearch.server.cmd.admin;

import lightsearch.server.data.AdminHandlerDTO;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import test.data.DataProviderCreator;

import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminHandlerDTOTestNG {

    @Test
    @Parameters({"logDirectoryName"})
    public void adminDAO(String logDirectoryName) {
        testBegin("AdminHandlerDTO", "adminDAO()");
        
        AdminHandlerDTO adminHandlerDTO = DataProviderCreator.createDataProvider(AdminHandlerDTO.class, logDirectoryName);
        System.out.println("adminHandlerDTO.adminDAO():  " + adminHandlerDTO.adminDAO());
        
        testEnd("AdminHandlerDTO", "adminDAO()");
    }
    
    @Test
    @Parameters({"logDirectoryName"})
    public void adminParametersHolder(String logDirectoryName) {
        testBegin("AdminHandlerDTO", "adminParametersHolder()");
        
        System.out.println("Class: AdminHandlerDTO");
        System.out.println("Method:adminParametersHolder(). Test BEGIN");
        
        AdminHandlerDTO adminHandlerDTO = DataProviderCreator.createDataProvider(AdminHandlerDTO.class, logDirectoryName);
        System.out.println("adminHandlerDTO.adminParametersHolder():  " + adminHandlerDTO.adminParametersHolder());
        
        testEnd("AdminHandlerDTO", "adminParametersHolder()");
    }
    
    @Test
    @Parameters({"logDirectoryName"})
    public void currentDateTime(String logDirectoryName) {
        testBegin("AdminHandlerDTO", "currentDateTime()");
        
        AdminHandlerDTO adminHandlerDTO = DataProviderCreator.createDataProvider(AdminHandlerDTO.class, logDirectoryName);
        System.out.println("adminHandlerDTO.currentDateTime():  " + adminHandlerDTO.currentDateTime());
        
        testEnd("AdminHandlerDTO", "currentDateTime()");
    }
    
    @Test
    @Parameters({"logDirectoryName"})
    public void threadManager(String logDirectoryName) {
        testBegin("AdminHandlerDTO", "threadManager()");
        
        AdminHandlerDTO adminHandlerDTO = DataProviderCreator.createDataProvider(AdminHandlerDTO.class, logDirectoryName);
        System.out.println("adminHandlerDTO.threadManager():  " + adminHandlerDTO.threadManager());
        
        testEnd("AdminHandlerDTO", "threadManager()");
    }
    
    @Test
    @Parameters({"logDirectoryName"})
    public void threadParametersHolder(String logDirectoryName) {
        testBegin("AdminHandlerDTO", "threadParametersHolder()");
        
        AdminHandlerDTO adminHandlerDTO = DataProviderCreator.createDataProvider(AdminHandlerDTO.class, logDirectoryName);
        System.out.println("adminHandlerDTO.threadParametersHolder():  " + adminHandlerDTO.threadParametersHolder());
        
        testEnd("AdminHandlerDTO", "threadParametersHolder()");
    }
}
