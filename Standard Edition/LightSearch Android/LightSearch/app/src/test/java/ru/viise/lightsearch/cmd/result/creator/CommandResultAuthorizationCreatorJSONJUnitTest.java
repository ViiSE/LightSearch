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

import java.util.Arrays;

import ru.viise.lightsearch.cmd.result.AuthorizationCommandResult;
import ru.viise.lightsearch.exception.CommandResultCreatorException;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.catchMessage;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandResultAuthorizationCreatorJSONJUnitTest {

    private CommandResultCreator cmdResCr;

    @Before
    public void setUp() {
        String IMEI = "123456789123456";
        String rawMessage = "{" +
                                "\"IMEI\":\"123456789123456\"," +
                                "\"is_done\":\"true\"," +
                                "\"message\":\"Auth success!\"," +
                                "\"ident\":\"007\"," +
                                "\"TK_list\":[\"TK 1\", \"TK 2\"]," +
                                "\"sklad_list\":[\"sklad 1\", \"sklad 2\"]" +
                            "}";
        cmdResCr = CommandResultCreatorInit.commandResultAuthorizationCreator(rawMessage, IMEI);
    }

    @Test
    public void createCommandResult() {
        testBegin("CommandResultAuthorizationCreatorJSON", "createCommandResult()");

        try {
            AuthorizationCommandResult result = (AuthorizationCommandResult) cmdResCr.createCommandResult();
            assertThat(result).isNotNull();
            assertThat(result.isDone()).isTrue();
            assertThat(result.userIdent()).isEqualTo("007");
            assertThat(result.message()).isNotNull();

            System.out.println("Result:");
            System.out.println("isDone:" + result.isDone());
            System.out.println("message:" + result.message());
            System.out.println("userIdent:" + result.userIdent());
            System.out.println("TKList:" + Arrays.toString(result.TKList()));
            System.out.println("skladList:" + Arrays.toString(result.skladList()));
        } catch (CommandResultCreatorException ex) {
            catchMessage(ex);
        }

        testEnd("CommandResultAuthorizationCreatorJSON", "createCommandResult()");
    }
}
