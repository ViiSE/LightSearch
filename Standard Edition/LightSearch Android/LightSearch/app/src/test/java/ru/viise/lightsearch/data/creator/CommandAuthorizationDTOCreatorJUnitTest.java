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

package ru.viise.lightsearch.data.creator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import ru.viise.lightsearch.data.AuthorizationDTO;
import ru.viise.lightsearch.data.CommandAuthorizationDTO;
import test.rule.CreateAuthorizationDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandAuthorizationDTOCreatorJUnitTest {

    private CommandAuthorizationDTOCreator cmdAuthDTOCreator;

    @Rule
    public final CreateAuthorizationDTORule authorizationDTORule = new CreateAuthorizationDTORule();

    @Before
    public void setUp() {
        AuthorizationDTO authorizationDTO = authorizationDTORule.getAuthorizationDTO();
        String IMEI = "123456789123456";
        cmdAuthDTOCreator =
                CommandAuthorizationDTOCreatorInit.commandAuthorizationDTOCreator(
                        IMEI, authorizationDTO, null);
    }

    @Test
    public void createCommandDTO() {
        testBegin("CommandAuthorizationDTOCreator", "createCommandDTO()");

        CommandAuthorizationDTO cmdAuthDTO = cmdAuthDTOCreator.createCommandDTO();
        assertThat(cmdAuthDTO).isNotNull();

        System.out.println("CommandAuthorizationDTO: ");
        System.out.println("IMEI: " + cmdAuthDTO.IMEI());
        System.out.println("ip: " + cmdAuthDTO.ip());
        System.out.println("os: " + cmdAuthDTO.os());
        System.out.println("model: " + cmdAuthDTO.model());
        System.out.println("username: " + cmdAuthDTO.username());
        System.out.println("password: " + cmdAuthDTO.password());
        System.out.println("userIdent: " + cmdAuthDTO.userIdent());
        System.out.println("reconnectDTO: " + cmdAuthDTO.reconnectDTO());

        testEnd("CommandAuthorizationDTOCreator", "createCommandDTO()");
    }
}
