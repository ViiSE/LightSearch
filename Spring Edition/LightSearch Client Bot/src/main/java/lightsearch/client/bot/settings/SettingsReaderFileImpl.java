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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service("settingsReaderFile")
@Scope("prototype")
public class SettingsReaderFileImpl implements SettingsReader {

    private final String OS_DETECTOR       = "osDetectorDefault";
    private final String CURRENT_DIRECTORY = "currentDirectoryFromFile";

    private String settingsName;

    @Autowired
    private ApplicationContext ctx;

    public SettingsReaderFileImpl(String settingsName) {
        this.settingsName = settingsName;
    }

    @Override
    public String settingsContent() {
        OsDetector osDetector = ctx.getBean(OS_DETECTOR, OsDetector.class);
        CurrentDirectory currDir = (CurrentDirectory) ctx.getBean(CURRENT_DIRECTORY, osDetector);

        try(FileInputStream fin = new FileInputStream(currDir.currentDirectory() + settingsName);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin))) {
            return br.lines().collect(Collectors.joining());
        }
        catch(IOException ex) {
            throw new RuntimeException("Error " + settingsName + " file: " + ex.getMessage());
        }
    }
}
