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

import java.util.Map;

/**
 * Интерфейс, предоставляющий карту администраторов.
 * <p>
 * Ключом в данной карте является имя администратора, а значением - хэш пароля администратора. Данное соглашение
 * является по-умолчанию, и разработчик может сам задать свое соглашение для пары ключ-значение.
 * <p>
 * Данный интерфейс необходим для считывания администраторов из стороннего источника. Используется для создания
 * экземпляра реализации интерфейса {@link lightsearch.server.data.LightSearchServerDTO} по умолчанию:
 * {@link lightsearch.server.data.LightSearchServerDTOImpl}.
 * @since 1.0
 * @author ViiSE
 */
public interface AdministratorsMap {
    Map<String,String> administratorsMap();
}
