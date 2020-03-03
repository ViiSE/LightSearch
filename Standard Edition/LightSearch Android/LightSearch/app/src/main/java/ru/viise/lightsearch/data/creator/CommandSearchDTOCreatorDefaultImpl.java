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

import ru.viise.lightsearch.data.CommandSearchDTO;
import ru.viise.lightsearch.data.CommandSearchDTOInit;
import ru.viise.lightsearch.data.SearchFragmentContentEnum;

public class CommandSearchDTOCreatorDefaultImpl implements CommandSearchDTOCreator {

    private final String ALL    = SearchFragmentContentEnum.ALL.stringValue();
    private final String NULL   = SearchFragmentContentEnum.NULL.stringValue();
    private final String ALL_UI = SearchFragmentContentEnum.ALL_UI.stringValue();

    private final String barcode;
    private final SearchFragmentContentEnum subdivision;
    private String sklad;
    private String TK;

    public CommandSearchDTOCreatorDefaultImpl(
            String barcode, SearchFragmentContentEnum subdivision, String sklad, String TK) {
        this.barcode = barcode;
        this.subdivision = subdivision;
        this.sklad = sklad;
        this.TK = TK;
    }

    @Override
    public CommandSearchDTO createCommandSearchDTO() {
        String subdivStr = null;
        switch(subdivision) {
            case SKLAD:
                if(sklad.equals(ALL_UI)) {
                    sklad = ALL;
                    subdivStr = "Все склады";
                }
                else
                    subdivStr = sklad;
                TK = NULL;
                break;
            case TK:
                if(TK.equals(ALL_UI)) {
                    TK = ALL;
                    subdivStr = "Все ТК";
                }
                else
                    subdivStr = TK;
                sklad = NULL;
                break;
            case ALL:
                sklad = ALL;
                TK = ALL;
                subdivStr = "Все";
                break;
        }
        return CommandSearchDTOInit.commandSearchDTO(barcode, sklad, TK, subdivStr);
    }
}
