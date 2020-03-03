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
package lightsearch.server.cmd.client;

import lightsearch.server.exception.CommandConverterException;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.*;

/**
 *
 * @author ViiSE
 */
public class ClientCommandConverterTestNG {

    @Test
    @Parameters({"bigMessage"})
    public void convertToClientCommand(String message) {
        try {
            testBegin("ClientCommandConverter", "convertToClientCommand()");
            
            ClientCommandConverter clientConverter = ClientCommandConverterInit.clientCommandConverter();
            assertNotNull(message, "Message is null!");
            assertNotEquals(message, "", "Message is null!");
            ClientCommand clientCommand = clientConverter.convertToClientCommand(message);
            System.out.println("ClientCommand: ");
            System.out.println("\t clientCommand.IMEI: " + clientCommand.IMEI());
            System.out.println("\t clientCommand.ip: " + clientCommand.ip());
            System.out.println("\t clientCommand.os: " + clientCommand.os());
            System.out.println("\t clientCommand.model: " + clientCommand.model());
            System.out.println("\t clientCommand.username: " + clientCommand.username());
            System.out.println("\t clientCommand.password: " + clientCommand.password());
            System.out.println("\t clientCommand.sklad: " + clientCommand.sklad());
            System.out.println("\t clientCommand.TK: " + clientCommand.TK());
            System.out.println("\t clientCommand.barcode: " + clientCommand.barcode());
        } catch (CommandConverterException ex) {
            catchMessage(ex);
        }

        testEnd("ClientCommandConverter", "convertToClientCommand()");
    }
}
