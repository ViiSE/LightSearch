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

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class FilesWorkspace extends VerticalLayout {

    public FilesWorkspace(TextAreaWorkspace textAreaWorkspace, ButtonChangeInfo buttonChangeInfo) {
        super(textAreaWorkspace, buttonChangeInfo);
        super.getStyle().set("background-color", "#fafafa");
        super.getStyle().set("border-radius", "8px");
        super.setWidth("60%");
        super.getStyle().set("box-shadow", "1px 1px 3px rgba(0,0,0,0.2)");
    }
}
