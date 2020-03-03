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

package lightsearch.updater.producer.ui.views.main.workspace.version;

import lightsearch.updater.apk.APK;
import lightsearch.updater.apk.APKSaver;
import lightsearch.updater.apk.APKVersionCreator;
import lightsearch.updater.apk.APKVersionsUploader;
import lightsearch.updater.producer.apk.APKProducer;
import lightsearch.updater.producer.apk.APKSaverProducer;
import lightsearch.updater.producer.apk.APKVersionCreatorProducer;
import lightsearch.updater.producer.apk.APKVersionsUploaderProducer;
import lightsearch.updater.ui.views.main.workspace.version.APKVersionWorkspaceCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("apkVersionWorkspaceCreatorProducerDefault")
public class APKVersionWorkspaceCreatorProducerDefaultImpl implements APKVersionWorkspaceCreatorProducer {

    private final String APK_VERSION_WORKSPACE_CREATOR = "apkVersionWorkspaceCreatorDefault";

    @Autowired private ApplicationContext ctx;

    @Autowired private APKSaverProducer apkSaverProducer;
    @Autowired private APKVersionsUploaderProducer apkVersionsUploaderProducer;
    @Autowired private APKVersionCreatorProducer apkVersionCreatorProducer;
    @Autowired private APKProducer apkProducer;

    @Override
    public APKVersionWorkspaceCreator getApkVersionWorkspaceCreatorDefaultInstance() {
        APKSaver apkSaver = apkSaverProducer.getAPKSaverDefaultInstance();
        APKVersionsUploader apkVersionsUploader = apkVersionsUploaderProducer.getApkVersionsUploaderDefaultInstance();
        APKVersionCreator apkVersionCreator = apkVersionCreatorProducer.getAPKVersionCreatorDefaultInstance();
        APK apk = apkProducer.getAPKDefaultInstance();

        return (APKVersionWorkspaceCreator) ctx.getBean(APK_VERSION_WORKSPACE_CREATOR, apkSaver, apkVersionsUploader, apkVersionCreator, apk);
    }
}
