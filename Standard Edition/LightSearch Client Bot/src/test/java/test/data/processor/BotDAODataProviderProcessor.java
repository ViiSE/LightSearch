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

package test.data.processor;

import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotDAODefaultImpl;

public class BotDAODataProviderProcessor implements DataProviderProcessor {
    @Override
    public Object apply(Object... ignore) {
        BotDAO botDAO = new BotDAODefaultImpl();
        botDAO.setBotName("Super test.bot");
        botDAO.setCardCode("111");
        botDAO.setIMEI("123456789012345");
        botDAO.setPassword("password");
        botDAO.setUserIdent("001");
        botDAO.setUsername("user");

        return botDAO;
    }
}
