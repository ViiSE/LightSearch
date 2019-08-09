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

package lightsearch.client.bot.settings;

import lightsearch.client.bot.constants.BotSettingsEnum;
import lightsearch.client.bot.exception.SettingsParserException;
import lightsearch.client.bot.parser.SettingsParser;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("botSettingsReaderJSONFile")
@Scope("prototype")
public class BotSettingsReaderJSONFileImpl implements BotSettingsReader {

    private final String TYPE       = BotSettingsEnum.TYPE.toString();
    private final String BOT_AMOUNT = BotSettingsEnum.BOT_AMOUNT.toString();

    private final String SETTINGS_READER = "settingsReaderFile";
    private final String SETTINGS_PARSER = "settingsParserJSON";

    private final String fileName;
    private String botSettingsType;
    private int botAmount;
    private JSONObject data;

    @Autowired
    private ApplicationContext ctx;

    private boolean isRead;

    public BotSettingsReaderJSONFileImpl(String fileName) {
        this.fileName = fileName;
    }

    private void read() {
        try {
            SettingsReader reader = (SettingsReader) ctx.getBean(SETTINGS_READER, fileName);
            SettingsParser parser = ctx.getBean(SETTINGS_PARSER, SettingsParser.class);
            data = (JSONObject) parser.parse(reader.settingsContent());

            botSettingsType = data.get(TYPE).toString();
            botAmount       = Integer.parseInt(data.get(BOT_AMOUNT).toString());
        }
        catch(SettingsParserException ex) {
            throw new RuntimeException("Error " + fileName + " file: " + ex.getMessage());
        }
        isRead = true;
    }

    @Override
    public String type() {
        if(!isRead) read();
        return botSettingsType;
    }

    @Override
    public int botAmount() {
        if(!isRead) read();
        return botAmount;
    }

    @Override
    public Object data() {
        if(!isRead) read();
        return data;
    }
}
