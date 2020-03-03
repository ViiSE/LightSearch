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

package ru.viise.lightsearch.cmd.result.creator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;

import java.util.ArrayList;
import java.util.List;

import ru.viise.lightsearch.cmd.result.ConfirmCartProductsResult;
import ru.viise.lightsearch.data.CartRecord;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.data.SubdivisionList;
import ru.viise.lightsearch.data.SubdivisionListInit;
import ru.viise.lightsearch.data.UnitsEnum;
import ru.viise.lightsearch.exception.CommandResultCreatorException;
import test.rule.CreateSoftCheckRecordsListRule;
import test.rule.CreateSubdivisionsListRule;

import static com.google.common.truth.Truth.assertThat;
import static test.TestMessage.catchMessage;
import static test.TestMessage.testBegin;
import static test.TestMessage.testEnd;

public class CommandResultConfirmCartProductsCreatorJSONJUnitTest {

    private final String amountUnit = UnitsEnum.CURRENT_AMOUNT_CART_UNIT.stringValue();
    private final List<SoftCheckRecord> recordsSC = new ArrayList<>();
    private final SubdivisionList subdivisions = SubdivisionListInit.subdivisionList(amountUnit);
    private CommandResultCreator cmdResCr;

    @Rule
    public final CreateSubdivisionsListRule  subdivisionsListRule =
            new CreateSubdivisionsListRule(subdivisions, amountUnit);

    @Rule
    public final CreateSoftCheckRecordsListRule softCheckRecordsListRule =
            new CreateSoftCheckRecordsListRule(recordsSC, subdivisions);

    @Rule
    public final RuleChain chain = RuleChain
            .outerRule(subdivisionsListRule)
            .around(softCheckRecordsListRule);

    @Before
    public void setUp() {
        String IMEI = "123456789123456";
        String rawMessage = "{" +
                                "\"IMEI\":\"123456789123456\"," +
                                "\"is_done\":\"true\"," +
                                "\"data\":[" +
                                            "{" +
                                                "\"ID\":\"111111\"," +
                                                "\"amount\":\"10\"" +
                                            "}," +
                                            "{" +
                                                "\"ID\":\"222222\"," +
                                                "\"amount\":\"20\"" +
                                            "}" +
                                         "]" +
                            "}";
        cmdResCr = CommandResultCreatorInit.commandResultConfirmCartProductsCreator(
                rawMessage, IMEI, recordsSC);
    }

    @Test
    public void createCommandResult() {
        testBegin("CommandResultAuthorizationCreatorJSON", "createCommandResult()");

        try {
            ConfirmCartProductsResult result =
                    (ConfirmCartProductsResult) cmdResCr.createCommandResult();
            assertThat(result).isNotNull();
            assertThat(result.isDone()).isTrue();

            System.out.println("Result:");
            System.out.println("isDone:" + result.isDone());
            System.out.println("message:" + result.message());
            System.out.println("cart records:");
            result.cartRecords().forEach(record -> {
                CartRecord cartRecord = (CartRecord) record;

                if(cartRecord.barcode().equals("111111")) {
                    assertThat(cartRecord.maxAmount()).isEqualTo(10.0f);
                    assertThat(cartRecord.currentAmount()).isEqualTo(10.0f);
                    assertThat(cartRecord.isConfirmed()).isFalse();
                } else if(cartRecord.barcode().equals("222222")) {
                    assertThat(cartRecord.maxAmount()).isEqualTo(20.0f);
                    assertThat(cartRecord.currentAmount()).isEqualTo(10.0f);
                    assertThat(cartRecord.isConfirmed()).isTrue();
                }

                System.out.println("-------------------------------------------------------------");
                System.out.println("name: " + cartRecord.name());
                System.out.println("barcode: " + cartRecord.barcode());
                System.out.println("price: " + cartRecord.priceWithUnit());
                System.out.println("current amount: " + cartRecord.currentAmount());
                System.out.println("max amount: " + cartRecord.maxAmountWithUnit());
                System.out.println("isConfirmed: " + cartRecord.isConfirmed());
                System.out.println("oldMaxAmount: " + cartRecord.oldMaxAmountWithUnit());
            });
        } catch (CommandResultCreatorException ex) {
            catchMessage(ex);
        }

        testEnd("CommandResultAuthorizationCreatorJSON", "createCommandResult()");
    }
}
