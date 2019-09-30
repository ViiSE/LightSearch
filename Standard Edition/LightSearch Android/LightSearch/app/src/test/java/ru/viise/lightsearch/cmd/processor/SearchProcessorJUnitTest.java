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

package ru.viise.lightsearch.cmd.processor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.viise.lightsearch.cmd.result.SearchCommandResult;
import ru.viise.lightsearch.data.ClientCommandDTO;
import ru.viise.lightsearch.data.ClientCommandDTOInit;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.data.CommandSearchDTO;
import ru.viise.lightsearch.exception.MessageRecipientException;
import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.type.MessageSearchInit;
import test.rule.CreateCommandSearchDTORule;
import test.server.processor.SearchProcessorTest;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class SearchProcessorJUnitTest {

    private ClientCommandDTO clientCommandDTO;
    private CommandDTO commandDTO;

    private MessageSender messageSender = mock(MessageSender.class);
    private MessageRecipient messageRecipient = mock(MessageRecipient.class);

    @Rule
    public final CreateCommandSearchDTORule commandSearchDTORule = new CreateCommandSearchDTORule();

    @Before
    public void setUp() throws MessageSenderException, MessageRecipientException {
        String IMEI = "123456789123456";
        commandDTO = commandSearchDTORule.getCommandSearchDTO();
        CommandSearchDTO cmdDTO = commandSearchDTORule.getCommandSearchDTO();
        String rawMessage = MessageSearchInit.messageSearch(IMEI, cmdDTO).message();

        SearchProcessorTest searchProcTest = new SearchProcessorTest();
        doNothing().when(messageSender).sendMessage(isA(String.class));
        when(messageRecipient.acceptMessage()).thenReturn(searchProcTest.apply(rawMessage));

        clientCommandDTO =
                ClientCommandDTOInit.clientCommandDTO(IMEI, messageSender, messageRecipient);
    }

    @Test
    public void apply() {
        testBegin("SearchProcessor", "apply()");

        SearchCommandResult result = (SearchCommandResult)
                new SearchProcessor(clientCommandDTO).apply(commandDTO);
        assertThat(result).isNotNull();

        System.out.println("result.records: " + result.records());
        System.out.println("result.subdivision: " + result.subdivision());
        System.out.println("result.isReconnect: " + result.isReconnect());
        System.out.println("result.reconnectDTO: " + result.reconnectDTO());

        testEnd("SearchProcessor", "apply()");
    }
}
