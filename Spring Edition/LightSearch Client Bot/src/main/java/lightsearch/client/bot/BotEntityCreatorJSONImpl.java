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
package lightsearch.client.bot;

import lightsearch.client.bot.processor.BotEntityProcessor;
import lightsearch.client.bot.producer.BotEntityCreatorHolderProducer;
import lightsearch.client.bot.settings.BotEntityCreatorHolder;
import lightsearch.client.bot.settings.BotSettingsReader;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("botEntityCreatorJSON")
@Scope("prototype")
public class BotEntityCreatorJSONImpl implements BotEntityCreator {
    
    private final String type;
    private final JSONObject data;
    private final int botAmount;
    private final String serverIP;
    private final int serverPort;
    private final long delayMessageDisplay;

    @Autowired
    private BotEntityCreatorHolderProducer producer;

    public BotEntityCreatorJSONImpl(BotSettingsReader settingsReader, String serverIP, int serverPort, long delayMessageDisplay) {
        type      = settingsReader.type();
        botAmount = settingsReader.botAmount();
        data      = (JSONObject) settingsReader.data();
        
        this.serverIP   = serverIP;
        this.serverPort = serverPort;
        this.delayMessageDisplay = delayMessageDisplay;
    }

    @Override
    public List<BotEntity> botList() {
        BotEntityCreatorHolder holder = producer.getBotEntityCreatorHolderMapDefaultInstance(botAmount, serverIP,
                serverPort, delayMessageDisplay);
        
        BotEntityProcessor proc = holder.get(type);
        if(proc != null)
            return proc.apply(data);
        else
            throw new RuntimeException("BotEntityProcessor is null!");
    }
}
