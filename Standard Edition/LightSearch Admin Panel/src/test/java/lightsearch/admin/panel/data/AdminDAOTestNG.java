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
package lightsearch.admin.panel.data;

import lightsearch.admin.panel.data.AdminDAO;
import lightsearch.admin.panel.data.AdminDAOInit;
import static org.testng.Assert.*;

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
        assertNotNull(adminDAO, "AdminDAO is null!");

        assertNotNull(name, "Name is null!");
        
        adminDAO.setName(name);
        String admDAOName = adminDAO.name();
        assertNotNull(admDAOName, "AdminDAO: name is null!");
        
        System.out.println("AdminDAO: name(): " + adminDAO.name());
        
        testEnd("AdminDAO", "name()");
    }
}
