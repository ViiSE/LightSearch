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
package test.bot.data;

import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotDAOCreator;
import lightsearch.client.bot.data.BotDAOCreatorInit;
import lightsearch.client.bot.settings.BotSettingsEnum;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotDAOCreatorTestNG {
    
    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String BOT_NAME       = BotSettingsEnum.BOT_NAME.toString();
    private final String USERNAME       = BotSettingsEnum.USERNAME.toString();
    private final String PASSWORD       = BotSettingsEnum.PASSWORD.toString();
    private final String IMEI_FIELD     = BotSettingsEnum.IMEI.toString();
    private final String CARD_CODE      = BotSettingsEnum.CARD_CODE.toString();
    private final String USER_IDENT     = BotSettingsEnum.USER_IDENT.toString();
    
    private BotDAOCreator botDAOCr;
    
    @BeforeClass
    public void setUpClass() {
        JSONObject data = getData();
        botDAOCr = BotDAOCreatorInit.botDAOCreator(data);
    }
    
    @Test
    public void createBotDAO() {
        testBegin("BotDAOCreator", "createBotDAO()");
        
        BotDAO botDAO = botDAOCr.createBotDAO();
        assertNotNull(botDAO, "BotDAO is null!");
        System.out.println("BotDAO: " + botDAO);
        
        System.out.println("BotDAO.botName(): " + botDAO.botName());
        System.out.println("BotDAO.username(): " + botDAO.username());
        System.out.println("BotDAO.password(): " + botDAO.password());
        System.out.println("BotDAO.IMEI(): " + botDAO.IMEI());
        System.out.println("BotDAO.cardCode(): " + botDAO.cardCode());
        System.out.println("BotDAO.userIdent(): " + botDAO.userIdent());
        
        testEnd("BotDAOCreator", "createBotDAO()");
    }
    
    private JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put(IMPLEMENTATION, "lightsearch.client.bot.data.BotDAODefaultImpl");
        data.put(BOT_NAME, "super bot");
        data.put(USERNAME, "user");
        data.put(PASSWORD, "pass");
        data.put(IMEI_FIELD, "123456789012345");
        data.put(CARD_CODE, "008");
        data.put(USER_IDENT, "888");
        
        return data;
    }
}
