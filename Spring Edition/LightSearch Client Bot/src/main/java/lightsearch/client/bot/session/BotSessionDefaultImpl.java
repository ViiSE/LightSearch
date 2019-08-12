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

import lightsearch.client.bot.BotEntity;
import lightsearch.client.bot.BotsDoneSwitcher;
import lightsearch.client.bot.BotsChecker;
import lightsearch.client.bot.BotThread;
import lightsearch.client.bot.BotEntityCreator;
import lightsearch.client.bot.producer.BotEntityCreatorProducer;
import lightsearch.client.bot.producer.BotSettingsReaderProducer;
import lightsearch.client.bot.producer.BotsCheckerProducer;
import lightsearch.client.bot.producer.BotThreadProducer;
import lightsearch.client.bot.producer.ConnectionDTOProducer;
import lightsearch.client.bot.settings.BotSettingsReader;
import lightsearch.client.bot.settings.GlobalSettings;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired private BotSettingsReaderProducer botSettingsReaderProducer;
    @Autowired private BotEntityCreatorProducer botEntityCreatorProducer;
    @Autowired private BotsCheckerProducer botsCheckerProducer;
    @Autowired private ConnectionDTOProducer connectionDTOProducer;
    @Autowired private BotThreadProducer botThreadProducer;

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

        BotSettingsReader botSettingsReader = botSettingsReaderProducer.getBotSettingsReaderJSONFileInstance(botSettingsName);
        BotEntityCreator botEntityCreator = botEntityCreatorProducer.getBotEntityCreatorJSONInstance(botSettingsReader,
                serverIP, serverPort, delayMessageDisplay);

        List<BotEntity> botsEntities = botEntityCreator.botList();
        botsEntities.forEach(botEntity -> {
            BotThread bThread = botThreadProducer.getBotThreadDefaultInstance(botEntity);
            bots.add(bThread);
        });

        bots.forEach((bot) -> { bot.start(); });

        if(isPerformance) {
            BotsDoneSwitcher.addBots(bots.size());
            BotsChecker checker = botsCheckerProducer.getBotCheckerDefaultInstance(
                    connectionDTOProducer.getConnectionDTODefaultInstance(serverIP, serverPort));
            checker.start();
        }
    }
}
