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

package ru.viise.lightsearch.data;

public class CommandBindDTODefaultImpl implements CommandBindDTO {

    private final String barcode;
    private final String factoryBarcode;
    private final String cardCode;
    private final int selected;

    public CommandBindDTODefaultImpl(String barcode, String factoryBarcode, String cardCode, int selected) {
        this.barcode = barcode;
        this.factoryBarcode = factoryBarcode;
        this.cardCode = cardCode;
        this.selected = selected;
    }

    @Override
    public String factoryBarcode() {
        return factoryBarcode;
    }

    @Override
    public String userIdent() {
        return cardCode;
    }

    @Override
    public String barcode() {
        return barcode;
    }

    @Override
    public int selected() {
        return selected;
    }
}
