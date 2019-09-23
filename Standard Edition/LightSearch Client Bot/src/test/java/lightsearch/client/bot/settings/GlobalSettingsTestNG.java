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

import lightsearch.client.bot.settings.GlobalSettings;
import lightsearch.client.bot.settings.GlobalSettingsInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static test.ResourcesFilesPath.getResourcesFilesPath;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class GlobalSettingsTestNG {
    
    private GlobalSettings globalSettings;
    
    @BeforeClass
    public void setUpClass() {
        String globalSettingsName = getResourcesFilesPath() + "global_settings.json";
        assertNotNull(globalSettingsName, "GlobalSettings FileName is null!");
        
        globalSettings = GlobalSettingsInit.globalSettingsCreator(globalSettingsName);
    }
    
    @Test
    public void serverIP() {
        testBegin("GlobalSettings", "serverIP()");
        
        assertNotNull(globalSettings, "GlobalSettings is null!");
        System.out.println("serverIP(): " + globalSettings.serverIP());
        
        testEnd("GlobalSettings", "serverIP()");
    }
    
    @Test
    public void serverPort() {
        testBegin("GlobalSettings", "serverPort()");
        
        assertNotNull(globalSettings, "GlobalSettings is null!");
        System.out.println("serverPort(): " + globalSettings.serverPort());
        
        testEnd("GlobalSettings", "serverPort()");
    }
    
    @Test
    public void delayMessageDisplay() {
        testBegin("GlobalSettings", "delayMessageDisplay()");
        
        assertNotNull(globalSettings, "GlobalSettings is null!");
        System.out.println("delayMessageDisplay(): " + globalSettings.delayMessageDisplay());
        
        testEnd("GlobalSettings", "delayMessageDisplay()");
    }
}
