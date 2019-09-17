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

import lightsearch.updater.exception.ReleaseInfoException;
import lightsearch.updater.producer.release.info.ReleaseInfoPathProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component("releaseInfoSaverToFile")
public class ReleaseInfoSaverToFileImpl implements ReleaseInfoSaver {

    private final Logger logger = LoggerFactory.getLogger(ReleaseInfoSaverToFileImpl.class);

    @Autowired
    private ReleaseInfoPathProducer releaseInfoPathProducer;
    private ReleaseInfoPath releaseInfoPath;

    @Override
    public synchronized void saveInfo(String infoContent) throws ReleaseInfoException {
        if(releaseInfoPath == null)
            releaseInfoPath = releaseInfoPathProducer.getReleaseInfoPathDefaultInstance();

        Path infoPath = releaseInfoPath.releaseInfoPath();

        try {
            Files.write(infoPath, infoContent.getBytes());
            logger.info("Release info is save");
        } catch (IOException ex) {
            logger.error("ReleaseInfoSaver: saveInfo exception: " + ex.getMessage());
            throw new ReleaseInfoException("Error writing to file: " + ex.getMessage());
        }
    }
}
