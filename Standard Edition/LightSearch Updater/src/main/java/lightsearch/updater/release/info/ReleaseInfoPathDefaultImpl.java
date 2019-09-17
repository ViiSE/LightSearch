/*
 *  Copyright 2019 ViiSE
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package lightsearch.updater.release.info;

import lightsearch.updater.os.InfoDirectory;
import lightsearch.updater.producer.os.InfoDirectoryProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("releaseInfoPathDefault")
public class ReleaseInfoPathDefaultImpl implements ReleaseInfoPath {

    @Autowired
    private InfoDirectoryProducer infoDirectoryProducer;
    private InfoDirectory infoDirectory;

    @Override
    public Path releaseInfoPath() {
        if(infoDirectory == null)
            infoDirectory = infoDirectoryProducer.getInfoDirectoryDefaultInstance();

        return Paths.get(infoDirectory.infoDirectory() + File.separator + "update.json");
    }
}
