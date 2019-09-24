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
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import lightsearch.updater.apk.APKVersionCreator;
import lightsearch.updater.apk.APKVersionsUploader;
import lightsearch.updater.exception.APKException;

public class DialogNewVersion extends Dialog {

    public DialogNewVersion(
            APKVersionCreator apkVersionCreator, APKVersionsUploader apkVersionsUploader, ComboBoxVersions comboBoxVersions) {
        super();
        TextField textFieldDialog = new TextField();
        textFieldDialog.setRequired(true);
        textFieldDialog.setLabel("New Version");
        textFieldDialog.setPlaceholder("Enter new version");

        Button buttonDialogOK = new Button("Create");
        buttonDialogOK.addClickListener(event -> {
            if(!textFieldDialog.isEmpty()) {
                try {
                    apkVersionCreator.createNewVersion(textFieldDialog.getValue());
                    comboBoxVersions.setDataProvider(new ListDataProvider<>(apkVersionsUploader.uploadVersions()));
                    Notification.show("New version \"" + textFieldDialog.getValue() + "\" is created!");
                    textFieldDialog.setInvalid(false);
                    textFieldDialog.clear();
                    super.close();
                } catch (APKException ex) {
                    textFieldDialog.setErrorMessage(ex.getMessage());
                    textFieldDialog.setInvalid(true);
                }
            } else
                textFieldDialog.setErrorMessage("Enter release version!");
        });

        super.add(textFieldDialog, buttonDialogOK);
    }
}
