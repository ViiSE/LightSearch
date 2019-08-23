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

public interface DatabaseCommandMessageProducer {
    DatabaseCommandMessage getDatabaseCommandMessageConnectionDefaultWindowsJSONInstance(String command, String IMEI, String username, String userIdentifier);
    DatabaseCommandMessage getDatabaseCommandMessageSearchDefaultWindowsJSONInstance(String command, String IMEI, String barcode, String sklad, String TK);
    DatabaseCommandMessage getDatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONInstance(String command, String IMEI, String userIdent, String cardCode);
    DatabaseCommandMessage getDatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONInstance(String command, String IMEI, String userIdent, String cardCode);
    DatabaseCommandMessage getDatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONInstance(String command, String IMEI, String userIdent, String cardCode, String delivery);
    DatabaseCommandMessage getDatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONInstance(String command, String IMEI, String userIdent, String cardCode, String data);
}