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

import test.rule.CreateCommandCloseSoftCheckDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandCloseSoftCheckDTOJUnitTest {

    private CommandCloseSoftCheckDTO cmdCloseSCDTO;

    @Rule
    public final CreateCommandCloseSoftCheckDTORule cmdCloseSCDTORule =
            new CreateCommandCloseSoftCheckDTORule();

    @Before
    public void setUp() {
        cmdCloseSCDTO = cmdCloseSCDTORule.getCommandCloseSoftCheckDTO();
    }

    @Test
    public void userIdentifier() {
        testBegin("CommandCloseSoftCheckDTO", "userIdentifier()");

        String userIdent = cmdCloseSCDTO.userIdentifier();
        assertThat(userIdent).isNotNull();
        assertThat(userIdent).isNotEmpty();

        System.out.println("userIdent: " + userIdent);

        testEnd("CommandCloseSoftCheckDTO", "userIdentifier()");
    }

    @Test
    public void cardCode() {
        testBegin("CommandCloseSoftCheckDTO", "cardCode()");

        String cardCode = cmdCloseSCDTO.cardCode();
        assertThat(cardCode).isNotNull();
        assertThat(cardCode).isNotEmpty();

        System.out.println("cardCode: " + cardCode);

        testEnd("CommandCloseSoftCheckDTO", "cardCode()");
    }

    @Test
    public void deliveryType() {
        testBegin("CommandCloseSoftCheckDTO", "deliveryType()");

        DeliveryTypeEnum deliveryType = cmdCloseSCDTO.deliveryType();

        System.out.println("deliveryType: " + deliveryType);

        testEnd("CommandCloseSoftCheckDTO", "deliveryType()");
    }
}
