/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.producer.database;

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.database.cmd.message.DatabaseCommandMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("databaseCommandMessageProducerDefault")
public class DatabaseCommandMessageProducerDefaultImpl implements DatabaseCommandMessageProducer {

    private final String DB_CMD_MESSAGE_CONNECTION         = "databaseCommandMessageConnectionDefaultWindowsJSON";
    private final String DB_CMD_SEARCH                     = "databaseCommandSearchDefaultWindowsJSON";
    private final String DB_CMD_OPEN_SOFT_CHECK            = "databaseCommandOpenSoftCheckDefaultWindowsJSON";
    private final String DB_CMD_MESSAGE_CANCEL_SOFT_CHECK  = "databaseCommandMessageCancelSoftCheckDefaultWindowsJSON";
    private final String DB_CMD_MESSAGE_CLOSE_SOFT_CHECK   = "databaseCommandMessageCloseSoftCheckDefaultWindowsJSON";
    private final String DB_CMD_MESSAGE_CONFIRM_SOFT_CHECK = "databaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSON";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_MESSAGE_CONNECTION, command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageSearchDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_SEARCH, command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_OPEN_SOFT_CHECK, command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_MESSAGE_CANCEL_SOFT_CHECK, command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_MESSAGE_CLOSE_SOFT_CHECK, command);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONInstance(ClientCommand command) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_MESSAGE_CONFIRM_SOFT_CHECK, command);
    }
}
