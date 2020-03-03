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

import lightsearch.client.bot.settings.BotSettingsReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("botSettingsReaderProducerDefault")
public class BotSettingsReaderProducerDefaultImpl implements BotSettingsReaderProducer {

    private final String BOT_SETTINGS_READER = "botSettingsReaderJSONFile";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public BotSettingsReader getBotSettingsReaderJSONFileInstance(String fileName) {
        return (BotSettingsReader) ctx.getBean(BOT_SETTINGS_READER, fileName);
    }
}
