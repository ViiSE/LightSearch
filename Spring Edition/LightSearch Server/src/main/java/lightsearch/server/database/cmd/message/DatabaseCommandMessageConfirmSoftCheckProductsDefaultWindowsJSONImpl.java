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

import lightsearch.server.exception.MessageParserException;
import lightsearch.server.message.parser.MessageParser;
import lightsearch.server.producer.message.MessageParserProducer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
    private final String data;

    @Autowired
    private MessageParserProducer msgParserProducer;

    public DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONImpl(String command, 
            String IMEI, String userIdent, String cardCode, String data) {
        this.command   = command;
        this.IMEI      = IMEI;
        this.userIdent = userIdent;
        this.cardCode  = cardCode;
        this.data      = data;
    }
    
    @Override
    public String message() {
        String rawData = "[";
        String jMsgStr = "{\"data\": " + data + "}";
        MessageParser msgParser = msgParserProducer.getMessageParserJSONInstance();
        try {
            JSONObject message = (JSONObject)msgParser.parse(jMsgStr);
            JSONArray jData = (JSONArray)message.get("data");

            for(Object product : jData) {
                rawData += "\r\n{\r\n";
                JSONObject productJSON = (JSONObject)product;
                String id = productJSON.get(ID_FIELD).toString();
                String amount = productJSON.get(AMOUNT_FIELD).toString();
                rawData += "\"" + ID_FIELD     + "\"" + ":" + "\"" + id     + "\",\r\n" +
                           "\"" + AMOUNT_FIELD + "\"" + ":" + "\"" + amount + "\"\r\n},";
            }
        }
        catch(MessageParserException ignore) {}
        
        rawData = rawData.substring(0, rawData.lastIndexOf("},")) + "}";
        rawData += "\r\n]";
        
        String message = "{\r\n"
                + "\"" + CMD_FIELD + "\":\""  + command + "\",\r\n"
                + "\"" + IMEI_FIELD + "\":\"" + IMEI + "\",\r\n"
                + "\"" + USER_IDENT_FIELD + "\":\"" + userIdent + "\",\r\n"
                + "\"" + CARD_CODE_FIELD + "\":\"" + cardCode + "\",\r\n"
                + "\"" + DATA_FIELD + "\":" + rawData + "\r\n"
                + "}";
        return message;
    }
}
