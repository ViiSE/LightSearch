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

package ru.viise.lightsearch.message.type;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.viise.lightsearch.data.CommandAuthorizationDTO;
import test.rule.CreateCommandAuthorizationDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class MessageAuthorizationJUnitTest {

    private CommandAuthorizationDTO cmdAuthDTO;

    @Rule
    public final CreateCommandAuthorizationDTORule cmdAuthDTORule = new CreateCommandAuthorizationDTORule();

    @Before
    public void setUp() {
        cmdAuthDTO = cmdAuthDTORule.getCommandAuthorizationDTO();
    }

    @Test
    public void message() {
        testBegin("MessageAuthorizationDTO", "message()");

        MessageAuthorization msgAuth = MessageAuthorizationInit.messageAuthorization(cmdAuthDTO);
        assertThat(msgAuth).isNotNull();

        String rawMessage = msgAuth.message();
        assertThat(rawMessage).isNotNull();
        assertThat(rawMessage).isNotEmpty();

        System.out.println("Message: " + rawMessage);

        testEnd("MessageAuthorizationDTO", "message()");
    }
}
