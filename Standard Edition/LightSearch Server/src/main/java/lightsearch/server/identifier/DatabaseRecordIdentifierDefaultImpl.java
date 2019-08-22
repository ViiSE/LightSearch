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

/**
 * Реализация интерфейса {@link DatabaseRecordIdentifier} по умолчанию.
 * <p>
 * Значение идентификатора лежит в пределах от 0 до 1 000 000. Если значение идентификатора после операции инкремента
 * равняется 1 000 000, то его значение обнуляется.
 * <p>
 * Поле {@link #databaseRecordIdentifier} статично. После первого вызова конструктора поле
 * {@link #databaseRecordIdentifier} не меняет свое значение.
 * @author ViiSE
 * @since 2.0
 */
public class DatabaseRecordIdentifierDefaultImpl implements DatabaseRecordIdentifier {

    private final long MAX = 1000000;
    private static boolean first = true;
    private static long databaseRecordIdentifier;
    
    public DatabaseRecordIdentifierDefaultImpl(long databaseRecordIdentifier) {
        if(first) {
            DatabaseRecordIdentifierDefaultImpl.databaseRecordIdentifier = databaseRecordIdentifier;
            first = false;
        }
    }
    
    @Override
    synchronized public long next() {
        ++databaseRecordIdentifier;
        if(databaseRecordIdentifier >= MAX)
            databaseRecordIdentifier = 0;
        return databaseRecordIdentifier;
    }

    @Override
    public long databaseRecordIdentifier() {
        return databaseRecordIdentifier;
    }
    
}
