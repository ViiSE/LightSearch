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
 * Предоставляет настройки LightSearch Server.
 * <p>
 * Настройками LightSearch Server является время перезагрузки сервера и тайм-аут клиента со стороны сервера.
 * <p>
 * Данный интерфейс необходим для настроек LightSearch Server из стороннего источника. Используется для создания
 * экземпляра реализации интерфейса {@link lightsearch.server.data.LightSearchServerSettingsDAO} по умолчанию.
 * @author ViiSE
 * @see lightsearch.server.data.LightSearchServerSettingsDAOImpl
 * @since 1.0.0
 */
public interface ServerSettings {
    int rebootServerValue();
    int timeoutClientValue();
}
