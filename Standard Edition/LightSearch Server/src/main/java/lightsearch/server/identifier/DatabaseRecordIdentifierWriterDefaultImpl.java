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
package lightsearch.server.identifier;

import java.io.FileOutputStream;
import java.io.IOException;

import lightsearch.server.exception.IdentifierException;
import lightsearch.server.data.LightSearchServerDTO;

/**
 * Реализация интерфейса {@link DatabaseRecordIdentifierWriter} по умолчанию.
 * <p>
 * Записывает значение идентификатора базы данных в файл db_identifier, расположенного в той же директории, что и
 * исполняемый файл LightSearch Server.
 * @author ViiSE
 * @see DatabaseRecordIdentifier
 * @since 2.0.0
 */
public class DatabaseRecordIdentifierWriterDefaultImpl implements DatabaseRecordIdentifierWriter {

    private final LightSearchServerDTO serverDTO;
    
    public DatabaseRecordIdentifierWriterDefaultImpl(LightSearchServerDTO serverDTO) {
        this.serverDTO = serverDTO;
    }
    
    @Override
    public void write(long databaseRecordIdentifier) throws IdentifierException {
        try(FileOutputStream fout = new FileOutputStream(serverDTO.currentDirectory() + "db_identifier")) {
            fout.write(String.valueOf(databaseRecordIdentifier).getBytes());
        } catch(IOException ex) {
            throw new IdentifierException(ex.getMessage());
        }
    }
    
}
