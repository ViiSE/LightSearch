/*
 * Copyright 2019 ViiSE.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lightsearch.admin.panel.data;

import java.util.Map;

/**
 *
 * @author ViiSE
 */
public class AdminPanelDTODefaultImpl implements AdminPanelDTO {

    private final Map<String, String> clients;
    private final Map<String, String> blacklist;

    public AdminPanelDTODefaultImpl(Map<String, String> clients, Map<String, String> blacklist) {
        this.clients = clients;
        this.blacklist = blacklist;
    }

    @Override
    public Map<String, String> clients() {
        return clients;
    }

    @Override
    public Map<String, String> blacklist() {
        return blacklist;
    }
}
