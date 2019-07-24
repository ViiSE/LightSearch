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
package lightsearch.client.bot.processor;

import java.util.ArrayList;
import java.util.List;
import lightsearch.client.bot.BotEntity;
import lightsearch.client.bot.settings.BotSettingsEnum;
import org.json.simple.JSONObject;

/**
 *
 * @author ViiSE
 */
public class BotEntityProcessorSimpleJSON extends BotEntityProcessor {

    private final String BOT_DTO        = BotSettingsEnum.BOT_DTO.toString();
    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String BOT_NAME       = BotSettingsEnum.BOT_NAME.toString();
    private final String USERNAME       = BotSettingsEnum.USERNAME.toString();
    private final String IMEI           = BotSettingsEnum.IMEI.toString();
    private final String CARD_CODE      = BotSettingsEnum.CARD_CODE.toString();
    private final String USER_IDENT     = BotSettingsEnum.USER_IDENT.toString();
    
    public BotEntityProcessorSimpleJSON(int botAmount) {
        super(botAmount);
    }

    @Override
    public List<BotEntity> apply(Object data) {
        JSONObject jData = (JSONObject) data;
        List<BotEntity> bots = new ArrayList<>();
        for(int i = 0; i < super.botAmount(); i++) {
            
        }
    }
    
}
