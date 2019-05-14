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
    private final SearchFragmentContentEnum podrazdelenie;
    private String sklad;
    private String TK;

    public CommandSearchDTOCreatorDefaultImpl(String barcode, SearchFragmentContentEnum podrazdelenie, String sklad, String TK) {
        this.barcode = barcode;
        this.podrazdelenie = podrazdelenie;
        this.sklad = sklad;
        this.TK = TK;
    }

    @Override
    public CommandSearchDTO createCommandSearchDTO() {
        String podrStr = null;
        switch(podrazdelenie) {
            case SKLAD:
                if(sklad.equals(ALL_UI)) {
                    sklad = ALL;
                    podrStr = "Все склады";
                }
                else
                    podrStr = sklad;
                TK = NULL;
                break;
            case TK:
                if(TK.equals(ALL_UI)) {
                    TK = ALL;
                    podrStr = "Все ТК";
                }
                else
                    podrStr = TK;
                sklad = NULL;
                break;
            case ALL:
                sklad = NULL;
                TK = NULL;
                podrStr = "Все";
                break;
        }
        return CommandSearchDTOInit.commandSearchDTO(barcode, sklad, TK, podrStr);
    }
}
