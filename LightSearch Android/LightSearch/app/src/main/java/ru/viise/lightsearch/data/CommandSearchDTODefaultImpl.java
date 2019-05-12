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

public class CommandSearchDTODefaultImpl implements CommandSearchDTO {

    private final String barcode;
    private final String sklad;
    private final String TK;
    private final String podrazdelenie;

    public CommandSearchDTODefaultImpl(String barcode, String sklad, String TK, String podrazdelenie) {
        this.barcode = barcode;
        this.sklad = sklad;
        this.TK = TK;
        this.podrazdelenie = podrazdelenie;
    }

    @Override
    public String barcode() {
        return barcode;
    }

    @Override
    public String sklad() {
        return sklad;
    }

    @Override
    public String TK() {
        return TK;
    }

    @Override
    public String podrazdelenie() {
        return podrazdelenie;
    }
}
