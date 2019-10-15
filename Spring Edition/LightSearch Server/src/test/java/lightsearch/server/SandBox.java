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

package lightsearch.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lightsearch.server.data.pojo.ClientCommandDTO;
import org.testng.annotations.Test;

import java.io.IOException;

public class SandBox {

    @Test
    public void sandBox() throws JsonProcessingException, IOException {
        ClientCommandDTO clientCommandDTO = new ClientCommandDTO();
        clientCommandDTO.setUserIdentifier("655");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println("Serialization: " + mapper.writeValueAsString(clientCommandDTO));

        ClientCommandDTO r = mapper.readValue("{\"ident\":25}", ClientCommandDTO.class);
        System.out.println("Deserialization: " + r.getUserIdentifier());
    }
}
