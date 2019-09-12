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

import com.juicy.JuicyAceEditor;
import com.juicy.mode.JuicyAceMode;
import com.juicy.theme.JuicyAceTheme;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.StreamResource;
import lightsearch.updater.apk.APK;
import lightsearch.updater.apk.APKSaver;
import lightsearch.updater.apk.APKVersionCreator;
import lightsearch.updater.apk.APKVersionsUploader;
import lightsearch.updater.exception.APKException;
import lightsearch.updater.exception.ReleaseInfoException;
import lightsearch.updater.producer.apk.APKProducer;
import lightsearch.updater.producer.apk.APKSaverProducer;
import lightsearch.updater.producer.apk.APKVersionCreatorProducer;
import lightsearch.updater.producer.apk.APKVersionsUploaderProducer;
import lightsearch.updater.producer.release.info.ReleaseInfoCheckerProducer;
import lightsearch.updater.producer.release.info.ReleaseInfoSaverProducer;
import lightsearch.updater.producer.release.info.ReleaseInfoUploaderProducer;
import lightsearch.updater.release.info.ReleaseInfoChecker;
import lightsearch.updater.release.info.ReleaseInfoSaver;
import lightsearch.updater.release.info.ReleaseInfoUploader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Route
@PWA(name = "LightSearch Updater Admin Panel", shortName = "Admin Panel")
@PageTitle("Admin Panel")
public class MainView extends VerticalLayout {

    private final APKSaver apkSaver;
    private final APKVersionsUploader apkVersionsUploader;
    private final APKVersionCreator apkVersionCreator;
    private final APK apk;

    private final ReleaseInfoUploader infoUploader;
    private final ReleaseInfoChecker infoChecker;
    private final ReleaseInfoSaver infoSaver;

    public MainView(
            @Autowired APKSaverProducer apkSaverProducer,
            @Autowired APKVersionsUploaderProducer apkVersionsUploaderProducer,
            @Autowired APKVersionCreatorProducer apkVersionCreatorProducer,
            @Autowired APKProducer apkProducer,
            @Autowired ReleaseInfoUploaderProducer infoUploaderProducer,
            @Autowired ReleaseInfoCheckerProducer infoCheckerProducer,
            @Autowired ReleaseInfoSaverProducer infoSaverProducer) {
        this.apkSaver = apkSaverProducer.getAPKSaverDefaultInstance();
        this.apkVersionsUploader = apkVersionsUploaderProducer.getApkVersionsUploaderDefaultInstance();
        this.apkVersionCreator = apkVersionCreatorProducer.getAPKVersionCreatorDefaultInstance();
        this.apk = apkProducer.getAPKProducerDefaultInstance();

        this.infoUploader = infoUploaderProducer.getReleaseInfoUploaderFromFileInstance();
        this.infoChecker = infoCheckerProducer.getReleaseInfoCheckerJSONInstance();
        this.infoSaver = infoSaverProducer.getReleaseInfoSaverToFileInstance();

        Footer footer = createFooter();
        VerticalLayout workspaceAPK = createAPKVersionsWorkspace();
        VerticalLayout workspaceFiles = createFilesWorkspace();

        HorizontalLayout workspaceRootH = new HorizontalLayout(workspaceAPK, workspaceFiles);
        workspaceRootH.setWidth("100%");

        VerticalLayout mainWorkspace = new VerticalLayout(footer, workspaceRootH);
        mainWorkspace.getStyle().set("margin", "auto");
        mainWorkspace.getStyle().set("background-color", "#0083FF");
        mainWorkspace.getStyle().set("border-radius", "0px 0px 8px 8px");
        mainWorkspace.setWidth("90%");
        mainWorkspace.getStyle().set("box-shadow", "1px 3px 5px rgba(0,0,0,0.3)");

        super.add(mainWorkspace);

//        Page page = UI.getCurrent().getPage();
//        page.addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
//            @Override
//            public void browserWindowResized(BrowserWindowResizeEvent browserWindowResizeEvent) {
//                if(browserWindowResizeEvent.getWidth() < 400)
//                    mainWorkspace.replace(workspaceRootH, workspaceRootV);
//                else
//                    mainWorkspace.replace(workspaceRootV, workspaceRootH);
//                MainView.super.add(mainWorkspace);
//            }
//        });
    }

    private Footer createFooter() {
        Footer footer = new Footer();

        StreamResource streamResource = new StreamResource("logo.png", () ->
            MainView.class.getClassLoader().getResourceAsStream("META-INF/resources/images/white.png"));

        Image image = new Image(streamResource, "logo");
        image.setHeight("100px");
        image.setWidth("100px");
        image.getStyle().set("padding-top", "10px");
        footer.setWidth("100%");
        footer.getStyle().set("background-color", "#0083FF");
        footer.getStyle().set("border-radius", "8px 8px 2px 2px");
        footer.getStyle().set("font-size", "32pt");
        footer.getStyle().set("text-align", "center");
        footer.getStyle().set("margin", "0");
        footer.getStyle().set("padding", "0");

        footer.add(image);
        return footer;
    }

    private VerticalLayout createAPKVersionsWorkspace() {
        Label label = new Label("Releases Manager");
        label.getStyle().set("color", "#596575");
        label.getStyle().set("font-size", "12pt");

        ComboBox<String> comboBoxVersions = createComboBoxVersions();
        Button buttonCreateNewVersion = createButtonCreateNewVersion(comboBoxVersions);
        Upload uploadAPK = createUploadAPK(apk);
        Button buttonSubmitChanges = createButtonSubmitChanges(apk);

        VerticalLayout apkWorkspace = new VerticalLayout(label, comboBoxVersions, buttonCreateNewVersion, uploadAPK, buttonSubmitChanges);
        apkWorkspace.getStyle().set("background-color", "#fafafa");
        apkWorkspace.getStyle().set("border-radius", "8px");
        apkWorkspace.getStyle().set("box-shadow", "1px 1px 3px rgba(0,0,0,0.2)");
        apkWorkspace.setWidth("40%");

        return apkWorkspace;
    }

    private ComboBox<String> createComboBoxVersions() {
        ComboBox<String> comboBoxVersions = new ComboBox<>("Versions Releases");
        comboBoxVersions.setItems(apkVersionsUploader.uploadVersions());
        comboBoxVersions.setPreventInvalidInput(true);
        comboBoxVersions.addValueChangeListener(event -> apk.setVersion(event.getValue()));
        comboBoxVersions.setWidthFull();
        return comboBoxVersions;
    }

    private Button createButtonCreateNewVersion(ComboBox<String> comboBoxVersions) {
        Dialog dialogNewVersion = createDialogNewVersion(comboBoxVersions);
        Button buttonCreateNewVersion = new Button("Create new version release");
        buttonCreateNewVersion.setWidthFull();
        buttonCreateNewVersion.addClickListener(event -> dialogNewVersion.open());

        return buttonCreateNewVersion;
    }

    private Dialog createDialogNewVersion(ComboBox<String> comboBoxVersions) {
        Dialog dialogNewVersion = new Dialog();

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
                    dialogNewVersion.close();
                } catch (APKException ex) {
                    textFieldDialog.setErrorMessage(ex.getMessage());
                    textFieldDialog.setInvalid(true);
                }
            }
            else
                textFieldDialog.setErrorMessage("Enter release version!");
        });

        dialogNewVersion.add(textFieldDialog, buttonDialogOK);
        return dialogNewVersion;
    }

    private Upload createUploadAPK(APK apk) {
        MemoryBuffer fileBuffer = new MemoryBuffer();
        Upload upload = new Upload(fileBuffer);
        upload.setWidth("90%");
        upload.setAcceptedFileTypes(".apk");
        upload.addFinishedListener(event -> {
            apk.setAPKName(event.getFileName());
            try {
                apk.setContent(fileBuffer.getInputStream());
                //FileOutputStream buffer = (FileOutputStream) fileBuffer.getFileData().getOutputBuffer();
                Notification.show("File " + event.getFileName() + " uploaded.");

            }
            catch(IOException ex) {
                Notification.show(ex.getMessage());
            }
        });

        return upload;
    }

    private Button createButtonSubmitChanges(APK apk) {
        Button buttonSubmitChanges = new Button("Submit changes");
        buttonSubmitChanges.setWidthFull();
        buttonSubmitChanges.addClickListener(event -> {
            if(apk.getVersion() != null && !(apk.getVersion().equals("")))
                try {
                    apkSaver.saveAPK(apk);
                    Notification.show("APK was saved successfully!");
                }
                catch(APKException ex) {
                    Notification.show(ex.getMessage());
                }
            else
                Notification.show("Select APK version!");
        });

        return buttonSubmitChanges;
    }

    private VerticalLayout createFilesWorkspace() {
        Label label = new Label("Release Information Editor");
        label.getStyle().set("color", "#596575");
        label.getStyle().set("font-size", "12pt");

        //TextArea textAreaInfo = createTextAreaInfo();
        JuicyAceEditor editor = createEditorInfo();
        Button buttonChangeInfo = createButtonChangeInfo(editor);//textAreaInfo);
        VerticalLayout textAreaWorkspace = new VerticalLayout(label, editor);//textAreaInfo);
        textAreaWorkspace.setWidth("100%");
        textAreaWorkspace.getStyle().set("resize", "none");
        //textAreaWorkspace.setHeight("100%");

        textAreaWorkspace.setPadding(false);
        textAreaWorkspace.setMargin(false);

        VerticalLayout filesWorkspace = new VerticalLayout(textAreaWorkspace, buttonChangeInfo);
        filesWorkspace.getStyle().set("background-color", "#fafafa");
        filesWorkspace.getStyle().set("border-radius", "8px");
        filesWorkspace.setWidth("60%");
        filesWorkspace.getStyle().set("box-shadow", "1px 1px 3px rgba(0,0,0,0.2)");

        return filesWorkspace;
    }

    private JuicyAceEditor createEditorInfo() {
        JuicyAceEditor editor = new JuicyAceEditor();
        editor.setTheme(JuicyAceTheme.chrome);
        editor.getElement().getStyle().set("border-radius", "8px");
        editor.setMode(JuicyAceMode.json);
        editor.setFontsize(14);
        editor.setWidth("100%");
        editor.setHeight("300px");

        try {
            editor.setValue((String)infoUploader.uploadInfo());
        } catch (ReleaseInfoException ex) {
            Notification.show(ex.getMessage());
        }

        return editor;
    }

    private TextArea createTextAreaInfo() {
        TextArea textArea = new TextArea("Release Information Editor");
        textArea.setHeight("300px");
        textArea.setWidth("100%");
        textArea.getStyle().set("white-space", "pre");
        textArea.getStyle().set("overflow-wrap", "normal");
        textArea.getStyle().set("overflow", "auto");
        textArea.getStyle().set("resize", "none");

        try {
            textArea.setValue((String)infoUploader.uploadInfo());
        } catch (ReleaseInfoException ex) {
            Notification.show(ex.getMessage());
        }

        return textArea;
    }

    private Button createButtonChangeInfo(TextArea textArea) {
        Button buttonChangeInfo = new Button("Change info");
        buttonChangeInfo.setWidth("100%");
        buttonChangeInfo.addClickListener(event -> {
            try {
                infoChecker.check(textArea.getValue());
                infoSaver.saveInfo(textArea.getValue());
                textArea.setInvalid(false);
                Notification.show("Release information is changed!");
            }
            catch(ReleaseInfoException ex) {
                textArea.setErrorMessage(ex.getMessage());
                textArea.setInvalid(true);
            }
        });

        return buttonChangeInfo;
    }

    private Button createButtonChangeInfo(JuicyAceEditor editor) {
        Button buttonChangeInfo = new Button("Change info");
        buttonChangeInfo.setWidth("100%");
        buttonChangeInfo.addClickListener(event -> {
            try {
                infoChecker.check(editor.getValue());
                infoSaver.saveInfo(editor.getValue());
                Notification.show("Release information is changed!");
            }
            catch(ReleaseInfoException ex) {
                Notification.show(ex.getMessage());
            }
        });

        return buttonChangeInfo;
    }
}
