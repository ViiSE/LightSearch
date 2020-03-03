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

package ru.viise.lightsearch.cmd.result.creator;

import org.junit.Before;
import org.junit.Test;

import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.exception.CommandResultCreatorException;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.catchMessage;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandResultOpenSoftCheckCreatorErrorJUnitTest {

    private CommandResultCreator cmdResCr;

    @Before
    public void setUp() {
        cmdResCr = CommandResultCreatorInit.commandResultOpenSoftCheckCreator(
                false, "Open soft check failed.",null);
    }

    @Test
    public void createCommandResult() {
        testBegin("CommandResultOpenSoftCheckCreatorError", "createCommandResult()");

        try {
            CommandResult cmdRes = cmdResCr.createCommandResult();
            assertThat(cmdRes).isNotNull();
            assertThat(cmdRes.isDone()).isFalse();
            assertThat(cmdRes.message()).isNotNull();
            assertThat(cmdRes.message()).isEqualTo("Open soft check failed.");

            System.out.println("Result: ");
            System.out.println("isDone: " + cmdRes.isDone());
            System.out.println("message: " + cmdRes.message());
            System.out.println("isReconnect: " + cmdRes.isReconnect());
            System.out.println("reconnectDTO: " + cmdRes.reconnectDTO());
        } catch (CommandResultCreatorException ex) {
            catchMessage(ex);
        }

        testEnd("CommandResultOpenSoftCheckCreatorError", "createCommandResult()");
    }
}
