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
package lightsearch.server.message.result;

import lightsearch.server.identifier.IdentifierEnum;
import org.json.simple.JSONObject;

/**
 * Реализация интерфейса {@link lightsearch.server.message.result.DeviceInformation}, принимающая сообщение, которое
 * содержит информацию о подключаемом клиенте в формате JSON.
 * <p>
 * Из сообщения, которое пришло от клиента, LightSearch Server достает идентификатор клиента, который в дальнейшем
 * используется для создания соответствующего обработчика клиента.
 * <p>
 * Для получения идентификатора клиента из сообщения в формате JSON используется библиотека
 * <a href="https://github.com/fangyidong/json-simple">JSON.simple</a>.
 * @author ViiSE
 * @since 1.0
 */
public class DeviceInformationJSONImpl implements DeviceInformation {

    private final String IDENTIFIER = IdentifierEnum.IDENTIFIER.stringValue();
    private final JSONObject devInfo;
    
    public DeviceInformationJSONImpl(Object devInfo) {
        this.devInfo = (JSONObject)devInfo;
    }

    @Override
    public String identifier() {
        return devInfo.get(IDENTIFIER).toString();
    }
    
}
