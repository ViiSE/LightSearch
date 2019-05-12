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

package ru.viise.lightsearch.pref;

import android.content.SharedPreferences;

public class PreferencesManagerDefaultImpl implements PreferencesManager {

    private final UsernamePreferencesManager usernamePrefManager;
    private final HostPreferencesManager hostPrefManager;
    private final PortPreferencesManager portPrefManager;

    public PreferencesManagerDefaultImpl(SharedPreferences sPref) {
        usernamePrefManager = UsernamePreferencesManagerInit.usernamePreferencesManager(sPref);
        portPrefManager     = PortReferencesManagerInit.portPreferencesManager(sPref);
        hostPrefManager     = HostPreferencesManagerInit.hostPreferencesManager(sPref);
    }

    @Override
    public String load(PreferencesManagerType type) {
        switch (type) {
            case USERNAME_MANAGER:
                return usernamePrefManager.loadUsername();
            case HOST_MANAGER:
                return hostPrefManager.loadHost();
            case PORT_MANAGER:
                return portPrefManager.loadPort();
            default:
                return null;
        }
    }

    @Override
    public void save(PreferencesManagerType type, String value) {
        switch (type) {
            case USERNAME_MANAGER:
                usernamePrefManager.saveUsername(value);
            case HOST_MANAGER:
                hostPrefManager.saveHost(value);
            case PORT_MANAGER:
                portPrefManager.savePort(value);
        }
    }
}
