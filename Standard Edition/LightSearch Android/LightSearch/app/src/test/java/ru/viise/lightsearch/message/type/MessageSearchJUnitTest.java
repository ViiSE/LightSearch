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

import ru.viise.lightsearch.data.CommandSearchDTO;
import test.rule.CreateCommandSearchDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class MessageSearchJUnitTest {

    private String IMEI;
    private CommandSearchDTO cmdSearchDTO;

    @Rule
    public final CreateCommandSearchDTORule cmdSearchDTORule = new CreateCommandSearchDTORule();

    @Before
    public void setUp() {
        IMEI = cmdSearchDTORule.getIMEI();
        cmdSearchDTO = cmdSearchDTORule.getCommandSearchDTO();
    }

    @Test
    public void message() {
        testBegin("MessageSearch", "message()");

        MessageSearch msgSearch = MessageSearchInit.messageSearch(IMEI, cmdSearchDTO);
        assertThat(msgSearch).isNotNull();

        String rawMessage = msgSearch.message();
        assertThat(rawMessage).isNotNull();
        assertThat(rawMessage).isNotEmpty();

        System.out.println("Message: " + rawMessage);

        testEnd("MessageSearch", "message()");
    }
}
