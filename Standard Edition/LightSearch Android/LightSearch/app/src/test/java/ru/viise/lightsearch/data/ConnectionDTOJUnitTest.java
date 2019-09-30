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

import test.rule.CreateConnectionDTOTestRule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class ConnectionDTOJUnitTest {

    private ConnectionDTO connectionDTO;

    @Rule
    public final CreateConnectionDTOTestRule connectionDTOTestRule = new CreateConnectionDTOTestRule();

    @Before
    public void setUp() {
        connectionDTO = connectionDTOTestRule.getConnectionDTO();
    }

    @Test
    public void ip() {
        testBegin("ConnectionDTO", "ip()");

        String ip = connectionDTO.ip();
        assertThat(ip).isNotNull();
        assertThat(ip).isNotEmpty();

        System.out.println("ip: " + ip);

        testEnd("ConnectionDTO", "ip()");
    }

    @Test
    public void port() {
        testBegin("ConnectionDTO", "port()");

        int port = connectionDTO.port();
        assertThat(port).isGreaterThan(1024);
        assertThat(port).isLessThan(65535);

        System.out.println("port: " + port);

        testEnd("ConnectionDTO", "port()");
    }
}
