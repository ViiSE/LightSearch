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
package lightsearch.admin.panel.message.type;

import lightsearch.admin.panel.cmd.message.MessageCommandContentEnum;
import lightsearch.admin.panel.cmd.message.MessageCommandEnum;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class MessageCreateAdminJSONDfaultImpl implements MessageCreateAdmin {

    private final String CREATE_ADMIN   = MessageCommandEnum.CREATE_ADMIN.stringValue();
    private final String COMMAND        = MessageCommandContentEnum.COMMAND.stringValue();
    private final String NAME           = MessageCommandContentEnum.ADMIN_NAME.stringValue();
    private final String NEW_ADMIN_NAME = MessageCommandContentEnum.NEW_ADMIN_NAME.stringValue();
    private final String PASSWORD       = MessageCommandContentEnum.PASSWORD.stringValue();
    
    private final String name;
    private final String adminName;
    private final String password;

    public MessageCreateAdminJSONDfaultImpl(String name, String adminName, String password) {
        this.name = name;
        this.adminName = adminName;
        this.password = password;
    }

    @Override
    public String message() {
        JSONObject msgCreateAdmin = new JSONObject();
        msgCreateAdmin.put(COMMAND, CREATE_ADMIN);
        msgCreateAdmin.put(NAME, name);
        msgCreateAdmin.put(NEW_ADMIN_NAME, adminName);
        msgCreateAdmin.put(PASSWORD, password);
        return msgCreateAdmin.toJSONString();
    }
}
