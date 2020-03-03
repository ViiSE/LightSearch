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

package lightsearch.updater.apk;

import lightsearch.updater.exception.APKException;
import lightsearch.updater.os.ReleasesDirectory;
import lightsearch.updater.producer.os.ReleasesDirectoryProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("apkVersionCreatorDefault")
public class APKVersionCreatorDefaultImpl implements APKVersionCreator {

    private final Logger logger = LoggerFactory.getLogger(APKVersionCreatorDefaultImpl.class);

    @Autowired
    private ReleasesDirectoryProducer releasesDirectoryProducer;
    private ReleasesDirectory releasesDirectory;

    @Override
    public synchronized void createNewVersion(String versionName) throws APKException {
        if(releasesDirectory == null)
            this.releasesDirectory = releasesDirectoryProducer.getReleasesDirectoryDefaultInstance();

        Path path = Paths.get(releasesDirectory.releasesDirectory() + File.separator + versionName);
        if(Files.exists(path))
            throw new APKException("Version " + versionName + " already exists!");
        else {
            try {
                Files.createDirectories(path);
                logger.info("New version is created: " + versionName);
            } catch (IOException ex) {
                logger.error("APKVersionCreator: createNewVersion exception: " + ex.getMessage());
                throw new APKException(ex.getMessage());
            }
        }
    }
}
