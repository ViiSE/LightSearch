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

import ru.viise.lightsearch.data.CommandCancelSoftCheckDTO;
import test.rule.CreateCommandCancelSoftCheckDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class MessageCancelSoftCheckJUnitTest {

    private String IMEI;
    private CommandCancelSoftCheckDTO cmdCancelSCDTO;

    @Rule
    public final CreateCommandCancelSoftCheckDTORule cmdCancelSCDTORule =
            new CreateCommandCancelSoftCheckDTORule();

    @Before
    public void setUp() {
        IMEI = cmdCancelSCDTORule.getIMEI();
        cmdCancelSCDTO = cmdCancelSCDTORule.getCommandCancelSoftCheckDTO();
    }

    @Test
    public void message() {
        testBegin("MessageCancelSoftCheck", "message()");

        MessageCancelSoftCheck msgCancelSC =
                MessageCancelSoftCheckInit.messageCancelSoftCheck(IMEI, cmdCancelSCDTO);
        assertThat(msgCancelSC).isNotNull();

        String rawMessage = msgCancelSC.message();
        assertThat(rawMessage).isNotNull();
        assertThat(rawMessage).isNotEmpty();

        System.out.println("Message: " + rawMessage);

        testEnd("MessageCancelSoftCheck", "message()");
    }
}