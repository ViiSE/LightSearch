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
public class MessageChangeDatabaseJSONDefaultImpl implements MessageChangeDatabase {

    private final String CHANGE_DATABASE = MessageCommandEnum.CHANGE_DATABASE.stringValue();
    private final String COMMAND         = MessageCommandContentEnum.COMMAND.stringValue();
    private final String NAME            = MessageCommandContentEnum.ADMIN_NAME.stringValue();
    private final String DATABASE_NAME   = MessageCommandContentEnum.DATABASE_NAME.stringValue();
    private final String IP              = MessageCommandContentEnum.IP.stringValue();
    private final String PORT            = MessageCommandContentEnum.PORT.stringValue();
    
    private final String name;
    private final String dbName;
    private final String ip;
    private final String port;

    public MessageChangeDatabaseJSONDefaultImpl(String name, String dbName, String ip, String port) {
        this.name = name;
        this.dbName = dbName;
        this.ip = ip;
        this.port = port;
    }

    @Override
    public String message() {
        JSONObject msgChDbObj = new JSONObject();
        msgChDbObj.put(COMMAND, CHANGE_DATABASE);
        msgChDbObj.put(NAME, name);
        msgChDbObj.put(DATABASE_NAME, dbName);
        msgChDbObj.put(IP, ip);
        msgChDbObj.put(PORT, port);
        return msgChDbObj.toJSONString();
    }
}
