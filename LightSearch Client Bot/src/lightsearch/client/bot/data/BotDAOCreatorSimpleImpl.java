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
package lightsearch.client.bot.data;

import lightsearch.client.bot.settings.BotSettingsEnum;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class BotDAOCreatorSimpleImpl implements BotDAOCreator {
    
    private final static String BOT_NAME   = "1";
    private final static String USERNAME   = "user";
    private final static String PASSWORD   = "pass";
    private final static String IMEI_T     = "1111111111";
    private final static String CARD_CODE  = "22";
    private final static String USER_IDENT = "33";
    
    private static int botNameCount   = 0;
    private static int usernameCount  = 0;
    private static int passwordCount  = 0;
    private static int IMEICount      = 0;
    private static int cardCodeCount  = 0;
    private static int userIdentCount = 0;
    
    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    
    private final JSONObject data;

    public BotDAOCreatorSimpleImpl(Object data) {
        this.data = (JSONObject) data;
    }

    @Override
    public BotDAO createBotDAO() {
        String botName   = String.format("%s%s", BOT_NAME, (botNameCount++));
        String username  = String.format("%s%s", USERNAME, (usernameCount++));
        String password  = String.format("%s%s", PASSWORD, (passwordCount++));
        String IMEI      = String.format("%s%s", IMEI_T, (IMEICount++));
        String cardCode  = String.format("%s%s", CARD_CODE, (cardCodeCount++));
        String userIdent = String.format("%s%s", USER_IDENT, (userIdentCount++));
            
        String implementation = data.get(IMPLEMENTATION).toString();    
        
        try {
            BotDAO botDAO = (BotDAO) Class.forName(implementation).newInstance();
            botDAO.setBotName(botName);
            botDAO.setUsername(username);
            botDAO.setPassword(password);
            botDAO.setIMEI(IMEI);
            botDAO.setCardCode(cardCode);
            botDAO.setUserIdent(userIdent);
            
            return botDAO;
        } catch (InstantiationException ex) {
            throw new RuntimeException("Exception: " + ex.getMessage());
        } catch (IllegalAccessException ex) {
            throw new RuntimeException("Class " + implementation + " not accessible. Exception: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Class " + implementation + " not found. Exception: " + ex.getMessage());
        }
    }
    
}
