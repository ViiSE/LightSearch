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
package test.system;

import lightsearch.server.cmd.system.SystemCommand;
import lightsearch.server.cmd.system.SystemCommandConverter;
import lightsearch.server.cmd.system.SystemCommandConverterInit;
import lightsearch.server.exception.CommandConverterException;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SystemCommandConverterTestNG {
    
    private String initMessage() {
        String message = "{"
                            + "\"command\":\"clear\""
                        + "}";
        return message;
    }
    
    @Test
    public void convertToClientCommand() {
        try {
            testBegin("SystemCommandConverter", "convertToSystemCommand()");
            
            SystemCommandConverter systemConverter = SystemCommandConverterInit.systemCommandConverter();
            String message = initMessage();
            assertNotNull(message, "Message is null!");
            assertFalse(message.equals(""), "Message is null!");
            SystemCommand systemCommand = systemConverter.convertToSystemCommand(message);
            System.out.println("SystemCommand: ");
            System.out.println("\t systemCommand.command: " + systemCommand.command());    
        } catch (CommandConverterException ex) {
            System.out.println("CATCH! " + ex.getMessage());
        }

        testEnd("SystemCommandConverter", "convertToSystemCommand()");
    }
}
