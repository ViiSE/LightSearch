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

package lightsearch.server.producer.timer;

import lightsearch.server.log.LoggerServer;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.GarbageCollectorTimerCreator;
import lightsearch.server.timer.TimersIDEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("garbageCollectorTimerCreatorProducerDefaultImpl")
public class GarbageCollectorTimerCreatorProducerDefaultImpl implements  GarbageCollectorTimerCreatorProducer {

    private final String GARBAGE_COLLECTOR_TIMER_CREATOR = "garbageCollectorTimerCreatorDefault";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public GarbageCollectorTimerCreator getGarbageCollectorTimerCreatorDefaultInstance(
            LoggerServer loggerServer, CurrentDateTime currentDateTime, ThreadManager threadManager, TimersIDEnum id) {
        return (GarbageCollectorTimerCreator) ctx.getBean(GARBAGE_COLLECTOR_TIMER_CREATOR, loggerServer, currentDateTime,
                threadManager, id);
    }
}
