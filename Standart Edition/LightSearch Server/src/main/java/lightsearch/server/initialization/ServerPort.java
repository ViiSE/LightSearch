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
package lightsearch.server.initialization;

/**
 * Предоставляет порт, по которому будет работать LightSearch Server.
 * <p>
 * Данный интерфейс необходим для считывания порта LightSearch Server из стороннего источника. Используется для создания
 * экземпляра реализации интерфейса {@link lightsearch.server.data.LightSearchServerDTO} по умолчанию.
 * @since 1.0
 * @author ViiSE
 * @see lightsearch.server.data.LightSearchServerDTOImpl
 */
public interface ServerPort {
    int port();
}
