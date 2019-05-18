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

package ru.viise.lightsearch.cmd.result;

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Objects;

import ru.viise.lightsearch.cmd.ClientCommandContentEnum;
import ru.viise.lightsearch.cmd.processor.ConfirmSoftCheckProductsProcessor;
import ru.viise.lightsearch.data.CartRecord;
import ru.viise.lightsearch.data.SoftCheckRecord;
import ru.viise.lightsearch.data.creator.CartRecordsCreator;
import ru.viise.lightsearch.data.creator.CartRecordsCreatorInit;
import ru.viise.lightsearch.exception.MessageParserException;
import ru.viise.lightsearch.message.parser.MessageParser;
import ru.viise.lightsearch.message.parser.MessageParserInit;

public class CommandResultConfirmSoftCheckProductsCreatorJSONDefaultImpl implements CommandResultCreator {

    private final String IS_DONE    = ClientCommandContentEnum.IS_DONE.stringValue();
    private final String IMEI_FIELD = ClientCommandContentEnum.IMEI.stringValue();
    private final String DATA       = ClientCommandContentEnum.DATA.stringValue();

    private final String rawMessage;
    private final String IMEI;
    private final List<SoftCheckRecord> softCheckRecords;


    public CommandResultConfirmSoftCheckProductsCreatorJSONDefaultImpl(String rawMessage,
               String IMEI, List<SoftCheckRecord> softCheckRecords) {
        this.rawMessage = rawMessage;
        this.IMEI = IMEI;
        this.softCheckRecords = softCheckRecords;
    }

    @Override
    public CommandResult createCommandResult() {
        try {
            MessageParser msgParser = MessageParserInit.messageParser();
            JSONObject objMsg = (JSONObject)msgParser.parse(rawMessage);
            String incomingIMEI = Objects.requireNonNull(objMsg.get(IMEI_FIELD)).toString();

            String incomingIsDone = Objects.requireNonNull(objMsg.get(IS_DONE)).toString();
            ResultCommandVerifier resCmdVerifier =
                    ResultCommandVerifierInit.resultCommandVerifier(incomingIMEI, IMEI, incomingIsDone);


            boolean isDone = resCmdVerifier.verify();
            CartRecordsCreator cartRecCr =
                    CartRecordsCreatorInit.cartRecordsCreator(softCheckRecords, objMsg.get(DATA));
            List<SoftCheckRecord> cartRecords = cartRecCr.createCartRecords();

            ConfirmSoftCheckProductsResult confirmSCProdRes =
                    ConfirmSoftCheckProductsResultInit.confirmSoftCheckProductsResult(isDone, null,
                            cartRecords);
            return confirmSCProdRes;
        }
        catch(MessageParserException | NullPointerException ex) {
            return null;
        }
    }
}
