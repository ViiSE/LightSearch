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
package test.bot.data;

import lightsearch.client.bot.data.SearchDTO;
import lightsearch.client.bot.data.SearchDTOCreator;
import lightsearch.client.bot.data.SearchDTOCreatorInit;
import lightsearch.client.bot.settings.BotSettingsEnum;
import org.json.simple.JSONObject;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SearchDTOCreatorTestNG {
    
    private final String IMPLEMENTATION = BotSettingsEnum.IMPLEMENTATION.toString();
    private final String BARCODE        = BotSettingsEnum.BARCODE.toString();
    private final String SKLAD          = BotSettingsEnum.SKLAD.toString();
    private final String TK_FIELD       = BotSettingsEnum.TK.toString();
    
    private SearchDTOCreator searchDTOCr;
    
    @BeforeClass
    public void setUpClass() {
        JSONObject data = getData();
        searchDTOCr = SearchDTOCreatorInit.searchDTOCreator(data);
    }
    
    @Test
    public void createSearchDTO() {
        testBegin("SearchDTOCreator", "createSearchDTO()");
        
        SearchDTO searchDTO = searchDTOCr.createSearchDTO();
        assertNotNull(searchDTO, "SearchDTO is null!");
        System.out.println("SearchDTO: " + searchDTO);
        System.out.println("SearchDTO.barcode: " + searchDTO.barcode());
        System.out.println("SearchDTO.sklad(): " + searchDTO.sklad());
        System.out.println("SearchDTO.TK(): " + searchDTO.TK());
        
        testEnd("SearchDTOCreator", "createSearchDTO()");
    }
    
    private JSONObject getData() {
        JSONObject data = new JSONObject();
        data.put(IMPLEMENTATION, "lightsearch.client.bot.data.SearchDTODefaultImpl");
        data.put(BARCODE, "111111");
        data.put(SKLAD, "all");
        data.put(TK_FIELD, "null");
        
        return data;
    }
}
