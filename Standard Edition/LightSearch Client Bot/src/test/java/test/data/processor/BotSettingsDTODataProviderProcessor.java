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
import lightsearch.client.bot.TestCycleInit;
import lightsearch.client.bot.data.BotSettingsDTOInit;
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorAuthorizationDefaultImpl;
import lightsearch.client.bot.processor.ProcessorConnectionDefaultImpl;
import test.data.DataProviderCreator;

import java.util.ArrayList;
import java.util.List;

public class BotSettingsDTODataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... args) {
        int cycleAmount = (Integer) args[0];
        long delayBeforeSendingMessage = (Long) args[1];

        TestCycle testCycle = DataProviderCreator.createDataProvider(TestCycle.class);

        return BotSettingsDTOInit.botSettingsDTO(testCycle, cycleAmount, delayBeforeSendingMessage);
    }
}
