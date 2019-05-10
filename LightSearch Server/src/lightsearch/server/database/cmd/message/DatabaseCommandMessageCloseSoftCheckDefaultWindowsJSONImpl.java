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
public class DatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONImpl implements DatabaseCommandMessage {

    private final String CMD_FIELD       = DatabaseCommandMessageEnum.COMMAND.stringValue();
    private final String IMEI_FIELD      = DatabaseCommandMessageEnum.IMEI.stringValue();
    private final String USER_IDENT_FIELD  = DatabaseCommandMessageEnum.USER_IDENT.stringValue();
    private final String CARD_CODE_FIELD = DatabaseCommandMessageEnum.CARD_CODE.stringValue();
    private final String DATA_FIELD      = DatabaseCommandMessageEnum.DATA.stringValue();
    private final String DELIVERY_FIELD  = DatabaseCommandMessageEnum.DELIVERY.stringValue();
    
    private final String command;
    private final String IMEI;
    private final String userIdent;
    private final String cardCode;
    private final String data;
    private final String delivery;
    
    public DatabaseCommandMessageCloseSoftCheckDefaultWindowsJSONImpl(String command, 
            String IMEI, String userIdent, String cardCode, String data, String delivery) {
        this.command   = command;
        this.IMEI      = IMEI;
        this.userIdent = userIdent;
        this.cardCode  = cardCode;
        this.data      = data;
        this.delivery  = delivery;
    }
    
    @Override
    public String message() {
        String message = "{\r\n"
                + "\"" + CMD_FIELD + "\":\""  + command + "\",\r\n"
                + "\"" + IMEI_FIELD + "\":\"" + IMEI + "\",\r\n"
                + "\"" + USER_IDENT_FIELD + "\":\"" + userIdent + "\",\r\n"
                + "\"" + CARD_CODE_FIELD + "\":\"" + cardCode + "\",\r\n"
                + "\"" + DATA_FIELD + "\":[\r\n" + data + "\r\n],\r\n"
                + "\"" + DELIVERY_FIELD + "\":\"" + delivery + "\"\r\n" 
                + "}";
        return message;
    }
    
}
