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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.viise.lightsearch.cmd.ClientCommandContentEnum;
import ru.viise.lightsearch.data.SearchRecordDTO;
import ru.viise.lightsearch.data.SearchRecordDTOInit;

public class SearchRecordsDTOCreatorJSONDefaultImpl implements SearchRecordsDTOCreator {

    private final String PODRAZDELENIE = ClientCommandContentEnum.PODRAZDELENIE.stringValue();
    private final String ID            = ClientCommandContentEnum.ID.stringValue();
    private final String NAME          = ClientCommandContentEnum.NAME.stringValue();
    private final String PRICE         = ClientCommandContentEnum.PRICE.stringValue();
    private final String AMOUNT        = ClientCommandContentEnum.AMOUNT.stringValue();

    private final JSONArray data;

    public SearchRecordsDTOCreatorJSONDefaultImpl(Object data) {
        this.data = (JSONArray)data;
    }

    @Override
    public List<SearchRecordDTO> createSearchRecordsDTO() {
        List<SearchRecordDTO> records = new ArrayList<>();
        for(Object rec : data) {
            JSONObject recJ = (JSONObject)rec;
            SearchRecordDTO searchRecDTO = SearchRecordDTOInit.searchRecordDTO(
                    recJ.get(PODRAZDELENIE).toString(),
                    recJ.get(ID).toString(),
                    recJ.get(NAME).toString(),
                    recJ.get(PRICE).toString(),
                    recJ.get(AMOUNT).toString()
            );
            records.add(searchRecDTO);
        }
        return records;
    }
}
