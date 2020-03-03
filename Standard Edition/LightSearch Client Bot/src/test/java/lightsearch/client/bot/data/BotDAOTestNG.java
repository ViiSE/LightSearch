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

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotDAOTestNG {
    
    private final BotDAO botDAO = new BotDAODefaultImpl();;
    
    @Test
    @Parameters({"IMEI"})
    public void IMEI(String IMEI) {
        testBegin("BotDAO", "IMEI()");

        botDAO.setIMEI(IMEI);
        assertNotNull(IMEI, "IMEI is null!");
        
        System.out.println("IMEI: " + botDAO.IMEI());
        
        testEnd("BotDAO", "IMEI()");
    }
    
    @Test
    @Parameters({"botName"})
    public void botName(String botName) {
        testBegin("BotDAO", "botName()");

        botDAO.setBotName(botName);
        assertNotNull(botName, "BotName is null!");
        
        System.out.println("BotName: " + botDAO.botName());
        
        testEnd("BotDAO", "botName()");
    }
    
    @Test
    @Parameters({"cardCode"})
    public void cardCode(String cardCode) {
        testBegin("BotDAO", "cardCode()");
        
        botDAO.setCardCode(cardCode);
        assertNotNull(cardCode, "CardCode is null!");
        
        System.out.println("CardCode: " + botDAO.cardCode());
        
        testEnd("BotDAO", "cardCode()");
    }
    
    @Test
    @Parameters({"username"})
    public void username(String username) {
        testBegin("BotDAO", "username()");

        botDAO.setUsername(username);
        assertNotNull(username, "Username is null!");
        
        System.out.println("Username: " + botDAO.username());
        
        testEnd("BotDAO", "username()");
    }
    
    @Test
    @Parameters({"password"})
    public void password(String password) {
        testBegin("BotDAO", "password()");

        botDAO.setPassword(password);
        assertNotNull(password, "Password is null!");
        
        System.out.println("Password: " + botDAO.password());
        
        testEnd("BotDAO", "password()");
    }
    
    @Test
    @Parameters({"userIdent"})
    public void userIdent(String userIdent) {
        testBegin("BotDAO", "userIdent()");

        botDAO.setUserIdent(userIdent);
        assertNotNull(userIdent, "UserIdent is null!");
        
        System.out.println("UserIdent: " + botDAO.userIdent());
        
        testEnd("BotDAO", "userIdent()");
    }
}
