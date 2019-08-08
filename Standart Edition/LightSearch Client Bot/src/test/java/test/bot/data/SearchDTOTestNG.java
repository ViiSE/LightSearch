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
import lightsearch.client.bot.data.SearchDTOInit;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static test.message.TestMessage.testBegin;
import static test.message.TestMessage.testEnd;

/**
 *
 * @author ViiSE
 */
public class SearchDTOTestNG {
    
    private SearchDTO searchDTO;
    
    @BeforeClass
    public void setUpClass() {
        String barcode = "111111111";
        String sklad   = "sklad";
        String TK      = "TK";
        searchDTO = SearchDTOInit.searchDTO(barcode, sklad, TK);
    }
    
    @Test
    public void barcode() {
        testBegin("SearchDTO", "barcode()");
        
        assertNotNull(searchDTO.barcode(), "Barcode is null!");
        System.out.println("barcode: " + searchDTO.barcode());
        
        testEnd("SearchDTO", "barcode()");
    }
    
    @Test
    public void sklad() {
        testBegin("SearchDTO", "sklad()");
        
        assertNotNull(searchDTO.sklad(), "Sklad is null!");
        System.out.println("Sklad: " + searchDTO.sklad());
        
        testEnd("SearchDTO", "sklad()");
    }
    
    @Test
    public void TK() {
        testBegin("SearchDTO", "TK()");
        
        assertNotNull(searchDTO.TK(), "TK is null!");
        System.out.println("TK: " + searchDTO.TK());
        
        testEnd("SearchDTO", "TK()");
    }
}
