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
package lightsearch.client.bot.data;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("lightsearch.client.bot.data.SearchDTODefaultImpl")
@Scope("prototype")
public class SearchDTODefaultImpl implements SearchDTO {

    private final String barcode;
    private final String sklad;
    private final String TK;
    
    public SearchDTODefaultImpl(String barcode, String sklad, String TK) {
        this.barcode = barcode;
        this.sklad   = sklad;
        this.TK      = TK;
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
    
}
