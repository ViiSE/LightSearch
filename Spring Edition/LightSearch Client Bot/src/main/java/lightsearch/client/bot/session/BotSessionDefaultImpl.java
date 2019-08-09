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

package lightsearch.client.bot.session;

import lightsearch.client.bot.*;
import lightsearch.client.bot.data.ConnectionDTO;
import lightsearch.client.bot.settings.BotSettingsReader;
import lightsearch.client.bot.settings.GlobalSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("botSessionDefault")
@Scope("prototype")
public class BotSessionDefaultImpl implements BotSession {

    private final String botSettingsName;
    private final String serverIP;
    private final int serverPort;
    private final long delayMessageDisplay;
    private final boolean isPerformance;

    private final String BOT_SETTINGS_READER = "botSettingsReaderJSONFile";
    private final String BOT_ENTITY_CREATOR  = "botEntityCreatorJSON";
    private final String BOT_THREAD          = "botThreadDefault";
    private final String BOTS_CHECKER        = "botsCheckerDefault";
    private final String CONNECTION_DTO      = "connectionDTODefault";

    @Autowired
    private ApplicationContext ctx;

    public BotSessionDefaultImpl(String botSettingsName, GlobalSettings globalSettings, boolean isPerformance) {
        this.botSettingsName = botSettingsName;
        this.serverIP = globalSettings.serverIP();
        this.serverPort = globalSettings.serverPort();
        this.delayMessageDisplay = globalSettings.delayMessageDisplay();
        this.isPerformance = isPerformance;
    }

    @Override
    public void startSession() {
        List<BotThread> bots = new ArrayList<>();

        BotSettingsReader botSettingsReader = (BotSettingsReader) ctx.getBean(BOT_SETTINGS_READER, botSettingsName);
        BotEntityCreator botEntityCreator = (BotEntityCreator) ctx.getBean(BOT_ENTITY_CREATOR, botSettingsReader, serverIP, serverPort, delayMessageDisplay);

        List<BotEntity> botsEntities = botEntityCreator.botList();
        botsEntities.forEach(botEntity -> {
            BotThread bThread = (BotThread) ctx.getBean(BOT_THREAD, botEntity);
            bots.add(bThread);
        });

        bots.forEach((bot) -> { bot.start(); });

        if(isPerformance) {
            BotsDoneSwitcher.addBots(bots.size());
            BotsChecker checker = (BotsChecker) ctx.getBean(BOTS_CHECKER, (ConnectionDTO) ctx.getBean(CONNECTION_DTO, serverIP, serverPort));
            checker.start();
        }
    }
}
