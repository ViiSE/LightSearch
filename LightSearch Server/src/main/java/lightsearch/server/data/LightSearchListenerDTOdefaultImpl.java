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
package lightsearch.server.data;

import lightsearch.server.checker.LightSearchChecker;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.thread.ThreadManager;
import lightsearch.server.time.CurrentDateTime;
import lightsearch.server.timer.TimersIDEnum;

/**
 *
 * @author ViiSE
 */
public class LightSearchListenerDTOdefaultImpl implements LightSearchListenerDTO {

    private final LightSearchChecker checker;
    private final CurrentDateTime currentDateTime;
    private final ThreadManager threadManager;
    private final IteratorDatabaseRecord iteratorDatabaseRecord;
    private final IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter;
    private final TimersIDEnum timerRebootId;
    
    public LightSearchListenerDTOdefaultImpl(LightSearchChecker checker, CurrentDateTime currentDateTime, 
            ThreadManager threadManager, IteratorDatabaseRecord iteratorDatabaseRecord, 
            IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter, TimersIDEnum timerRebootId) {
        this.checker = checker;
        this.currentDateTime = currentDateTime;
        this.threadManager = threadManager;
        this.iteratorDatabaseRecord = iteratorDatabaseRecord;
        this.iteratorDatabaseRecordWriter = iteratorDatabaseRecordWriter;
        this.timerRebootId = timerRebootId;
    }

    @Override
    public LightSearchChecker checker() {
        return checker;
    }

    @Override
    public CurrentDateTime currentDateTime() {
        return currentDateTime;
    }

    @Override
    public ThreadManager threadManager() {
        return threadManager;
    }

    @Override
    public IteratorDatabaseRecord iteratorDatabaseRecord() {
        return iteratorDatabaseRecord;
    }

    @Override
    public IteratorDatabaseRecordWriter iteratorDatabaseRecordWriter() {
        return iteratorDatabaseRecordWriter;
    }

    @Override
    public TimersIDEnum timerRebootId() {
        return timerRebootId;
    }
    
}
