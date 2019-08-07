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
package test.bot.settings;

import lightsearch.client.bot.settings.Configuration;
import lightsearch.client.bot.settings.ConfigurationInit;
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
public class ConfigurationTestNG {
    
    private Configuration configuration;
    
    @BeforeClass
    public void setUpClass() {
        String configurationName = getResourcesFilesPath() + "configuration.json";
        assertNotNull(configurationName, "ConfigurationName is null!");
        
        configuration = ConfigurationInit.configuration(configurationName);
    }
    
    @Test
    public void botSettingsName() {
        testBegin("Configuration", "botSettingsName()");
        
        assertNotNull(configuration, "Configuration is null!");
        System.out.println("botSettingsName(): " + configuration.botSettingsName());
        
        testEnd("Configuration", "botSettingsName()");
    }
    
    @Test
    public void globalSettingsName() {
        testBegin("Configuration", "globalSettingsName()");
        
        assertNotNull(configuration, "Configuration is null!");
        System.out.println("globalSettingsName(): " + configuration.globalSettingsName());
        
        testEnd("Configuration", "globalSettingsName()");
    }
    
    @Test
    public void isPerfomance() {
        testBegin("Configuration", "isPerformance()");
        
        assertNotNull(configuration, "Configuration is null!");
        System.out.println("isPerformance(): " + configuration.isPerformance());
        
        testEnd("Configuration", "isPerformance()");
    }
}
