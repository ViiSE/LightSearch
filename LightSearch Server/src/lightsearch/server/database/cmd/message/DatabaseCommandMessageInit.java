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
package lightsearch.server.database.cmd.message;

/**
 *
 * @author ViiSE
 */
public class DatabaseCommandMessageInit {
    
    public static DatabaseCommandMessage databaseCommandMessageConnection(String command, String IMEI) {
        return new DatabaseCommandMessageConnectionDefaultWindowsJSONImpl(command, IMEI);
    }
    
    public static DatabaseCommandMessage databaseCommandMessageSearch(String command, String IMEI, 
            String barcode, String sklad, String TK) {
        return new DatabaseCommandMessageSearchDefaultWindowsJSONImpl(command, IMEI, barcode, sklad, TK);
    }
    
    public static DatabaseCommandMessage databaseCommandMessageOpenSoftCheck(String command, String IMEI,
            String userIdent, String cardCode) {
        return new DatabaseCommandMessageOpenSoftCheckDefaultWindowsJSONImpl(command, IMEI, userIdent, cardCode);
    }
    
    public static DatabaseCommandMessage databaseCommandMessageCancelSoftCheck(String command, 
            String IMEI, String userIdent, String cardCode) {
        return new DatabaseCommandMessageCancelSoftCheckDefaultWindowsJSONImpl(command, 
                IMEI, userIdent, cardCode);
    }
    
    public static DatabaseCommandMessage databaseCommandMessageCloseSoftCheck(String command, 
            String IMEI, String userIdent, String cardCode, String data, String delivery) {
        return new DatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONImpl(command, 
                IMEI, userIdent, cardCode, data, delivery);
    }
    
    public static DatabaseCommandMessage databaseCommandMessageConfirmSoftCheckProducts(String command, 
            String IMEI, String userIdent, String cardCode, String data) {
        return new DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONImpl(command, 
                IMEI, userIdent, cardCode, data);
    }
}
