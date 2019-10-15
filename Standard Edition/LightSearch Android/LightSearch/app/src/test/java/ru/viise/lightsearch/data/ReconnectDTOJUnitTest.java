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

import ru.viise.lightsearch.cmd.CommandTypeEnum;
import test.rule.CreateReconnectDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class ReconnectDTOJUnitTest {

    private ReconnectDTO reconnectDTO;

    @Rule
    public final CreateReconnectDTORule reconnectDTORule = new CreateReconnectDTORule();

    @Before
    public void setUp() {
        reconnectDTO = reconnectDTORule.getReconnectDTO();
    }

    @Test
    public void lastCommand() {
        testBegin("ReconnectDTO", "lastCommand()");

        CommandDTO lastCommand = reconnectDTO.lastCommand();
        assertThat(lastCommand).isNotNull();

        System.out.println("Last command: " + lastCommand);

        testEnd("ReconnectDTO", "lastCommand()");
    }

    @Test
    public void lastCommandType() {
        testBegin("ReconnectDTO", "lastCommandType()");

        CommandTypeEnum lastCommandType = reconnectDTO.lastCommandType();
        assertThat(lastCommandType).isNotNull();

        System.out.println("Last command type: " + lastCommandType);

        testEnd("ReconnectDTO", "lastCommandType()");
    }
}