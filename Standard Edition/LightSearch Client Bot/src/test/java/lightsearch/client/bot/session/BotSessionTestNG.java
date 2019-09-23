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

import lightsearch.client.bot.processor.TestProcessorServer;
import lightsearch.client.bot.settings.Configuration;
import lightsearch.client.bot.settings.ConfigurationInit;
import lightsearch.client.bot.settings.GlobalSettings;
import lightsearch.client.bot.settings.GlobalSettingsInit;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;
import static test.ResourcesFilesPath.getResourcesFilesPath;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class BotSessionTestNG {
    
    private final String configurationName = getResourcesFilesPath() + "configuration.json";
    private String botSettingsName;
    private GlobalSettings globalSettings;
    private boolean isPerformance;
    
    @BeforeClass
    @Parameters({"ip", "port", "answerAuth"})
    public void setUpClass(String ip, int port, String answerMessage) {
        Thread serverThread = new Thread(new TestProcessorServer(port, answerMessage));
        serverThread.start();
        
        Configuration configuration = ConfigurationInit.configuration(configurationName);
        globalSettings = GlobalSettingsInit.globalSettingsCreator(getResourcesFilesPath() + configuration.globalSettingsName());
        botSettingsName = getResourcesFilesPath() + configuration.botSettingsName();
        isPerformance = configuration.isPerformance();
    }
    
    @Test
    @Parameters
    public void startSession() {
        testBegin("botSession", "startSession()");
        
        assertNotNull(globalSettings, "GlobalSettings is null!");
        assertNotNull(botSettingsName, "BotSettingsName is null!");
        
        BotSession botSession = BotSessionInit.botSession(botSettingsName,
                globalSettings, isPerformance);
        assertNotNull(botSession, "botSession is null!");

        botSession.startSession();
        
        testEnd("botSession", "startSession()");
    }
}
