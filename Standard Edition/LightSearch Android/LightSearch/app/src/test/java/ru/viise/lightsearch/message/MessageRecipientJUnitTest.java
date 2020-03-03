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

import java.io.DataInputStream;
import java.io.IOException;

import ru.viise.lightsearch.exception.MessageRecipientException;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.TestMessage.catchMessage;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class MessageRecipientJUnitTest {

    private DataInputStream dataInputStream = mock(DataInputStream.class);

    @Before
    public void setUp() throws IOException {
        when(dataInputStream.readUTF()).thenReturn("OK");
    }

    @Test
    public void acceptMessage() {
        testBegin("MessageRecipient", "acceptMessage()");

        try {
            MessageRecipient msgRecipient = MessageRecipientInit.messageRecipient(dataInputStream);
            assertThat(msgRecipient).isNotNull();

            String acceptMsg = msgRecipient.acceptMessage();
            assertThat(acceptMsg).isNotNull();

            assertThat(acceptMsg).isEqualTo("OK");

            System.out.println("Accepted message: " + acceptMsg);

        } catch (MessageRecipientException ex) {
            catchMessage(ex);
        }

        testEnd("MessageRecipient", "acceptMessage()");
    }
}
