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

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminDAOTestNG {
    
    @Test
    @Parameters({"name"})
    public void name(String name) {
        testBegin("AdminDAO", "name()");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        adminDAO.setName(name);
        System.out.println("adminDAO: name " + adminDAO.name());

        testEnd("AdminDAO", "name()");
    }
    
    @Test
    @Parameters({"isFirst"})
    public void isFirst(boolean isFirst) {
        testBegin("AdminDAO", "isFirst()");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        adminDAO.setIsFirst(isFirst);
        System.out.println("adminDAO: isFirst " + adminDAO.isFirst());

        testEnd("AdminDAO", "isFirst()");
    }
    
    @Test
    @Parameters({"name"})
    public void setName(String name) {
        testBegin("AdminDAO", "setName()");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        adminDAO.setName(name);
        System.out.println("adminDAO: name " + adminDAO.name());
        
        testEnd("AdminDAO", "setName()");
    }
    
    @Test
    @Parameters({"isFirst"})
    public void setIsFirst(boolean isFirst) {
        testBegin("AdminDAO", "setIsFirst()");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        adminDAO.setIsFirst(isFirst);
        System.out.println("adminDAO: isFirst " + adminDAO.isFirst());
        
        testEnd("AdminDAO", "setIsFirst()");
    }
}
