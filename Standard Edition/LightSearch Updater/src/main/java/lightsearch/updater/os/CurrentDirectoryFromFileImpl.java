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
package lightsearch.updater.os;

import lightsearch.updater.LightSearchUpdater;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URISyntaxException;

/**
 *
 * @author ViiSE
 */
@Component("currentDirectoryFromFile")
public class CurrentDirectoryFromFileImpl implements CurrentDirectory {

    @Override
    public String currentDirectory() {
        return System.getProperty("user.dir") + File.separator;
    }
}
