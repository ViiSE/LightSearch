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

import test.rule.CreateCommandCancelSoftCheckDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandCancelSoftCheckDTOJUnitTest {

    private CommandCancelSoftCheckDTO cmdCancelSCDTO;

    @Rule
    public final CreateCommandCancelSoftCheckDTORule cmdCancelSVDTORule =
            new CreateCommandCancelSoftCheckDTORule();

    @Before
    public void setUp() {
        cmdCancelSCDTO = cmdCancelSVDTORule.getCommandCancelSoftCheckDTO();
    }

    @Test
    public void userIdentifier() {
        testBegin("CommandCancelSoftCheckDTO", "userIdentifier()");

        String userIdentifier = cmdCancelSCDTO.userIdentifier();
        assertThat(userIdentifier).isNotNull();
        assertThat(userIdentifier).isNotEmpty();

        System.out.println("userIdentifier: " + userIdentifier);

        testEnd("CommandCancelSoftCheckDTO", "userIdentifier()");
    }

    @Test
    public void cardCode() {
        testBegin("CommandCancelSoftCheckDTO", "cardCode()");

        String cardCode = cmdCancelSCDTO.cardCode();
        assertThat(cardCode).isNotNull();
        assertThat(cardCode).isNotEmpty();

        System.out.println("cardCode: " + cardCode);

        testEnd("CommandCancelSoftCheckDTO", "cardCode()");
    }

    @Test
    public void isCart() {
        testBegin("CommandCancelSoftCheckDTO", "isCart");

        boolean isCart = cmdCancelSCDTO.isCart();

        System.out.println("isCart: " + isCart);

        testEnd("CommandCancelSoftCheckDTO", "isCart()");
    }
}
