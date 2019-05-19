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

import java.util.Objects;

import ru.viise.lightsearch.cmd.ClientCommandContentEnum;
import ru.viise.lightsearch.exception.MessageParserException;
import ru.viise.lightsearch.message.parser.MessageParser;
import ru.viise.lightsearch.message.parser.MessageParserInit;

public class CommandResultCloseSoftCheckCreatorJSONDefaultImpl implements CommandResultCreator {

    private final String IS_DONE    = ClientCommandContentEnum.IS_DONE.stringValue();
    private final String IMEI_FIELD = ClientCommandContentEnum.IMEI.stringValue();
    private final String MESSAGE    = ClientCommandContentEnum.MESSAGE.stringValue();

    private final String rawMessage;
    private final String IMEI;

    public CommandResultCloseSoftCheckCreatorJSONDefaultImpl(String rawMessage, String IMEI) {
        this.rawMessage = rawMessage;
        this.IMEI = IMEI;
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
            String message = Objects.requireNonNull(objMsg.get(MESSAGE)).toString();

            CloseSoftCheckCommandResult closeSCCmdRes =
                    CloseSoftCheckCommandResultInit.closeSoftCheckCommandResult(isDone, message);
            return closeSCCmdRes;
        }
        catch(MessageParserException | NullPointerException ex) {
            return null;
        }
    }
}
