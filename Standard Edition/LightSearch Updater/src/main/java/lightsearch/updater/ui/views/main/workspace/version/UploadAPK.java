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

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import lightsearch.updater.apk.APK;

import java.io.IOException;

public class UploadAPK extends Upload {

    public UploadAPK(APK apk, MemoryBuffer memoryBuffer) {
        super(memoryBuffer);
        super.setWidth("90%");
        super.setAcceptedFileTypes(".apk");
        super.addFinishedListener(event -> {
            apk.setAPKName(event.getFileName());
            try {
                apk.setContent(memoryBuffer.getInputStream());
                Notification.show("File " + event.getFileName() + " uploaded.");
            }
            catch(IOException ex) {
                Notification.show(ex.getMessage());
            }
        });
    }
}
