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
package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.time.CurrentDateTimeInit;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class CurrentDateTimeTestNG {
    
    @Test
    public void dateTimeWithDot() {
        testBegin("CurrentDateTime", "dateTimeWithDot()");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        String curDateTime = currentDateTime.dateTimeWithDot();
        assertNotNull(curDateTime, "Current DateTime is null!");
        assertFalse(curDateTime.equals(""), "Current DateTime is null!");
        System.out.println("dateTimeWithDot: " + curDateTime);
        
        testEnd("CurrentDateTime", "dateTimeWithDot()");
    }
    
    @Test
    public void dateTimeInStandartFormat() {
        testBegin("CurrentDateTime", "dateTimeInStandartFormat()");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        String curDateTime = currentDateTime.dateTimeInStandartFormat();
        assertNotNull(curDateTime, "Current DateTime is null!");
        assertFalse(curDateTime.equals(""), "Current DateTime is null!");
        System.out.println("dateTimeInStandartFormat: " + curDateTime);
        
        testEnd("CurrentDateTime", "dateTimeInStandartFormat()");
    }
    
    @Test
    public void dateLog() {
        testBegin("CurrentDateTime", "dateLog()");
        
        CurrentDateTime currentDateTime = CurrentDateTimeInit.currentDateTime();
        String curDate = currentDateTime.dateLog();
        assertNotNull(curDate, "Current Date is null!");
        assertFalse(curDate.equals(""), "Current Date is null!");
        System.out.println("dateLog: " + curDate);
        
        testEnd("CurrentDateTime", "dateLog()");
    }
}
