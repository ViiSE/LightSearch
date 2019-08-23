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
package lightsearch.server.cmd.changer;

import lightsearch.server.data.DatabaseRecordIdentifierWriterTimerCreatorDTO;
import lightsearch.server.timer.TimersIDEnum;

/**
 *
 * @author ViiSE
 */
public interface ServerStateChanger {
    void executeRebootTimer(TimersIDEnum id);
    void destroyRebootTimer(TimersIDEnum id);
    void executeDatabaseRecordIdentifierWriterTimer(DatabaseRecordIdentifierWriterTimerCreatorDTO identifierDbRecWriterTimerCrDTO);
    void destroyDatabaseRecordIdentifierWriterTimer(TimersIDEnum id);
    @Deprecated
    void executeGarbageCollectorTimer(TimersIDEnum id);
    @Deprecated
    void destroyGarbageCollectorTimer(TimersIDEnum id);
}