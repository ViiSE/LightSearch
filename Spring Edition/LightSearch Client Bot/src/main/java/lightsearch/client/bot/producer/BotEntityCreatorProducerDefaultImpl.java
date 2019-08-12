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

package lightsearch.client.bot.producer;

import lightsearch.client.bot.BotEntityCreator;
import lightsearch.client.bot.settings.BotSettingsReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("botEntityCreatorProducerDefault")
public class BotEntityCreatorProducerDefaultImpl implements BotEntityCreatorProducer {

    private final String BOT_ENTITY_CREATOR = "botEntityCreatorJSON";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public BotEntityCreator getBotEntityCreatorJSONInstance(BotSettingsReader settingsReader, String serverIP, int serverPort, long delayMessageDisplay) {
        return (BotEntityCreator) ctx.getBean(BOT_ENTITY_CREATOR, settingsReader, serverIP, serverPort, delayMessageDisplay);
    }
}
