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
import lightsearch.client.bot.data.BotDAODefaultImpl;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotDAOTestNG {
    
    private final BotDAO botDAO = new BotDAODefaultImpl();;
    
    @Test
    public void IMEI() {
        testBegin("BotDAO", "IMEI()");
        
        String IMEI = "123456789012345";
        botDAO.setIMEI(IMEI);
        assertNotNull(IMEI, "IMEI is null!");
        
        System.out.println("IMEI: " + botDAO.IMEI());
        
        testEnd("BotDAO", "IMEI()");
    }
    
    @Test
    public void botName() {
        testBegin("BotDAO", "botName()");
        
        String botName = "Super bot";
        botDAO.setBotName(botName);
        assertNotNull(botName, "BotName is null!");
        
        System.out.println("BotName: " + botDAO.botName());
        
        testEnd("BotDAO", "botName()");
    }
    
    @Test
    public void cardCode() {
        testBegin("BotDAO", "cardCode()");
        
        String cardCode = "007";
        botDAO.setCardCode(cardCode);
        assertNotNull(cardCode, "CardCode is null!");
        
        System.out.println("CardCode: " + botDAO.cardCode());
        
        testEnd("BotDAO", "cardCode()");
    }
    
    @Test
    public void username() {
        testBegin("BotDAO", "username()");
        
        String username = "user";
        botDAO.setUsername(username);
        assertNotNull(username, "Username is null!");
        
        System.out.println("Username: " + botDAO.username());
        
        testEnd("BotDAO", "username()");
    }
    
    @Test
    public void password() {
        testBegin("BotDAO", "password()");
        
        String password = "password";
        botDAO.setPassword(password);
        assertNotNull(password, "Password is null!");
        
        System.out.println("Password: " + botDAO.password());
        
        testEnd("BotDAO", "password()");
    }
    
    @Test
    public void userIdent() {
        testBegin("BotDAO", "userIdent()");
        
        String userIdent = "userIdent";
        botDAO.setUserIdent(userIdent);
        assertNotNull(userIdent, "UserIdent is null!");
        
        System.out.println("UserIdent: " + botDAO.userIdent());
        
        testEnd("BotDAO", "userIdent()");
    }
}
