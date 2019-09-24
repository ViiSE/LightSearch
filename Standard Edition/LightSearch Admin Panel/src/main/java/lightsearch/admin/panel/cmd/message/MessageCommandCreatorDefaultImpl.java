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
package lightsearch.admin.panel.cmd.message;

import lightsearch.admin.panel.cmd.message.processor.*;
import lightsearch.admin.panel.cmd.result.CommandResult;
import lightsearch.admin.panel.data.AdminCommandDAO;
import lightsearch.admin.panel.data.MessageCommandDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 *
 * @author ViiSE
 */
public class MessageCommandCreatorDefaultImpl implements MessageCommandCreator {
    
    private final String AUTHENTICATION  = MessageCommandEnum.AUTHENTICATION.stringValue();
    private final String RESTART         = MessageCommandEnum.RESTART.stringValue();
    private final String TIMEOUT_SERVER  = MessageCommandEnum.TIMEOUT_SERVER.stringValue();
    private final String TIMEOUT_CLIENT  = MessageCommandEnum.TIMEOUT_CLIENT.stringValue();
    private final String CLIENT_LIST     = MessageCommandEnum.CLIENT_LIST.stringValue();
    private final String KICK            = MessageCommandEnum.KICK.stringValue();
    private final String BLACKLIST       = MessageCommandEnum.BLACKLIST.stringValue();
    private final String ADD_BLACKLIST   = MessageCommandEnum.ADD_BLACKLIST.stringValue();
    private final String DEL_BLACKLIST   = MessageCommandEnum.DEL_BLACKLIST.stringValue();
    private final String CREATE_ADMIN    = MessageCommandEnum.CREATE_ADMIN.stringValue();
    private final String CHANGE_DATABASE = MessageCommandEnum.CHANGE_DATABASE.stringValue();
    
    private final MessageCommandDTO messageCommandDTO;

    public MessageCommandCreatorDefaultImpl(MessageCommandDTO messageCommandDTO) {
        this.messageCommandDTO = messageCommandDTO;
    }

    @Override
    public Map<String, Function<AdminCommandDAO, CommandResult>> createMessageCommandHolder() {
        Map<String, Function<AdminCommandDAO, CommandResult>> msgCmdHolder = new HashMap<>();
        msgCmdHolder.put(AUTHENTICATION, new AuthenticationMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(RESTART, new RestartMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(TIMEOUT_SERVER, new TimeoutServerMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(TIMEOUT_CLIENT, new TimeoutClientMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(CLIENT_LIST, new ClientListMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(KICK, new KickMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(BLACKLIST, new BlacklistMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(ADD_BLACKLIST, new AddBlacklistMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(DEL_BLACKLIST, new DelBlacklistMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(CREATE_ADMIN, new CreateAdminMessageProcessor(messageCommandDTO));
        msgCmdHolder.put(CHANGE_DATABASE, new ChangeDatabaseMessageProcessor(messageCommandDTO));
        return msgCmdHolder;
    }
}
