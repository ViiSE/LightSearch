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

import com.juicy.JuicyAceEditor;
import com.juicy.mode.JuicyAceMode;
import com.juicy.theme.JuicyAceTheme;
import com.vaadin.flow.component.notification.Notification;
import lightsearch.updater.exception.ReleaseInfoException;
import lightsearch.updater.release.info.ReleaseInfoUploader;

public class ReleaseEditor extends JuicyAceEditor {

    public ReleaseEditor(ReleaseInfoUploader infoUploader) {
        super();
        super.setTheme(JuicyAceTheme.chrome);
        super.getElement().getStyle().set("border-radius", "8px");
        super.setMode(JuicyAceMode.json);
        super.setFontsize(14);
        super.setWidth("100%");
        super.setHeight("300px");

        try {
            super.setValue((String)infoUploader.uploadInfo());
        } catch (ReleaseInfoException ex) {
            Notification.show(ex.getMessage());
        }
    }
}
