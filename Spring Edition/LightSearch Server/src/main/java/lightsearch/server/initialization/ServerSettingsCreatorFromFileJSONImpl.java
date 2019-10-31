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
package lightsearch.server.initialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lightsearch.server.data.pojo.LightSearchSettings;
import lightsearch.server.log.LoggerServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

import static lightsearch.server.log.LogMessageTypeEnum.ERROR;
import static lightsearch.server.log.LogMessageTypeEnum.INFO;

/**
 *
 * @author ViiSE
 */
@Component("serverSettingsCreatorFromFileJSON")
@Scope("prototype")
public class ServerSettingsCreatorFromFileJSONImpl implements ServerSettingsCreator {

    @Autowired
    private LoggerServer logger;

    private final String currentDirectory;
    
    public ServerSettingsCreatorFromFileJSONImpl(CurrentServerDirectory currentServerDirectory) {
        this.currentDirectory = currentServerDirectory.currentDirectory();
    }
    
    @Override
    public void createSettings() {
        LightSearchSettings settings;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            settings = objectMapper.readValue(new File(currentDirectory + "settings"), LightSearchSettings.class);
            logger.log(ServerSettingsCreatorFromFileJSONImpl.class, INFO,
                    "LightSearch Server settings is loaded: " + settings.toString());
        } catch (IOException ex) {
            settings = new LightSearchSettings();
            settings.setRebootTime(LocalTime.of(7, 0));
            settings.setTimeoutClient(0);
            logger.log(ServerSettingsCreatorFromFileJSONImpl.class, ERROR, "Cannot create settings." +
                    "Default settings applied: " + settings.toString() + ". Exception: " + ex.getMessage());
        }
    }
}
