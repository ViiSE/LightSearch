/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package lightsearch.server.producer.initialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lightsearch.server.data.pojo.LightSearchSettings;
import lightsearch.server.initialization.CurrentServerDirectory;
import lightsearch.server.initialization.ServerSettingsCreator;
import lightsearch.server.log.LogMessageTypeEnum;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

/**
 *
 * @author ViiSE
 */
@Component("serverSettingsCreatorProducerFromFileJSON")
public class ServerSettingsCreatorProducerFromFileJSONImpl implements ServerSettingsCreatorProducer {

    private final String SERVER_SETTINGS_CREATOR = "serverSettingsCreatorFromFileJSON";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ServerSettingsCreator getServerSettingsCreatorFromFileJSONInstance(CurrentServerDirectory currentServerDirectory) {
        return (ServerSettingsCreator) ctx.getBean(SERVER_SETTINGS_CREATOR, currentServerDirectory);
    }
}
