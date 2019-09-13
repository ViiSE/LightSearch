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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import lightsearch.updater.apk.APK;
import lightsearch.updater.apk.APKSaver;
import lightsearch.updater.exception.APKException;

public class ButtonSubmitChanges extends Button {

    public ButtonSubmitChanges(String label, APK apk, APKSaver apkSaver, ComboBoxVersions comboBoxVersions) {
        super(label);
        super.setWidthFull();
        super.addClickListener(event -> {
            if(apk.getVersion() != null && !(apk.getVersion().equals(""))) {
                if (apk.getContent() != null) {
                    try {
                        apkSaver.saveAPK(apk);
                        Notification.show("APK was saved successfully!");
                        comboBoxVersions.setInvalid(false);
                    } catch (APKException ex) {
                        Notification.show(ex.getMessage());
                    }
                }
                else
                    Notification.show("Upload APK file!");
            }
            else {
                comboBoxVersions.setErrorMessage("Select APK version!");
                comboBoxVersions.setInvalid(true);
            }
        });
    }
}
