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

import ru.viise.lightsearch.data.CommandCloseSoftCheckDTO;
import test.rule.CreateCommandCloseSoftCheckDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class MessageCloseSoftCheckJUnitTest {

    private String IMEI;
    private CommandCloseSoftCheckDTO cmdCloseSCDTO;

    @Rule
    public final CreateCommandCloseSoftCheckDTORule cmdCloseSCDTORule =
            new CreateCommandCloseSoftCheckDTORule();

    @Before
    public void setUp() {
        IMEI = cmdCloseSCDTORule.getIMEI();
        cmdCloseSCDTO = cmdCloseSCDTORule.getCommandCloseSoftCheckDTO();
    }

    @Test
    public void message() {
        testBegin("MessageCloseSoftCheck", "message()");

        MessageCloseSoftCheck msgCloseSC =
                MessageCloseSoftCheckInit.messageCloseSoftCheck(IMEI, cmdCloseSCDTO);
        assertThat(msgCloseSC).isNotNull();

        String rawMessage = msgCloseSC.message();
        assertThat(rawMessage).isNotNull();
        assertThat(rawMessage).isNotEmpty();

        System.out.println("Message: " + rawMessage);

        testEnd("MessageCloseSoftCheck", "message()");
    }
}
