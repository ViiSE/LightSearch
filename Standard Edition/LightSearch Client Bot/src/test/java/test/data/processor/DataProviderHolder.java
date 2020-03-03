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

import lightsearch.client.bot.TestCycle;
import lightsearch.client.bot.data.BotDAO;
import lightsearch.client.bot.data.BotSettingsDTO;
import lightsearch.client.bot.data.SearchDTO;
import lightsearch.client.bot.message.MessageRecipient;
import lightsearch.client.bot.message.MessageSender;
import lightsearch.client.bot.settings.BotSettingsReader;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public interface DataProviderHolder {

    Map<Class, DataProviderProcessor> holder = new HashMap<Class, DataProviderProcessor>() {{
        put(Socket.class, new SocketDataProviderProcessor());
        put(MessageRecipient.class, new MessageRecipientDataProviderProcessor());
        put(MessageSender.class, new MessageSenderDataProviderProcessor());
        put(BotDAO.class, new BotDAODataProviderProcessor());
        put(BotSettingsDTO.class, new BotSettingsDTODataProviderProcessor());
        put(BotSettingsReader.class, new BotSettingsReaderDataProviderProcessor());
        put(SearchDTO.class, new SearchDTODataProviderProcessor());
        put(TestCycle.class, new TestCycleDataProviderProcessor());
    }};
}
