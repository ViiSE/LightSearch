/*
 *  Copyright 2019 ViiSE.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package lightsearch.server.cmd.client;

import lightsearch.server.data.pojo.ClientCommandDTO;
import lightsearch.server.data.pojo.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("clientCommandDefault")
@Scope("prototype")
public class ClientCommandDefaultImpl implements ClientCommand {

    private final String command;
    private final String IMEI;
    private final String ip;
    private final String os;
    private final String model;
    private final String username;
    private final String password;
    private final String barcode;
    private final String sklad;
    private final String TK;
    private final List<Product> data;
    private final String userIdentifier;
    private final String delivery;
    private final String cardCode;

    public ClientCommandDefaultImpl(ClientCommandDTO clientCommandDTO) {
        command = clientCommandDTO.getCommand();
        IMEI = clientCommandDTO.getIMEI();
        ip = clientCommandDTO.getIp();
        os = clientCommandDTO.getOs();
        model = clientCommandDTO.getModel();
        username = clientCommandDTO.getUsername();
        password = clientCommandDTO.getPassword();
        barcode = clientCommandDTO.getBarcode();
        sklad = clientCommandDTO.getSklad();
        TK = clientCommandDTO.getTK();
        data = clientCommandDTO.getData();
        userIdentifier = clientCommandDTO.getUserIdentifier();
        delivery = clientCommandDTO.getDelivery();
        cardCode = clientCommandDTO.getCardCode();
    }

    @Override
    public String IMEI() {
        return IMEI;
    }

    @Override
    public String ip() {
        return ip;
    }

    @Override
    public String os() {
        return os;
    }

    @Override
    public String model() {
        return model;
    }

    @Override
    public String username() {
        return username;
    }

    @Override
    public String password() {
        return password;
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
    public Object data() {
        return data;
    }

    @Override
    public String userIdentifier() {
        return userIdentifier;
    }

    @Override
    public String delivery() {
        return delivery;
    }

    @Override
    public String cardCode() {
        return cardCode;
    }

    @Override
    public String command() {
        return command;
    }
}
