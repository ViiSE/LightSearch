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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service("apkSaverDefault")
@Scope("prototype")
public class APKSaverDefaultImpl implements APKSaver {

    private final Logger logger = LoggerFactory.getLogger(APKSaverDefaultImpl.class);

    @Autowired
    private ReleasesDirectoryProducer releasesDirectoryProducer;
    private ReleasesDirectory releasesDirectory;

    @Override
    public synchronized void saveAPK(APK apk) throws APKException {
        if(releasesDirectory == null)
            releasesDirectory = releasesDirectoryProducer.getReleasesDirectoryDefaultInstance();

        Path apkPath = Paths.get(releasesDirectory.releasesDirectory() + File.separator +
                apk.getVersion() + File.separator + apk.getAPKName());

        try {
            Files.write(apkPath, apk.getContent());
            logger.info("APK is saved: version - " + apk.getVersion() + ", name - " + apk.getAPKName());
        } catch (IOException ex) {
            logger.error("APKSaver: saveAPK exception: " + ex.getMessage());
            throw new APKException(ex.getMessage());
        }
    }
}
