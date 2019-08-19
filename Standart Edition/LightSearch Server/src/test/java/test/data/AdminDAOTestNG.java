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
package test.data;

import lightsearch.server.data.AdminDAO;
import lightsearch.server.data.AdminDAOInit;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminDAOTestNG {
    
    public class Admin {
        AdminDAO adminDAO;
        
        public Admin(AdminDAO adminDAO) {
            this.adminDAO = adminDAO;
        }
        
        public void changeAdminDAO(String name, boolean isFirst) {
            adminDAO.setName(name);
            adminDAO.setIsFirst(isFirst);
        }
        
        public String name() {
            return adminDAO.name();
        }
        
        public boolean isFirst() {
            return adminDAO.isFirst();
        }
    }
    
    @Test
    public void name() {
        testBegin("AdminDAO", "name()");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        Admin admin = new Admin(adminDAO);
        System.out.println("Before: adminDAO: name " + adminDAO.name());
        System.out.println("        amdin: name " + admin.name());
        adminDAO.setName("admin");
        System.out.println("After: adminDAO: name " + adminDAO.name());
        System.out.println("        amdin: name " + admin.name());
        
        testEnd("AdminDAO", "name()");
    }
    
    @Test
    public void isFirst() {
        testBegin("AdminDAO", "isFirst()");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        Admin admin = new Admin(adminDAO);
        System.out.println("Before: adminDAO: isFirst " + adminDAO.isFirst());
        System.out.println("        amdin: isFirst " + admin.isFirst());
        adminDAO.setIsFirst(false);
        System.out.println("After: adminDAO: isFirst " + adminDAO.isFirst());
        System.out.println("        amdin: isFirst " + admin.isFirst());
        
        testEnd("AdminDAO", "isFirst()");
    }
    
    @Test
    public void setName() {
        testBegin("AdminDAO", "setName()");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        adminDAO.setName("admin");
        System.out.println("Before: adminDAO: setName: name " + adminDAO.name());
        adminDAO.setName("newAdmin");
        System.out.println("After: adminDAO: name " + adminDAO.name());
        
        testEnd("AdminDAO", "setName()");
    }
    
    @Test
    public void setIsFirst() {
        testBegin("AdminDAO", "setIsFirst()");
        
        AdminDAO adminDAO = AdminDAOInit.adminDAO();
        System.out.println("Before: adminDAO: setIsFirst: isFirst " + adminDAO.isFirst());
        adminDAO.setIsFirst(false);
        System.out.println("After: adminDAO: setIsFirst: isFirst " + adminDAO.isFirst());   
        
        testEnd("AdminDAO", "setIsFirst()");
    }
}
