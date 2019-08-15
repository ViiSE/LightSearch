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

package lightsearch.server.producer.data;

import lightsearch.server.data.IteratorDatabaseRecordWriterTimerCreatorDTO;
import lightsearch.server.iterator.IteratorDatabaseRecord;
import lightsearch.server.iterator.IteratorDatabaseRecordWriter;
import lightsearch.server.timer.TimersIDEnum;

public interface IteratorDatabaseRecordWriterTimerCreatorDTOProducer {
    IteratorDatabaseRecordWriterTimerCreatorDTO getIteratorDatabaseRecordWriterTimerCreatorDTODefaultInstance(
            IteratorDatabaseRecord iterator, IteratorDatabaseRecordWriter iteratorWriter, long minutesToWrite,
            TimersIDEnum timerId);
}
