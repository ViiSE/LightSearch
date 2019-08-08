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

package lightsearch.client.bot.configuration;

import lightsearch.client.bot.settings.GlobalSettings;
import lightsearch.client.bot.settings.GlobalSettingsJSONFileImpl;
import lightsearch.client.bot.settings.SettingsReader;
import lightsearch.client.bot.settings.SettingsReaderFileImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.function.Function;

@ComponentScan(basePackages = "lightsearch.client.bot.*")
@Configuration
public class LightSearchClientBotConfiguration {

    @Bean
    public Function<String, SettingsReader> settingsReaderFactory() {
        return settingsName -> settingsReaderFileImpl(settingsName);
    }
    @Bean
    @Scope(value = "prototype")
    public SettingsReaderFileImpl settingsReaderFileImpl(String settingsName) {
        return new SettingsReaderFileImpl(settingsName);
    }

    @Bean
    public Function<String, GlobalSettings> globalSettingsFactory() {
        return fileName -> globalSettingsJSONFileImpl(fileName);
    }
    @Bean
    @Scope(value = "prototype")
    public GlobalSettings globalSettingsJSONFileImpl(String fileName) {
        return new GlobalSettingsJSONFileImpl(fileName);
    }
}
