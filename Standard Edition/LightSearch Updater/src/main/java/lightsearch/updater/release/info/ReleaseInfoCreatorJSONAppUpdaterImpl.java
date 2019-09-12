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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component("releaseInfoCreatorJSONAppUpdater")
public class ReleaseInfoCreatorJSONAppUpdaterImpl implements ReleaseInfoCreator {

    private final Logger logger = LoggerFactory.getLogger(ReleaseInfoCreatorJSONAppUpdaterImpl.class);

    @Override
    public String createInfo(Path path) throws ReleaseInfoCreatorException {
        String content =
                "{\n" +
                        "  \"latestVersion\": \"input_latest_version_here\",\n" +
                        "  \"latestVersionCode\": \"input_latest_version_code_here\",\n" +
                        "  \"url\": \"input_url_to_apk_here\",\n" +
                        "  \"releaseNotes\": [\n" +
                            "    \"- input\",\n" +
                            "    \"- release\",\n" +
                            "    \"- notes\",\n" +
                            "    \"- here\"\n" +
                        "  ]\n" +
                "}";

        try {
            Files.write(path, content.getBytes());
            logger.info("Create release info with AppUpdater API");
            return content;
        } catch (IOException ex) {
            logger.error("Create info: " + ex.getMessage());
            throw new ReleaseInfoCreatorException(ex.getMessage());
        }
    }
}
