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

/**
 *
 * @author ViiSE
 */
public class DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONImpl implements DatabaseCommandMessage {

    private final String CMD_FIELD       = DatabaseCommandMessageEnum.COMMAND.stringValue();
    private final String IMEI_FIELD      = DatabaseCommandMessageEnum.IMEI.stringValue();
    private final String USERNAME_FIELD  = DatabaseCommandMessageEnum.USERNAME.stringValue();
    private final String CARD_CODE_FIELD = DatabaseCommandMessageEnum.CARD_CODE.stringValue();
    private final String DATA_FIELD      = DatabaseCommandMessageEnum.DATA.stringValue();
    
    private final String command;
    private final String IMEI;
    private final String username;
    private final String cardCode;
    private final String data;

    public DatabaseCommandMessageConfirmSoftCheckProductsDefaultWindowsJSONImpl(String command, 
            String IMEI, String username, String cardCode, String data) {
        this.command = command;
        this.IMEI = IMEI;
        this.username = username;
        this.cardCode = cardCode;
        this.data = data;
    }
    
    
    
    @Override
    public String message() {
        String message = "{\r\n"
                + "\"" + CMD_FIELD + "\":\""  + command + "\",\r\n"
                + "\"" + IMEI_FIELD + "\":\"" + IMEI + "\",\r\n"
                + "\"" + USERNAME_FIELD + "\":\"" + username + "\",\r\n"
                + "\"" + CARD_CODE_FIELD + "\":\"" + cardCode + "\",\r\n"
                + "\"" + DATA_FIELD + "\":[\r\n" + data + "\r\n]\r\n"
                + "}";
        return message;
    }
}
