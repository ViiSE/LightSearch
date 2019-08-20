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
 * Предоставляет настройки базы данных.
 * <p>
 * Настройки базы данных являются данными подключения к базе и состоят из имени, IP-адреса и порта базы данных.
 * <p>
 * Данный интерфейс необходим для считывания настроек базы данных из стороннего источника. Используется для создания
 * экземпляра реализации интерфейса {@link lightsearch.server.data.LightSearchServerDatabaseDTO} по умолчанию.
 * @author ViiSE
 * @see lightsearch.server.data.LightSearchServerDatabaseDTODefaultImpl
 * @since 1.0
 */
public interface DatabaseSettings {
    String name();
    String ip();
    int port();
}
