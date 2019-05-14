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

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import ru.viise.lightsearch.cmd.CommandTypeEnum;
import ru.viise.lightsearch.cmd.processor.AuthorizationProcessor;
import ru.viise.lightsearch.cmd.processor.OpenSoftCheckProcessor;
import ru.viise.lightsearch.cmd.processor.SearchProcessor;
import ru.viise.lightsearch.cmd.result.CommandResult;
import ru.viise.lightsearch.data.ClientCommandDTO;
import ru.viise.lightsearch.data.ClientCommandDTOInit;
import ru.viise.lightsearch.data.CommandDTO;
import ru.viise.lightsearch.message.MessageRecipient;
import ru.viise.lightsearch.message.MessageSender;

public class ClientCommandCreatorDefaultImpl implements ClientCommandCreator {

    private final CommandTypeEnum AUTHORIZATION   = CommandTypeEnum.AUTHORIZATION;
    private final CommandTypeEnum SEARCH          = CommandTypeEnum.SEARCH;
    private final CommandTypeEnum OPEN_SOFT_CHECK = CommandTypeEnum.OPEN_SOFT_CHECK;

    private final String IMEI;
    private final MessageSender msgSender;
    private final MessageRecipient msgRecipient;

    public ClientCommandCreatorDefaultImpl(String IMEI, MessageSender msgSender,
               MessageRecipient msgRecipient) {
        this.IMEI = IMEI;
        this.msgSender = msgSender;
        this.msgRecipient = msgRecipient;
    }

    @Override
    public ClientCommandHolder createClientCommandHolder() {
        ClientCommandDTO clCmdDTO = ClientCommandDTOInit.clientCommandDTO(IMEI, msgSender, msgRecipient);

        Map<CommandTypeEnum, Function<CommandDTO, CommandResult>> cmdHolder = new HashMap<>();
        cmdHolder.put(AUTHORIZATION, new AuthorizationProcessor(clCmdDTO));
        cmdHolder.put(SEARCH, new SearchProcessor(clCmdDTO));
        cmdHolder.put(OPEN_SOFT_CHECK, new OpenSoftCheckProcessor(clCmdDTO));

        ClientCommandHolder cmdClHolder = ClientCommandHolderInit.clientCommandHolder(cmdHolder);
        return cmdClHolder;
    }
}
