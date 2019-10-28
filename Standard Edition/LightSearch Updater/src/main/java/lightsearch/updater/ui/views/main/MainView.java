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

package lightsearch.updater.ui.views.main;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.InitialPageSettings;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.PageConfigurator;
import lightsearch.updater.producer.ui.views.main.workspace.file.FilesWorkspaceCreatorProducer;
import lightsearch.updater.producer.ui.views.main.workspace.version.APKVersionWorkspaceCreatorProducer;
import lightsearch.updater.ui.views.main.workspace.MainWorkspace;
import lightsearch.updater.ui.views.main.workspace.RootWorkspace;
import lightsearch.updater.ui.views.main.workspace.file.FilesWorkspace;
import lightsearch.updater.ui.views.main.workspace.version.APKVersionWorkspace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@Route
@PWA(name = "LightSearch Updater Admin Panel", shortName = "Admin Panel")
@PageTitle("Admin Panel")
public class MainView extends VerticalLayout { // implements PageConfigurator {

    public MainView(
            @Autowired APKVersionWorkspaceCreatorProducer apkVersionWorkspaceCreatorProducer,
            @Autowired FilesWorkspaceCreatorProducer filesWorkspaceCreatorProducer) {
        FooterMainView footer = new FooterMainView();

        APKVersionWorkspace apkWorkspace = apkVersionWorkspaceCreatorProducer
                .getApkVersionWorkspaceCreatorDefaultInstance()
                .createApkVersionWorkspace();
        FilesWorkspace filesWorkspace = filesWorkspaceCreatorProducer
                .getFilesWorkspaceCreatorDefaultInstance()
                .createFilesWorkspace();
        RootWorkspace rootWorkspace = new RootWorkspace(apkWorkspace, filesWorkspace);

        MainWorkspace mainWorkspace = new MainWorkspace(footer, rootWorkspace);

        super.add(mainWorkspace);
    }

//    @Override
//    public void configurePage(InitialPageSettings settings) {
//        Map<String, String> attributes = new HashMap<>();
//        attributes.put("rel", "shortcut icon");
//        attributes.put("type", "image/png");
//        settings.addLink("META-INF/resources/icons/icon.png", attributes);
//        settings.addLink("shortcut icon", "META-INF/resources/icons/favicon.ico");
//        settings.addFavIcon("icon", "META-INF/resources/icons/icon.png", "256x256");
//    }

//    @Override
//    public void configurePage(InitialPageSettings settings) {
//        Map<String, String> attributes = new HashMap<>();
//        attributes.put("rel", "shortcut icon");
//        attributes.put("type", "image/png");
//        settings.addLink("shortcut icon", "icons/favico.ico");
//        settings.addFavIcon("icon", "icons/icon.png", "192x192");
//    }
}
