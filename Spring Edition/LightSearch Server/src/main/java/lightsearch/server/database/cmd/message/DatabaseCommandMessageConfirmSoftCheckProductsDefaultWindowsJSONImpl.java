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

/*
 * ViiSE (C). 2019. All rights reserved.
 * 
 *
 * This program is owned by ViiSE.
 * Modification and use of this source code for its own purposes is allowed only
 * with the consent of the author of this source code.
 * If you want to contact the author, please, send an email to: viise@outlook.com
 */
package lightsearch.server.database.cmd.message;

import lightsearch.server.cmd.client.ClientCommand;
import lightsearch.server.data.pojo.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * @author ViiSE
 */
@Component("databaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSON")
@Scope("prototype")
public class DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONImpl implements DatabaseCommandMessage {

    private final String CMD_FIELD        = DatabaseCommandMessageEnum.COMMAND.stringValue();
    private final String IMEI_FIELD       = DatabaseCommandMessageEnum.IMEI.stringValue();
    private final String USER_IDENT_FIELD = DatabaseCommandMessageEnum.USER_IDENT.stringValue();
    private final String CARD_CODE_FIELD  = DatabaseCommandMessageEnum.CARD_CODE.stringValue();
    private final String DATA_FIELD       = DatabaseCommandMessageEnum.DATA.stringValue();
    private final String ID_FIELD         = DatabaseCommandMessageEnum.ID.stringValue();
    private final String AMOUNT_FIELD     = DatabaseCommandMessageEnum.AMOUNT.stringValue();
    
    private final String command;
    private final String IMEI;
    private final String userIdent;
    private final String cardCode;
    private final List<Product> data;

    @SuppressWarnings("unchecked")
    public DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONImpl(ClientCommand clientCommand) {
        this.command   = clientCommand.command();
        this.IMEI      = clientCommand.IMEI();
        this.userIdent = clientCommand.userIdentifier();
        this.cardCode  = clientCommand.cardCode();
        this.data      = (List<Product>) clientCommand.data();
    }

    @Override
    public String message() {
        final StringBuilder rawData = new StringBuilder("[");
        data.forEach(product -> {
            rawData.append("\r\n{\r\n");
            String id = product.getId();
            String amount = product.getAmount();
            rawData.append("\"").append(ID_FIELD).append("\":\"").append(id).append("\",\r\n")
                    .append("\"").append(AMOUNT_FIELD).append("\":\"").append(amount).append("\"\r\n},");
            });

        String rawDataStr = rawData.substring(0, rawData.lastIndexOf("},")) + "}";
        rawDataStr += "\r\n]";

        return "{\r\n"
                    + "\"" + CMD_FIELD + "\":\""  + command + "\",\r\n"
                    + "\"" + IMEI_FIELD + "\":\"" + IMEI + "\",\r\n"
                    + "\"" + USER_IDENT_FIELD + "\":\"" + userIdent + "\",\r\n"
                    + "\"" + CARD_CODE_FIELD + "\":\"" + cardCode + "\",\r\n"
                    + "\"" + DATA_FIELD + "\":" + rawDataStr + "\r\n"
                + "}";
    }
}
