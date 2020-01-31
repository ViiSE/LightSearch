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

package ru.viise.lightsearch.data.creator;

import ru.viise.lightsearch.data.CommandCheckBindDTO;
import ru.viise.lightsearch.data.CommandCheckBindDTOInit;

public class CommandCheckBindDTOCreatorDefaultImpl implements CommandCheckBindDTOCreator {

    private final String input;
    private final int selected;
    private final String factoryBarcode;

    public CommandCheckBindDTOCreatorDefaultImpl(String input, int selected, String factoryBarcode) {
        this.input = input;
        this.selected = selected;
        this.factoryBarcode = factoryBarcode;
    }

    @Override
    public CommandCheckBindDTO create() {
        return CommandCheckBindDTOInit.commandCheckBindDTO(input, selected, factoryBarcode);
    }
}
