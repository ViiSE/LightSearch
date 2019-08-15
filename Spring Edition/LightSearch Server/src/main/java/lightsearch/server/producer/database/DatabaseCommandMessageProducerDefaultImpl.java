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

package lightsearch.server.producer.database;

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
    public DatabaseCommandMessage getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(String command, String IMEI, String username, String userIdentifier) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_MESSAGE_CONNECTION, command, IMEI, username, userIdentifier);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageSearchDefaultWindowsJSONInstance(String command, String IMEI, String barcode, String sklad, String TK) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_SEARCH, command, IMEI, barcode, sklad, TK);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONInstance(String command, String IMEI, String userIdent, String cardCode) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_OPEN_SOFT_CHECK, command, IMEI, userIdent, cardCode);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONInstance(String command, String IMEI, String userIdent, String cardCode) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_MESSAGE_CANCEL_SOFT_CHECK, command, IMEI, userIdent, cardCode);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONInstance(String command, String IMEI, String userIdent, String cardCode, String delivery) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_MESSAGE_CLOSE_SOFT_CHECK, command, IMEI, userIdent, cardCode, delivery);
    }

    @Override
    public DatabaseCommandMessage getDatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONInstance(String command, String IMEI, String userIdent, String cardCode, String data) {
        return (DatabaseCommandMessage) ctx.getBean(DB_CMD_MESSAGE_CONFIRM_SOFT_CHECK, command, IMEI, userIdent, cardCode, data);
    }
}
