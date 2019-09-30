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

import java.util.ArrayList;
import java.util.List;

import ru.viise.lightsearch.cmd.result.ConfirmSoftCheckProductsResult;
import ru.viise.lightsearch.cmd.result.OpenSoftCheckCommandResult;
import ru.viise.lightsearch.data.ClientCommandDTO;
import ru.viise.lightsearch.data.ClientCommandDTOInit;
import ru.viise.lightsearch.data.CommandConfirmSoftCheckRecordsDTO;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.exception.MessageRecipientException;
import ru.viise.lightsearch.exception.MessageSenderException;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;
import ru.viise.lightsearch.message.type.MessageConfirmSoftCheckProductsInit;
import test.rule.CreateCommandConfirmSoftCheckRecordsDTORule;
import test.server.processor.ConfirmSoftCheckProductsProcessorTest;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class ConfirmSoftCheckProcessorJUnitTest {

    private List<SoftCheckRecord> records = new ArrayList<>();
    private ClientCommandDTO clientCommandDTO;
    private CommandDTO commandDTO;

    private MessageSender messageSender = mock(MessageSender.class);
    private MessageRecipient messageRecipient = mock(MessageRecipient.class);

    @Rule
    public final CreateCommandConfirmSoftCheckRecordsDTORule commandConfirmSCRecsDTORule =
            new CreateCommandConfirmSoftCheckRecordsDTORule(records);

    @Before
    public void setUp() throws MessageSenderException, MessageRecipientException {
        String IMEI = commandConfirmSCRecsDTORule.getIMEI();
        commandDTO = commandConfirmSCRecsDTORule.getCommandConfirmSoftCheckRecordsDTO();
        CommandConfirmSoftCheckRecordsDTO cmdDTO =
                commandConfirmSCRecsDTORule.getCommandConfirmSoftCheckRecordsDTO();
        String rawMessage = MessageConfirmSoftCheckProductsInit.
                messageConfirmSoftCheckProducts(IMEI, cmdDTO).message();

        ConfirmSoftCheckProductsProcessorTest confSCProdsProcTest =
                new ConfirmSoftCheckProductsProcessorTest();
        doNothing().when(messageSender).sendMessage(isA(String.class));
        when(messageRecipient.acceptMessage()).thenReturn(confSCProdsProcTest.apply(rawMessage));

        clientCommandDTO =
                ClientCommandDTOInit.clientCommandDTO(IMEI, messageSender, messageRecipient);
    }

    @Test
    public void apply() {
        testBegin("OpenSoftCheckProcessor", "apply()");

        ConfirmSoftCheckProductsResult result = (ConfirmSoftCheckProductsResult)
                new ConfirmSoftCheckProductsProcessor(clientCommandDTO).apply(commandDTO);
        assertThat(result).isNotNull();

        System.out.println("Result:");
        System.out.println("result.isDone: " + result.isDone());
        System.out.println("result.data: " + result.cartRecords());
        System.out.println("result.isReconnect: " + result.isReconnect());
        System.out.println("result.reconnectDTO: " + result.reconnectDTO());

        testEnd("OpenSoftCheckProcessor", "apply()");
    }
}
