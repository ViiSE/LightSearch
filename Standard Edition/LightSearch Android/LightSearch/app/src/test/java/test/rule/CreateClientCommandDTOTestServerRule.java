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

package test.rule;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.data.ClientCommandDTO;
import ru.viise.lightsearch.data.ClientCommandDTOInit;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageRecipientInit;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.MessageSenderInit;

public class CreateClientCommandDTOTestServerRule implements TestRule {

    private ClientCommandDTO clientCommandDTO;

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                String IMEI = "1234567890123456";
                Socket clientSocket = new Socket("127.0.0.1", 50001);
                MessageSender messageSender = MessageSenderInit.messageSender(
                        new DataOutputStream(clientSocket.getOutputStream()));
                MessageRecipient messageRecipient = MessageRecipientInit.messageRecipient(
                        new DataInputStream(clientSocket.getInputStream()));

                clientCommandDTO =
                        ClientCommandDTOInit.clientCommandDTO(IMEI, messageSender, messageRecipient);
                base.evaluate();
            }
        };
    }

    public ClientCommandDTO getClientCommandDTO() {
        return clientCommandDTO;
    }
}
