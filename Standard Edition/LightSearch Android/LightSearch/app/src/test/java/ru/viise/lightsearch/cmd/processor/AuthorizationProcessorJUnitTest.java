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

import java.util.Arrays;

import ru.viise.lightsearch.cmd.result.AuthorizationCommandResult;
import ru.viise.lightsearch.data.ClientCommandDTO;
import ru.viise.lightsearch.data.ClientCommandDTOInit;
import ru.viise.lightsearch.data.CommandAuthorizationDTO;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.exception.MessageRecipientException;
import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.type.MessageAuthorizationInit;
import test.rule.CreateCommandAuthorizationDTORule;
import test.server.processor.AuthorizationProcessorTest;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class AuthorizationProcessorJUnitTest {

    private ClientCommandDTO clientCommandDTO;
    private CommandDTO commandDTO;

    private MessageSender messageSender = mock(MessageSender.class);
    private MessageRecipient messageRecipient = mock(MessageRecipient.class);

    @Rule
    public final CreateCommandAuthorizationDTORule commandAuthorizationDTORule =
            new CreateCommandAuthorizationDTORule();

    @Before
    public void setUp() throws MessageSenderException, MessageRecipientException {
        commandDTO = commandAuthorizationDTORule.getCommandAuthorizationDTO();
        CommandAuthorizationDTO cmdDTO = commandAuthorizationDTORule.getCommandAuthorizationDTO();
        String rawMessage = MessageAuthorizationInit.messageAuthorization(cmdDTO).message();

        AuthorizationProcessorTest authProcTest = new AuthorizationProcessorTest();
        doNothing().when(messageSender).sendMessage(isA(String.class));
        when(messageRecipient.acceptMessage()).thenReturn(authProcTest.apply(rawMessage));

        clientCommandDTO =
                ClientCommandDTOInit.clientCommandDTO("123456789123456", messageSender, messageRecipient);
    }

    @Test
    public void apply() {
        testBegin("AuthorizationProcessor", "apply()");

        AuthorizationCommandResult result = (AuthorizationCommandResult)
                new AuthorizationProcessor(clientCommandDTO).apply(commandDTO);
        assertThat(result).isNotNull();
        assertThat(result.message()).isNotNull();

        System.out.println("Result:");
        System.out.println("result.isDone: " + result.isDone());
        System.out.println("result.message: " + result.message());
        System.out.println("result.isReconnect: " + result.isReconnect());
        System.out.println("result.reconnectDTO: " + result.reconnectDTO());
        System.out.println("result.skladList: " + Arrays.toString(result.skladList()));
        System.out.println("result.TKList: " + Arrays.toString(result.TKList()));

        testEnd("AuthorizationProcessor", "apply()");
    }
}
