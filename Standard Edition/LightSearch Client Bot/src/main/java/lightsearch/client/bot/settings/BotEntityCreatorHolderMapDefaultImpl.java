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

import lightsearch.client.bot.processor.BotEntityProcessor;
import lightsearch.client.bot.processor.BotEntityProcessorAdvancedJSON;
import lightsearch.client.bot.processor.BotEntityProcessorSimpleJSON;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ViiSE
 */
public class BotEntityCreatorHolderMapDefaultImpl implements BotEntityCreatorHolder {

    private final String SIMPLE   = BotSettingsType.SIMPLE.toString();
    private final String ADVANCED = BotSettingsType.ADVANCED.toString();
    
    Map<String, BotEntityProcessor> processorsMap;

    public BotEntityCreatorHolderMapDefaultImpl(int botAmount, String ip, int port,
            long delayMessageDisplay) {
        processorsMap = new HashMap<>();
        processorsMap.put(SIMPLE, new BotEntityProcessorSimpleJSON(
                botAmount, ip, port, delayMessageDisplay));
        processorsMap.put(ADVANCED, new BotEntityProcessorAdvancedJSON(
                botAmount, ip, port, delayMessageDisplay));
    }

    @Override
    public BotEntityProcessor get(String type) {
        return processorsMap.get(type);
    }
}
