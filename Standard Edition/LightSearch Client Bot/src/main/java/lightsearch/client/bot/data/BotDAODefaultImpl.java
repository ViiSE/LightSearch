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

/**
 *
 * @author ViiSE
 */
public class BotDAODefaultImpl implements BotDAO {
    
    private String username;
    private String IMEI;
    private String cardCode;
    private String userIdent;
    private String botName;
    private String password;
    
    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
    }
    
    @Override
    public String IMEI() {
        return IMEI;
    }

    @Override
    public String cardCode() {
        return cardCode;
    }

    @Override
    public String userIdent() {
        return userIdent;
    }

    @Override
    public String botName() {
        return botName;
    }

    @Override
    public void setBotName(String botName) {
        this.botName = botName;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    @Override
    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    @Override
    public void setUserIdent(String userIdent) {
        this.userIdent = userIdent;
    }
}
