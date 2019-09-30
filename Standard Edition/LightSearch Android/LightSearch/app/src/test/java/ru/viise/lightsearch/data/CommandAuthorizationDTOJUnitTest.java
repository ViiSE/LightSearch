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

import test.rule.CreateCommandAuthorizationDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandAuthorizationDTOJUnitTest {

    private CommandAuthorizationDTO cmdAuthDTO;

    @Rule
    public final CreateCommandAuthorizationDTORule cmdAuthDTORule =
            new CreateCommandAuthorizationDTORule();

    @Before
    public void setUp() {
        cmdAuthDTO = cmdAuthDTORule.getCommandAuthorizationDTO();
    }

    @Test
    public void IMEI() {
        testBegin("CommandAuthorizationDTO", "IMEI()");

        String IMEI = cmdAuthDTO.IMEI();
        assertThat(IMEI).isNotNull();
        assertThat(IMEI).isNotEmpty();

        System.out.println("IMEI: " + IMEI);

        testEnd("CommandAuthorizationDTO", "IMEI()");
    }

    @Test
    public void ip() {
        testBegin("CommandAuthorizationDTO", "ip()");

        String ip = cmdAuthDTO.ip();
        assertThat(ip).isNotNull();
        assertThat(ip).isNotEmpty();

        System.out.println("ip: " + ip);

        testEnd("CommandAuthorizationDTO", "ip()");
    }

    @Test
    public void os() {
        testBegin("CommandAuthorizationDTO", "os()");

        String os = cmdAuthDTO.os();
        assertThat(os).isNotNull();
        assertThat(os).isNotEmpty();

        System.out.println("os: " + os);

        testEnd("CommandAuthorizationDTO", "os()");
    }

    @Test
    public void model() {
        testBegin("CommandAuthorizationDTO", "model()");

        String model = cmdAuthDTO.model();
        assertThat(model).isNotNull();
        assertThat(model).isNotEmpty();

        System.out.println("model: " + model);

        testEnd("CommandAuthorizationDTO", "model()");
    }

    @Test
    public void username() {
        testBegin("CommandAuthorizationDTO", "username()");

        String username = cmdAuthDTO.username();
        assertThat(username).isNotNull();
        assertThat(username).isNotEmpty();

        System.out.println("username: " + username);

        testEnd("CommandAuthorizationDTO", "username()");
    }

    @Test
    public void password() {
        testBegin("CommandAuthorizationDTO", "password()");

        String password = cmdAuthDTO.password();
        assertThat(password).isNotNull();
        assertThat(password).isNotEmpty();

        System.out.println("password: " + password);

        testEnd("CommandAuthorizationDTO", "password()");
    }

    @Test
    public void userIdent() {
        testBegin("CommandAuthorizationDTO", "userIdent()");

        String userIdent = cmdAuthDTO.userIdent();
        assertThat(userIdent).isNotNull();
        assertThat(userIdent).isNotEmpty();

        System.out.println("userIdent: " + userIdent);

        testEnd("CommandAuthorizationDTO", "userIdent()");
    }

    @Test
    public void reconnectDTO() {
        testBegin("CommandAuthorizationDTO", "reconnectDTO()");

        ReconnectDTO reconnectDTO = cmdAuthDTO.reconnectDTO();
        System.out.println("reconnectDTO: " + reconnectDTO);

        testEnd("CommandAuthorizationDTO", "reconnectDTO()");
    }
}
