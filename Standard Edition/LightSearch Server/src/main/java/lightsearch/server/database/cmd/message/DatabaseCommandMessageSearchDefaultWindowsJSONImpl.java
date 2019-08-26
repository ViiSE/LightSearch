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
 * Сообщение в формате JSON команды поиска товара для СУБД, работающих под управлением операционной системы
 * Microsoft Windows.
 * <p>
 * Для записи JSON сообщения в формате pretty в поле типа BLOB в сообщении необходимо добавлять после каждой строки
 * помимо символа переноса новой строки {@code \n} символ возврата каретки {@code \r}.
 * @author ViiSE
 * @since 2.0
 */
public class DatabaseCommandMessageSearchDefaultWindowsJSONImpl implements DatabaseCommandMessage {

    private final String CMD_FIELD = DatabaseCommandMessageEnum.COMMAND.stringValue();
    private final String IMEI_FIELD = DatabaseCommandMessageEnum.IMEI.stringValue();
    private final String BARCODE_FIELD = DatabaseCommandMessageEnum.BARCODE.stringValue();
    private final String SKLAD_FIELD = DatabaseCommandMessageEnum.SKLAD.stringValue();
    private final String TK_FIELD = DatabaseCommandMessageEnum.TK.stringValue();
    
    private final String command;
    private final String IMEI;
    private final String barcode;
    private final String sklad;
    private final String TK;
    
    public DatabaseCommandMessageSearchDefaultWindowsJSONImpl(String command, String IMEI, String barcode, 
            String sklad, String TK) {
        this.command = command;
        this.IMEI = IMEI;
        this.barcode = barcode;
        this.sklad = sklad;
        this.TK = TK;
    }
    
    @Override
    public String message() {
        return "{\r\n"
                + "\"" + CMD_FIELD + "\":\""  + command + "\",\r\n"
                + "\"" + IMEI_FIELD + "\":\"" + IMEI + "\",\r\n"
                + "\"" + BARCODE_FIELD + "\":\"" + barcode + "\",\r\n"
                + "\"" + SKLAD_FIELD + "\":\"" + sklad + "\",\r\n"
                + "\"" + TK_FIELD + "\":\"" + TK + "\"\r\n"
                + "}";
    }
}
