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

import java.util.ArrayList;
import java.util.List;

import test.rule.CreateCommandConfirmSoftCheckRecordsDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandConfirmCartRecordsDTOJUnitTest {

    private List<SoftCheckRecord> records = new ArrayList<>();
    private CommandConfirmSoftCheckRecordsDTO cmdConfirmSCRecsDTO;

    @Rule
    public final CreateCommandConfirmSoftCheckRecordsDTORule cmdConfirmSCRecsDTORule =
            new CreateCommandConfirmSoftCheckRecordsDTORule(records);

    @Before
    public void setUp() {
        cmdConfirmSCRecsDTO = cmdConfirmSCRecsDTORule.getCommandConfirmSoftCheckRecordsDTO();
    }

    @Test
    public void userIdent() {
        testBegin("CommandConfirmSoftCheckRecordsDTO", "userIdent()");

        String userIdent = cmdConfirmSCRecsDTO.userIdent();
        assertThat(userIdent).isNotNull();
        assertThat(userIdent).isNotEmpty();

        System.out.println("userIdent: " + userIdent);

        testEnd("CommandConfirmSoftCheckRecordsDTO", "userIdent()");
    }

    @Test
    public void cardCode() {
        testBegin("CommandConfirmSoftCheckRecordsDTO", "cardCode()");

        String cardCode = cmdConfirmSCRecsDTO.cardCode();
        assertThat(cardCode).isNotNull();
        assertThat(cardCode).isNotEmpty();

        System.out.println("cardCode: " + cardCode);

        testEnd("CommandConfirmSoftCheckRecordsDTO", "cardCode()");
    }

    @Test
    public void softCheckRecords() {
        testBegin("CommandConfirmSoftCheckRecordsDTO", "softCheckRecords()");

        List<SoftCheckRecord> softCheckRecords = cmdConfirmSCRecsDTO.softCheckRecords();
        assertThat(softCheckRecords).isNotNull();

        System.out.println("softCheckRecords: " + softCheckRecords);

        testEnd("CommandConfirmSoftCheckRecordsDTO", "softCheckRecords()");
    }
}
