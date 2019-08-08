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

package lightsearch.client.bot;

import lightsearch.client.bot.about.AppGreetings;
import lightsearch.client.bot.constants.BeansName;
import lightsearch.client.bot.constants.ResourcesName;
import lightsearch.client.bot.settings.Configuration;
import lightsearch.client.bot.settings.GlobalSettings;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

@SpringBootApplication
public class LightSearchClientBot {

    private static final String LIGHTSEARCH_CLIENT_BOT_APP_CONTEXT = ResourcesName.LIGHTSEARCH_CLIENT_BOT_APP_CONTEXT;

    private static final String GREETINGS       = BeansName.GREETINGS;
    private static final String CONFIGURATION   = BeansName.CONFIGURATION;
    private static final String GLOBAL_SETTINGS = BeansName.GLOBAL_SETTINGS;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(LightSearchClientBot.class, args);
        AppGreetings greetings = ctx.getBean(GREETINGS, AppGreetings.class);

        System.out.println(greetings.greetings());

        Configuration configuration;
        if(args.length == 0)
            configuration = ctx.getBean(CONFIGURATION, Configuration.class);
        else
            configuration = (Configuration) ctx.getBean(CONFIGURATION, args[0]);

        GlobalSettings globalSettings = (GlobalSettings) ctx.getBean(GLOBAL_SETTINGS, configuration.globalSettingsName());
        System.out.println(globalSettings.delayMessageDisplay());
        System.out.println(globalSettings.serverIP());
        System.out.println(globalSettings.serverPort());
    }
}
