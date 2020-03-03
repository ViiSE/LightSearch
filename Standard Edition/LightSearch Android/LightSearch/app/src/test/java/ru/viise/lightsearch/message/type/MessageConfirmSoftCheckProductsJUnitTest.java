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
import org.junit.rules.RuleChain;

import java.util.ArrayList;
import java.util.List;

import ru.viise.lightsearch.data.CommandConfirmSoftCheckRecordsDTO;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.data.SubdivisionList;
import ru.viise.lightsearch.data.SubdivisionListInit;
import test.rule.CreateCommandConfirmSoftCheckRecordsDTORule;
import test.rule.CreateSoftCheckRecordsListRule;
import test.rule.CreateSubdivisionsListRule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class MessageConfirmSoftCheckProductsJUnitTest {

    private String IMEI;
    private SubdivisionList subdList = SubdivisionListInit.subdivisionList("pcs.");
    private List<SoftCheckRecord> records = new ArrayList<>();
    private CommandConfirmSoftCheckRecordsDTO cmdConfirmSCRecsDTO;

    @Rule
    public final CreateCommandConfirmSoftCheckRecordsDTORule cmdConfirmSCRecsDTORule =
            new CreateCommandConfirmSoftCheckRecordsDTORule(records);

    @Rule
    public final RuleChain chain = RuleChain
            .outerRule(new CreateSubdivisionsListRule(subdList, "pcs."))
            .around(new CreateSoftCheckRecordsListRule(records, subdList))
            .around(cmdConfirmSCRecsDTORule);

    @Before
    public void setUp() {
        IMEI = cmdConfirmSCRecsDTORule.getIMEI();
        cmdConfirmSCRecsDTO = cmdConfirmSCRecsDTORule.getCommandConfirmSoftCheckRecordsDTO();
    }

    @Test
    public void message() {
        testBegin("MessageCloseSoftCheck", "message()");

        MessageConfirmSoftCheckProducts msgConfirmSCProds =
                MessageConfirmSoftCheckProductsInit.messageConfirmSoftCheckProducts(
                        IMEI, cmdConfirmSCRecsDTO);
        assertThat(msgConfirmSCProds).isNotNull();

        String rawMessage = msgConfirmSCProds.message();
        assertThat(rawMessage).isNotNull();
        assertThat(rawMessage).isNotEmpty();

        System.out.println("Message: " + rawMessage);

        testEnd("MessageCloseSoftCheck", "message()");
    }

}
