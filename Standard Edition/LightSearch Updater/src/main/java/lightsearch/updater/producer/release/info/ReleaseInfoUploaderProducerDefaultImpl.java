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

package lightsearch.updater.producer.release.info;

import lightsearch.updater.release.info.ReleaseInfoUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("releaseInfoUploaderProducerDefault")
public class ReleaseInfoUploaderProducerDefaultImpl implements ReleaseInfoUploaderProducer {

    private final String RELEASE_INFO_UPLOADER = "releaseInfoUploaderFromFile";

    @Autowired
    private ApplicationContext ctx;

    @Override
    public ReleaseInfoUploader getReleaseInfoUploaderFromFileInstance() {
        return ctx.getBean(RELEASE_INFO_UPLOADER, ReleaseInfoUploader.class);
    }
}
