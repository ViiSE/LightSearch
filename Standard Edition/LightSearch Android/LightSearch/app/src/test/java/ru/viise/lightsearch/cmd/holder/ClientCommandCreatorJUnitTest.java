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

package ru.viise.lightsearch.cmd.holder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import test.rule.CreateClientCommandCreatorRule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class ClientCommandCreatorJUnitTest {

    private ClientCommandCreator clientCommandCreator;

    @Rule
    public final CreateClientCommandCreatorRule clientCommandCreatorRule =
            new CreateClientCommandCreatorRule();

    @Before
    public void setUp() {
        clientCommandCreator = clientCommandCreatorRule.getClientCommandCreator();
    }

    @Test
    public void createClientCommandHolder() {
        testBegin("ClientCommandCreator", "createClientCommandHolder()");

        ClientCommandHolder holder = clientCommandCreator.createClientCommandHolder();
        assertThat(holder).isNotNull();

        System.out.println("ClientCommandHolder: " + holder);

        testEnd("ClientCommandCreator", "createClientCommandHolder()");
    }
}
