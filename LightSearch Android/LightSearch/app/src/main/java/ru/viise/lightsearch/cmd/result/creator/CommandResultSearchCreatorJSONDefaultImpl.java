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

import org.json.simple.JSONObject;

import java.util.List;
import java.util.Objects;

import ru.viise.lightsearch.cmd.ClientCommandContentEnum;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.cmd.result.verify.ResultCommandVerifier;
import ru.viise.lightsearch.cmd.result.verify.ResultCommandVerifierInit;
import ru.viise.lightsearch.cmd.result.SearchCommandResult;
import ru.viise.lightsearch.cmd.result.SearchCommandResultInit;
import ru.viise.lightsearch.data.SearchRecordDTO;
import ru.viise.lightsearch.data.creator.SearchRecordsDTOCreator;
import ru.viise.lightsearch.data.creator.SearchRecordsDTOCreatorInit;
import ru.viise.lightsearch.exception.MessageParserException;
import ru.viise.lightsearch.message.parser.MessageParser;
import ru.viise.lightsearch.message.parser.MessageParserInit;

public class CommandResultSearchCreatorJSONDefaultImpl implements CommandResultCreator {

    private final String IS_DONE    = ClientCommandContentEnum.IS_DONE.stringValue();
    private final String IMEI_FIELD = ClientCommandContentEnum.IMEI.stringValue();
    private final String DATA       = ClientCommandContentEnum.DATA.stringValue();

    private final String rawMessage;
    private final String IMEI;
    private final String subdivision;

    public CommandResultSearchCreatorJSONDefaultImpl(String rawMessage, String IMEI, String subdivision) {
        this.rawMessage = rawMessage;
        this.IMEI = IMEI;
        this.subdivision = subdivision;
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
            SearchRecordsDTOCreator searchRecsDTOCr =
                    SearchRecordsDTOCreatorInit.searchRecordsDTOCreator(objMsg.get(DATA));
            List<SearchRecordDTO> searchRecords = searchRecsDTOCr.createSearchRecordsDTO();

            SearchCommandResult searchCmdRes = SearchCommandResultInit.searchCommandResult(isDone,
                    null, searchRecords, subdivision);
            return searchCmdRes;
        }
        catch(MessageParserException | NullPointerException ex) {
            return null;
        }
    }
}
