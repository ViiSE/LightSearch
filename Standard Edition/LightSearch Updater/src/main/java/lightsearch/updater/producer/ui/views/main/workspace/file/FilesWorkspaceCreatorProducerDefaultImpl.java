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

package lightsearch.updater.producer.ui.views.main.workspace.file;

import lightsearch.updater.producer.release.info.ReleaseInfoCheckerProducer;
import lightsearch.updater.producer.release.info.ReleaseInfoSaverProducer;
import lightsearch.updater.producer.release.info.ReleaseInfoUploaderProducer;
import lightsearch.updater.release.info.ReleaseInfoChecker;
import lightsearch.updater.release.info.ReleaseInfoSaver;
import lightsearch.updater.release.info.ReleaseInfoUploader;
import lightsearch.updater.ui.views.main.workspace.file.FilesWorkspaceCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service("fileWorkspaceCreatorProducerDefault")
public class FilesWorkspaceCreatorProducerDefaultImpl implements FilesWorkspaceCreatorProducer {

    private final String FILES_WORKSPACE_CREATOR = "filesWorkspaceCreatorDefault";

    @Autowired private ApplicationContext ctx;

    @Autowired private ReleaseInfoUploaderProducer infoUploaderProducer;
    @Autowired private ReleaseInfoCheckerProducer infoCheckerProducer;
    @Autowired private ReleaseInfoSaverProducer infoSaverProducer;

    @Override
    public FilesWorkspaceCreator getFilesWorkspaceCreatorDefaultInstance() {
        ReleaseInfoUploader infoUploader = infoUploaderProducer.getReleaseInfoUploaderFromFileInstance();
        ReleaseInfoChecker infoChecker = infoCheckerProducer.getReleaseInfoCheckerJSONInstance();
        ReleaseInfoSaver infoSaver = infoSaverProducer.getReleaseInfoSaverToFileInstance();

        return (FilesWorkspaceCreator) ctx.getBean(FILES_WORKSPACE_CREATOR, infoUploader, infoChecker, infoSaver);
    }
}
