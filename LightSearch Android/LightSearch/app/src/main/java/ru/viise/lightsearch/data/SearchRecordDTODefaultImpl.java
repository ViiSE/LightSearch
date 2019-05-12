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

public class SearchRecordDTODefaultImpl implements SearchRecordDTO {

    private final String podrazdelenie;
    private final String id;
    private final String name;
    private final String price;
    private final String amount;

    public SearchRecordDTODefaultImpl(String podrazdelenie, String id, String name, String price, String amount) {
        this.podrazdelenie = podrazdelenie;
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    @Override
    public String podrazdelenie() {
        return podrazdelenie;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String price() {
        return price;
    }

    @Override
    public String amount() {
        return amount;
    }
}
