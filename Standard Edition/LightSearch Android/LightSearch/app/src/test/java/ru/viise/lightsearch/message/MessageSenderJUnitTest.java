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

package ru.viise.lightsearch.message;

import org.junit.Before;
import org.junit.Test;

import java.io.DataOutputStream;
import java.io.IOException;

import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.type.MessageSearch;
import ru.viise.lightsearch.message.type.MessageSearchInit;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static test.TestMessage.catchMessage;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class MessageSenderJUnitTest {

    private DataOutputStream dataOutputStream = mock(DataOutputStream.class);

    @Before
    public void setUp() throws IOException {
        doNothing().when(dataOutputStream).writeUTF(isA(String.class));
        doNothing().when(dataOutputStream).flush();
    }

    @Test
    public void sendMessage() {
        testBegin("MessageSender", "sendMessage()");

        try {
            MessageSender msgSender = MessageSenderInit.messageSender(dataOutputStream);
            assertThat(msgSender).isNotNull();

            String message = "{\"identifier\":\"client\"}";
            msgSender.sendMessage(message);

            System.out.println("Send is OK!");
        } catch (MessageSenderException ex) {
            catchMessage(ex);
        }

        testEnd("MessageSender", "sendMessage()");
    }
}
