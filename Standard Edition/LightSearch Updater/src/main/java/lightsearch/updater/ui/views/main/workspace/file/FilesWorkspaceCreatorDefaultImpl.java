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

package lightsearch.updater.ui.views.main.workspace.file;

import lightsearch.updater.release.info.ReleaseInfoChecker;
import lightsearch.updater.release.info.ReleaseInfoSaver;
import lightsearch.updater.release.info.ReleaseInfoUploader;
import lightsearch.updater.ui.views.main.LabelWorkspace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("filesWorkspaceCreatorDefault")
@Scope("prototype")
public class FilesWorkspaceCreatorDefaultImpl implements FilesWorkspaceCreator {

    private final ReleaseInfoUploader infoUploader;
    private final ReleaseInfoChecker infoChecker;
    private final ReleaseInfoSaver infoSaver;

    public FilesWorkspaceCreatorDefaultImpl(
            ReleaseInfoUploader infoUploader, ReleaseInfoChecker infoChecker, ReleaseInfoSaver infoSaver) {
        this.infoUploader = infoUploader;
        this.infoChecker = infoChecker;
        this.infoSaver = infoSaver;
    }

    @Override
    public FilesWorkspace createFilesWorkspace() {
        LabelWorkspace label = new LabelWorkspace("Release Information Editor");
        ReleaseEditor editor = new ReleaseEditor(infoUploader);
        ButtonChangeInfo buttonChangeInfo = new ButtonChangeInfo("Change info", editor, infoChecker, infoSaver);
        TextAreaWorkspace textAreaWorkspace = new TextAreaWorkspace(label, editor);

        return new FilesWorkspace(textAreaWorkspace, buttonChangeInfo);
    }
}
