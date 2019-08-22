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
 * Реализация интерфейса {@link HandlerIdentifier} по умолчанию.
 * <p>
 * Значение статического поля {@link #incrementValue} при старте LightSearch Server равняется нулю. После каждого вызова
 * метода {@link #next()} значение этого поля увеличивается на 1, и возвращается вызываемому методу.
 * @author ViiSE
 * @since 2.0
 */
public class HandlerIdentifierDefaultImpl implements HandlerIdentifier {

    private static long incrementValue = 0;

    @Override
    public long next() {
        return ++incrementValue;
    }
    
}
