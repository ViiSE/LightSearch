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

import ru.viise.lightsearch.handler.ClientHandler;

public class ClientHandlerDTODefaultImpl implements ClientHandlerDTO {

    private final ClientHandler clientHandler;
    private final String message;

    public ClientHandlerDTODefaultImpl(ClientHandler clientHandler, String message) {
        this.clientHandler = clientHandler;
        this.message = message;
    }

    @Override
    public ClientHandler clientHandler() {
        return clientHandler;
    }

    @Override
    public String message() {
        return message;
    }
}
