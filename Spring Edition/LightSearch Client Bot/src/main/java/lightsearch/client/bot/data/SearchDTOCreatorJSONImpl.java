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
package lightsearch.client.bot.data;

import lightsearch.client.bot.constants.BotSettingsEnum;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("searchDTOCreatorJSON")
@Scope("prototype")
public class SearchDTOCreatorJSONImpl implements SearchDTOCreator {

    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String BARCODE        = BotSettingsEnum.BARCODE.toString();
    private final String SKLAD          = BotSettingsEnum.SKLAD.toString();
    private final String TK_FIELD       = BotSettingsEnum.TK.toString();
    
    private final JSONObject data;

    @Autowired
    private ApplicationContext ctx;

    public SearchDTOCreatorJSONImpl(Object data) {
        this.data = (JSONObject) data;
    }

    @Override
    public SearchDTO createSearchDTO() {
        String impl    = data.get(IMPLEMENTATION).toString();
        String barcode = data.get(BARCODE).toString();
        String sklad   = data.get(SKLAD).toString();
        String TK      = data.get(TK_FIELD).toString();
            
        return (SearchDTO) ctx.getBean(impl, barcode, sklad, TK);
    }
}
