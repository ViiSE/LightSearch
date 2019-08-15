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
package lightsearch.server.iterator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author ViiSE
 */
@Component("iteratorDatabaseRecordDefault")
@Scope("prototype")
public class IteratorDatabaseRecordDefaultImpl implements IteratorDatabaseRecord {

    private final long MAX = 1000000;
    private static boolean first = true;
    private static long iteratorDatabaseRecord;
    
    public IteratorDatabaseRecordDefaultImpl(long iteratorDatabaseRecord) {
        if(first) {
            IteratorDatabaseRecordDefaultImpl.iteratorDatabaseRecord = iteratorDatabaseRecord;
            first = false;
        }
    }
    
    @Override
    synchronized public long next() {
        ++iteratorDatabaseRecord;
        if(iteratorDatabaseRecord >= MAX)
            iteratorDatabaseRecord = 0;
        return iteratorDatabaseRecord;
    }

    @Override
    public long iteratorDatabaseRecord() {
        return iteratorDatabaseRecord;
    }
    
}
