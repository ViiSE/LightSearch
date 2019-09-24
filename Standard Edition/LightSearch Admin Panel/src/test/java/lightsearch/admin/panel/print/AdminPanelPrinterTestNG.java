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
package lightsearch.admin.panel.print;

import lightsearch.admin.panel.print.AdminPanelPrinterInit;
import org.testng.annotations.Parameters;
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
    @Parameters({"firstMessage", "secondMessage"})
    public void print(String firstMessage, String secondMessage) {
        testBegin("AdminPanelPrinter", "print()");
        
        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        printer.print(firstMessage);
        printer.print(secondMessage);
        System.out.println();
        
        testEnd("AdminPanelPrinter", "print()");
    }
    
    @Test
    @Parameters({"firstMessage", "secondMessage"})
    public void println(String firstMessage, String secondMessage) {
        testBegin("AdminPanelPrinter", "println()");

        AdminPanelPrinter printer = AdminPanelPrinterInit.adminPanelPrinter();
        printer.println(firstMessage);
        printer.println(secondMessage);
        
        testEnd("AdminPanelPrinter", "println()");
    }
}
