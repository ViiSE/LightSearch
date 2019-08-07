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
package test.admin.panel;

import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import org.testng.annotations.Test;
import lightsearch.admin.panel.print.AdminPanelPrinter;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class AdminPanelPrinterTestNG {
    
    @Test
    public void print() {
        testBegin("AdminPanelPrinter", "print()");
        
        String message1 = "Hello! ";
        String message2 = "World!";
        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        printer.print(message1);
        printer.print(message2);
        
        System.out.println();
        
        testEnd("AdminPanelPrinter", "print()");
    }
    
    @Test
    public void println() {
        testBegin("AdminPanelPrinter", "println()");
        
        String message1 = "Hello! ";
        String message2 = "World!";
        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        printer.println(message1);
        printer.println(message2);
        
        testEnd("AdminPanelPrinter", "println()");
    }
}
