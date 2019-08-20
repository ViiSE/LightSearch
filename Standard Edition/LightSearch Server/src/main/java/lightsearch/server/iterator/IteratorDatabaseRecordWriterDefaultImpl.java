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

import java.io.FileOutputStream;
import java.io.IOException;
import lightsearch.server.exception.IteratorException;
import lightsearch.server.data.LightSearchServerDTO;

/**
 *
 * @author ViiSE
 */
public class IteratorDatabaseRecordWriterDefaultImpl implements IteratorDatabaseRecordWriter {

    private final LightSearchServerDTO serverDTO;
    
    public IteratorDatabaseRecordWriterDefaultImpl(LightSearchServerDTO serverDTO) {
        this.serverDTO = serverDTO;
    }
    
    @Override
    public void write(long iteratorDatabaseRecord) throws IteratorException {
        try(FileOutputStream fout = new FileOutputStream(serverDTO.currentDirectory() + "iterator")) {
            fout.write(String.valueOf(iteratorDatabaseRecord).getBytes());    
        } catch(IOException ex) {
            throw new IteratorException(ex.getMessage());
        }
    }
    
}
