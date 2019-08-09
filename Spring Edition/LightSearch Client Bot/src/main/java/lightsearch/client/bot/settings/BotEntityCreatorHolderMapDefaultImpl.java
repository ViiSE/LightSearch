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
package lightsearch.client.bot.settings;

import lightsearch.client.bot.constants.BotSettingsType;
import lightsearch.client.bot.processor.BotEntityProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("botEntityCreatorHolderMapDefault")
@Scope("prototype")
public class BotEntityCreatorHolderMapDefaultImpl implements BotEntityCreatorHolder {

    private final String SIMPLE   = BotSettingsType.SIMPLE.toString();
    private final String ADVANCED = BotSettingsType.ADVANCED.toString();

    private final String BOT_ENTITY_PROC_SIMPLE  = "botEntityProcessorSimpleJSON";
    private final String BOT_ENTITY_PROC_ADVANCE = "botEntityProcessorAdvancedJSON";

    private Map<String, BotEntityProcessor> processorsMap;

    private final int botAmount;
    private final String ip;
    private final int port;
    private final long delayMessageDisplay;

    @Autowired
    private ApplicationContext ctx;

    public BotEntityCreatorHolderMapDefaultImpl(int botAmount, String ip, int port, long delayMessageDisplay) {
        processorsMap = new HashMap<>();
        this.botAmount = botAmount;
        this.ip = ip;
        this.port = port;
        this.delayMessageDisplay = delayMessageDisplay;
    }

    private void fillMap() {
        if(processorsMap.isEmpty()) {
            processorsMap.put(SIMPLE, (BotEntityProcessor) ctx.getBean(BOT_ENTITY_PROC_SIMPLE, botAmount, ip, port, delayMessageDisplay));
            processorsMap.put(ADVANCED, (BotEntityProcessor) ctx.getBean(BOT_ENTITY_PROC_ADVANCE, botAmount, ip, port, delayMessageDisplay));
        }
    }

    @Override
    public BotEntityProcessor get(String type) {
        fillMap();
        return processorsMap.get(type);
    }
}
