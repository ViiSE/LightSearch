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

import test.rule.CreateCommandSearchDTORule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandSearchDTOJUnitTest {

    private CommandSearchDTO cmdSearchDTO;

    @Rule
    public final CreateCommandSearchDTORule cmdSearchDTORule = new CreateCommandSearchDTORule();

    @Before
    public void setUp() {
        cmdSearchDTO = cmdSearchDTORule.getCommandSearchDTO();
    }

    @Test
    public void barcode() {
        testBegin("CommandSearchDTO", "barcode()");

        String barcode = cmdSearchDTO.barcode();
        assertThat(barcode).isNotNull();
        assertThat(barcode).isNotEmpty();

        System.out.println("barcode: " + barcode);

        testEnd("CommandSearchDTO", "barcode()");
    }

    @Test
    public void sklad() {
        testBegin("CommandSearchDTO", "sklad()");

        String sklad = cmdSearchDTO.sklad();
        assertThat(sklad).isNotNull();
        assertThat(sklad).isNotEmpty();

        System.out.println("sklad: " + sklad);

        testEnd("CommandSearchDTO", "sklad()");
    }

    @Test
    public void TK() {
        testBegin("CommandSearchDTO", "TK()");

        String TK = cmdSearchDTO.TK();
        assertThat(TK).isNotNull();
        assertThat(TK).isNotEmpty();

        System.out.println("TK: " + TK);

        testEnd("CommandSearchDTO", "TK()");
    }

    @Test
    public void subdivision() {
        testBegin("CommandSearchDTO", "subdivision()");

        String subdivision = cmdSearchDTO.subdivision();
        assertThat(subdivision).isNotNull();
        assertThat(subdivision).isNotEmpty();

        System.out.println("subdivision: " + subdivision);

        testEnd("CommandSearchDTO", "subdivision()");
    }
}
