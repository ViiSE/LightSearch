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

import lightsearch.client.bot.constants.GlobalSettingsName;
import lightsearch.client.bot.exception.SettingsParserException;
import lightsearch.client.bot.parser.SettingsParser;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;

@Service("globalSettingsJSONFile")
@Scope("prototype")
public class GlobalSettingsJSONFileImpl implements GlobalSettings {

    private final String SETTINGS_READER = "settingsReaderFile";
    private final String SETTINGS_PARSER = "settingsParserJSON";

    private final String SERVER_IP             = GlobalSettingsName.SERVER_IP;
    private final String SERVER_PORT           = GlobalSettingsName.SERVER_PORT;
    private final String DELAY_MESSAGE_DISPLAY = GlobalSettingsName.DELAY_MESSAGE_DISPLAY;

    private final String fileName;

    private String serverIP;
    private int serverPort;
    private long delayMessageDisplay;

    private boolean isRead = false;

    @Autowired
    private ApplicationContext ctx;

    public GlobalSettingsJSONFileImpl(String fileName) {
        this.fileName = fileName;
    }

    private void read() {
        try {
            SettingsReader reader = (SettingsReader) ctx.getBean(SETTINGS_READER, fileName);
            SettingsParser parser = ctx.getBean(SETTINGS_PARSER, SettingsParser.class);

            JSONObject jObj = (JSONObject) parser.parse(reader.settingsContent());

            serverIP = jObj.get(SERVER_IP).toString();
            serverPort = Integer.parseInt(jObj.get(SERVER_PORT).toString());
            delayMessageDisplay = Integer.parseInt(jObj.get(DELAY_MESSAGE_DISPLAY).toString());
        } catch(SettingsParserException ex) {
            throw new RuntimeException("Error in file " + fileName + ".json. "
                    + "Exception: " + ex.getMessage());
        }
        isRead = true;
    }

    @Override
    public long delayMessageDisplay() {
        if(!isRead) read();
        return delayMessageDisplay;
    }

    @Override
    public String serverIP() {
        if(!isRead) read();
        return serverIP;
    }

    @Override
    public int serverPort() {
        if(!isRead) read();
        return serverPort;
    }
}
