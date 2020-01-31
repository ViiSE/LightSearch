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

import ru.viise.lightsearch.data.CommandBindDTO;
import ru.viise.lightsearch.data.CommandBindDTOInit;

public class CommandBindDTOCreatorDefaultImpl implements CommandBindDTOCreator {

    private final String barcode;
    private final String factoryBarcode;
    private final String userIdent;
    private final int selected;

    public CommandBindDTOCreatorDefaultImpl(String barcode, String factoryBarcode, String userIdent, int selected) {
        this.barcode = barcode;
        this.factoryBarcode = factoryBarcode;
        this.userIdent = userIdent;
        this.selected = selected;
    }

    @Override
    public CommandBindDTO create() {
        return CommandBindDTOInit.commandBindDTO(barcode, factoryBarcode, userIdent, selected);
    }
}
