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

import java.io.FileInputStream;
import java.io.IOException;
import lightsearch.server.data.LightSearchServerDTO;

/**
 *
 * @author ViiSE
 */
public class IteratorDatabaseRecordReaderDefaultImpl implements IteratorDatabaseRecordReader {

    private final LightSearchServerDTO serverDTO;
    
    public IteratorDatabaseRecordReaderDefaultImpl(LightSearchServerDTO serverDTO) {
        this.serverDTO = serverDTO;
    }

    @Override
    public long read() {
        long iteratorDatabaseRecord;
        
        try(FileInputStream fin = new FileInputStream(serverDTO.currentDirectory() + "iterator")) {
            byte[] buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
            StringBuilder iteratorDatabaseRecordString = new StringBuilder();
            for (byte b : buffer) iteratorDatabaseRecordString.append((char) b);
            
            iteratorDatabaseRecord = Integer.parseInt(iteratorDatabaseRecordString.toString());
        }
        catch(IOException | NumberFormatException ex) {
            throw new RuntimeException("Error: " + ex.getMessage());
        }
        
        return iteratorDatabaseRecord;
    }
    
}
