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

import lightsearch.client.bot.constants.BotSettingsEnum;
import lightsearch.client.bot.producer.BotDAOProducer;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("botDAOCreatorAdvanceJSON")
@Scope("prototype")
public class BotDAOCreatorAdvanceJSONImpl implements BotDAOCreator {

    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String BOT_NAME       = BotSettingsEnum.BOT_NAME.toString();
    private final String USERNAME       = BotSettingsEnum.USERNAME.toString();
    private final String PASSWORD       = BotSettingsEnum.PASSWORD.toString();
    private final String IMEI_FIELD     = BotSettingsEnum.IMEI.toString();
    private final String CARD_CODE      = BotSettingsEnum.CARD_CODE.toString();
    private final String USER_IDENT     = BotSettingsEnum.USER_IDENT.toString();

    private final JSONObject data;

    @Autowired
    private BotDAOProducer producer;

    public BotDAOCreatorAdvanceJSONImpl(Object data) {
        this.data = (JSONObject) data;
    }

    @Override
    public BotDAO createBotDAO() {
        String botName   = data.get(BOT_NAME).toString();
        String username  = data.get(USERNAME).toString();
        String password  = data.get(PASSWORD).toString();
        String IMEI      = data.get(IMEI_FIELD).toString();
        String cardCode  = data.get(CARD_CODE).toString();
        String userIdent = data.get(USER_IDENT).toString();

        String bean = data.get(IMPLEMENTATION).toString();

        BotDAO botDAO = producer.getBotDAOInstance(bean);
        botDAO.setBotName(botName);
        botDAO.setUsername(username);
        botDAO.setPassword(password);
        botDAO.setIMEI(IMEI);
        botDAO.setCardCode(cardCode);
        botDAO.setUserIdent(userIdent);

        return botDAO;
    }
}
