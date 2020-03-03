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

package lightsearch.updater.ui.views.main.workspace.version;

import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import lightsearch.updater.apk.APK;
import lightsearch.updater.apk.APKSaver;
import lightsearch.updater.apk.APKVersionCreator;
import lightsearch.updater.apk.APKVersionsUploader;
import lightsearch.updater.ui.views.main.LabelWorkspace;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("apkVersionWorkspaceCreatorDefault")
@Scope("prototype")
public class APKVersionWorkspaceCreatorDefaultImpl implements APKVersionWorkspaceCreator {

    private final APKSaver apkSaver;
    private final APKVersionsUploader apkVersionsUploader;
    private final APKVersionCreator apkVersionCreator;
    private final APK apk;

    public APKVersionWorkspaceCreatorDefaultImpl(
            APKSaver apkSaver, APKVersionsUploader apkVersionsUploader, APKVersionCreator apkVersionCreator, APK apk) {
        this.apkSaver = apkSaver;
        this.apkVersionsUploader = apkVersionsUploader;
        this.apkVersionCreator = apkVersionCreator;
        this.apk = apk;
    }

    @Override
    public APKVersionWorkspace createApkVersionWorkspace() {
        LabelWorkspace label = new LabelWorkspace("Releases Manager");
        ComboBoxVersions comboBoxVersions = new ComboBoxVersions("Versions Releases", apkVersionsUploader, apk);
        DialogNewVersion dialogNewVersion = new DialogNewVersion(apkVersionCreator, apkVersionsUploader, comboBoxVersions);
        ButtonCreateNewVersion buttonCreateNewVersion = new ButtonCreateNewVersion("Create new version release", dialogNewVersion);
        UploadAPK uploadAPK = new UploadAPK(apk, new MemoryBuffer());
        ButtonSubmitChanges buttonSubmitChanges = new ButtonSubmitChanges("Submit changes", apk, apkSaver, comboBoxVersions);

        return new APKVersionWorkspace(label, comboBoxVersions, buttonCreateNewVersion, uploadAPK, buttonSubmitChanges);
    }
}
