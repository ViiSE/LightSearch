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

import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.server.StreamResource;

public class FooterMainView extends Footer {

    public FooterMainView() {
        super.setWidth("100%");
        super.getStyle().set("background-color", "#0083FF");
        super.getStyle().set("border-radius", "8px 8px 2px 2px");
        super.getStyle().set("font-size", "32pt");
        super.getStyle().set("text-align", "center");
        super.getStyle().set("margin", "0");
        super.getStyle().set("padding", "0");

        StreamResource streamResource = new StreamResource("logo.png", () ->
                MainView.class.getClassLoader().getResourceAsStream("META-INF/resources/images/white.png"));

        super.add(new ImageLogo(streamResource, "logo"));
    }
}
