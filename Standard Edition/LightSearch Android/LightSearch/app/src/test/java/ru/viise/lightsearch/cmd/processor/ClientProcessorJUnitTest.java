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

import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.cmd.result.creator.CommandResultCreatorInit;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.exception.CommandResultCreatorException;
import test.rule.CreateCommandAuthorizationDTORule;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class ClientProcessorJUnitTest {

    private ClientProcessor processor = mock(ClientProcessor.class);
    private CommandDTO commandDTO;

    @Rule
    public final CreateCommandAuthorizationDTORule commandAuthorizationDTORule =
            new CreateCommandAuthorizationDTORule();

    @Before
    public void setUp() throws CommandResultCreatorException {
        commandDTO = commandAuthorizationDTORule.getCommandAuthorizationDTO();

        when(processor.apply(isA(CommandDTO.class)))
                .thenReturn(CommandResultCreatorInit.commandResultAuthorizationCreator(
                        false, "Cannot auth to server!").createCommandResult());
    }

    @Test
    public void apply() {
        testBegin("ClientProcessor", "apply()");

        CommandResult result = processor.apply(commandDTO);
        assertThat(result).isNotNull();
        assertThat(result.isDone()).isFalse();
        assertThat(result.message()).isEqualTo("Cannot auth to server!");

        System.out.println("Result: " + result);
        System.out.println("isDone: " + result.isDone());
        System.out.println("message: " + result.message());

        testEnd("ClientProcessor", "apply()");
    }
}
