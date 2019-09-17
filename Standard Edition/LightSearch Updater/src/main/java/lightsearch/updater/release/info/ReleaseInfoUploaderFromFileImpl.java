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

import lightsearch.updater.exception.ReleaseInfoCreatorException;
import lightsearch.updater.exception.ReleaseInfoException;
import lightsearch.updater.os.InfoDirectory;
import lightsearch.updater.producer.os.InfoDirectoryProducer;
import lightsearch.updater.producer.release.info.ReleaseInfoCreatorProducer;
import lightsearch.updater.producer.release.info.ReleaseInfoPathProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component("releaseInfoUploaderFromFile")
public class ReleaseInfoUploaderFromFileImpl implements ReleaseInfoUploader {

    private final Logger logger = LoggerFactory.getLogger(ReleaseInfoUploaderFromFileImpl.class);

    @Autowired private ReleaseInfoCreatorProducer infoCreatorProducer;
    @Autowired private ReleaseInfoPathProducer releaseInfoPathProducer;

    private ReleaseInfoCreator infoCreator;
    private ReleaseInfoPath releaseInfoPath;

    @Override
    public Object uploadInfo() throws ReleaseInfoException {
        if(infoCreator == null || releaseInfoPath == null)
            init();

        Path path = releaseInfoPath.releaseInfoPath();
        if(Files.exists(path)) {
            try {
                StringBuilder info = new StringBuilder();
                Files.readAllLines(path).forEach(line -> info.append(line).append("\n"));

                logger.info("Release info is uploaded");
                return info.toString();
            } catch (IOException ex) {
                logger.error("uploadInfo: " + ex.getMessage());
                throw new ReleaseInfoException(ex.getMessage());
            }
        }
        else {
            try {
                String info = infoCreator.createInfo(path);

                logger.info("Release info is uploaded");
                return info;
            }
            catch (ReleaseInfoCreatorException ex) {
                logger.error("uploadInfo: " + ex.getMessage());
                throw new ReleaseInfoException(ex.getMessage());
            }
        }
    }

    private void init() {
        this.infoCreator = infoCreatorProducer.getReleaseInfoCreatorJSONAppUpdaterInstance();
        this.releaseInfoPath = releaseInfoPathProducer.getReleaseInfoPathDefaultInstance();
    }
}
