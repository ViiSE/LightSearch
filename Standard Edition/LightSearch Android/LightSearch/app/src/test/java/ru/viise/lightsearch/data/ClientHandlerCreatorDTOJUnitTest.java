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
import org.junit.rules.RuleChain;

import test.rule.CreateClientHandlerCreatorDTORule;
import test.rule.CreateConnectionDTOTestRule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class ClientHandlerCreatorDTOJUnitTest {

    private ClientHandlerCreatorDTO clientHandlerCreatorDTO;

    @Rule
    public final CreateClientHandlerCreatorDTORule clientHandlerCreatorDTORule =
            new CreateClientHandlerCreatorDTORule();

    @Before
    public void setUp() {
        clientHandlerCreatorDTO = clientHandlerCreatorDTORule.getClientHandlerCreatorDTO();
    }

    @Test
    public void IMEI() {
        testBegin("ClientHandlerCreatorDTO", "IMEI()");

        String IMEI = clientHandlerCreatorDTO.IMEI();
        assertThat(IMEI).isNotNull();
        assertThat(IMEI).isNotEmpty();

        System.out.println("IMEI: " + IMEI);

        testEnd("ClientHandlerCreatorDTO", "IMEI()");
    }

    @Test
    public void connectionDTO() {
        testBegin("ClientHandlerCreatorDTO", "connectionDTO()");

        ConnectionDTO connectionDTO = clientHandlerCreatorDTO.connectionDTO();
        assertThat(connectionDTO).isNotNull();

        System.out.println("connectionDTO: " + connectionDTO);

        testEnd("ClientHandlerCreatorDTO", "connectionDTO()");
    }
}
