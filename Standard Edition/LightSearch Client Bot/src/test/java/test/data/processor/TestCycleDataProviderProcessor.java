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
import lightsearch.client.bot.processor.Processor;
import lightsearch.client.bot.processor.ProcessorAuthorizationDefaultImpl;
import lightsearch.client.bot.processor.ProcessorConnectionDefaultImpl;

import java.util.ArrayList;
import java.util.List;

public class TestCycleDataProviderProcessor implements DataProviderProcessor {

    @Override
    public Object apply(Object... ignore) {
        List<Processor> processors = new ArrayList<>();
        processors.add(new ProcessorConnectionDefaultImpl());
        processors.add(new ProcessorAuthorizationDefaultImpl());

        return TestCycleInit.testCycle(processors);
    }
}
