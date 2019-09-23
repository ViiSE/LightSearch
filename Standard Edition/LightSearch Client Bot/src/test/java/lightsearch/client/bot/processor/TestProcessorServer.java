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

package lightsearch.client.bot.processor;

import lightsearch.client.bot.exception.MessageRecipientException;
import lightsearch.client.bot.exception.MessageSenderException;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageRecipientInit;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.message.MessageSenderInit;
import test.TestUtils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static test.message.TestMessage.catchMessage;

public class TestProcessorServer implements Runnable {

    private final int port;
    private final String answerMessage;

    public TestProcessorServer(int port, String answerMessage) {
        this.port = port;
        this.answerMessage = answerMessage;
    }

    @Override
    public void run() {
        try(ServerSocket sock = new ServerSocket(port)) {
            System.out.println("Test server started.");
            Socket clientSock = sock.accept();
            System.out.println("CONNECT!");
            MessageRecipient msgRecipient =
                    MessageRecipientInit.messageRecipient(new DataInputStream(clientSock.getInputStream()));

            MessageSender msgSender =
                    MessageSenderInit.messageSender(new DataOutputStream(clientSock.getOutputStream()));

            System.out.println("Client send: " + msgRecipient.acceptMessage());
            msgSender.sendMessage(answerMessage);

            TestUtils.sleep(5000);

            System.out.println("Server shutdown.");
        } catch (IOException | MessageSenderException | MessageRecipientException ex) {
            catchMessage(ex);
        }
    }
}
