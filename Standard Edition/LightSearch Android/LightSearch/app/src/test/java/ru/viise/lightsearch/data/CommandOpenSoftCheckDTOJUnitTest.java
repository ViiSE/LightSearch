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

import test.rule.CreateCommandOpenSoftCheckDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandOpenSoftCheckDTOJUnitTest {

    private CommandOpenSoftCheckDTO cmdOpenSCDTO;

    @Rule
    public final CreateCommandOpenSoftCheckDTORule cmdOpenSCDTORule =
            new CreateCommandOpenSoftCheckDTORule();

    @Before
    public void setUp() {
        this.cmdOpenSCDTO = cmdOpenSCDTORule.getCommandOpenSoftCheckDTO();
    }

    @Test
    public void userIdentifier() {
        testBegin("CommandOpenSoftCheckDTO", "userIdentifier()");

        String userIdentifier = cmdOpenSCDTO.userIdentifier();
        assertThat(userIdentifier).isNotNull();
        assertThat(userIdentifier).isNotEmpty();

        System.out.println("userIdentifier: " + userIdentifier);

        testEnd("CommandOpenSoftCheckDTO", "userIdentifier()");
    }
}
