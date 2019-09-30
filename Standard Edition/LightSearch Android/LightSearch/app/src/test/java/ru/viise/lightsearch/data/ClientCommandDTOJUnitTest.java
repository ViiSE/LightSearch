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

package ru.viise.lightsearch.data;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import test.rule.CreateClientCommandDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class ClientCommandDTOJUnitTest {

    private ClientCommandDTO clientCommandDTO;

    @Rule
    public final CreateClientCommandDTORule clientCommandDTORule =
            new CreateClientCommandDTORule();

    @Before
    public void setUp() {
        clientCommandDTO = clientCommandDTORule.getClientCommandDTO();
    }

    @Test
    public void IMEI() {
        testBegin("ClientCommandDTO", "IMEI()");

        String IMEI = clientCommandDTO.IMEI();
        assertThat(IMEI).isNotNull();
        assertThat(IMEI).isNotEmpty();

        System.out.println("IMEI: " + IMEI);

        testEnd("ClientCommandDTO", "IMEI()");
    }

    @Test
    public void messageSender() {
        testBegin("ClientCommandDTO", "messageSender()");

        MessageSender messageSender = clientCommandDTO.messageSender();
        assertThat(messageSender).isNotNull();

        System.out.println("messageSender: " + messageSender);

        testEnd("ClientCommandDTO", "messageSender()");
    }

    @Test
    public void messageRecipient() {
        testBegin("ClientCommandDTO", "messageRecipient()");

        MessageRecipient messageRecipient = clientCommandDTO.messageRecipient();
        assertThat(messageRecipient).isNotNull();

        System.out.println("messageRecipient: " + messageRecipient);

        testEnd("ClientCommandDTO", "messageRecipient()");
    }
}
